package com.hsys.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.forms.CompanyJsonDeleteForm;
import com.hsys.business.forms.CompanyJsonGetForm;
import com.hsys.business.forms.CompanyJsonUpdateForm;
import com.hsys.common.HsysList;
import com.hsys.exception.HsysException;
import com.hsys.models.CompanyModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
import com.hsys.services.CompanyService;
import com.hsys.services.UserService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/06
 */
@Component
public class CompanyBusiness {
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserService userService;
	
	public List<CompanyModel> getCompanies() {
		return companyService.queryList(new CompanyModel());
	}
	
	public void add(CompanyModel company) {
		//权限判定
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		
		CompanyModel companyExist = companyService.queryByName(company.getName());
		//检测公司名是否已经存在
		if(companyExist != null) {
			throw new HsysException("该公司信息存在:%s", companyExist.getName()); 
		}
		//检测信息长度
		if(company.getName().length()>50) {
			throw new HsysException("该公司名过长"); 
		}
		if(company.getAddress().length()>50) {
			throw new HsysException("该地址过长"); 
		}
		//电话检测
		if(company.getPhoneNum().length()>50) {
				throw new HsysException("电话过长"); 
		}
		companyService.add(company);
	}

	public void update(CompanyJsonUpdateForm form) {
		//权限判定
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		//公司信息是否存在
		CompanyModel company = companyService.queryById(form.getId());
		if(company == null) {
			throw new HsysException("该信息不存在。");
		}
		
		//判断公司名是否更改
		if(!(company.getName()==null?form.getName()==null:company.getName().equals(form.getName()))) {
			//公司名长度不得大于50个字
			if(form.getName().length()<=50){
				CompanyModel companyExist = companyService.queryByName(form.getName());
				//检测公司名是否已经存在
				if(companyExist != null) {
					throw new HsysException("该公司信息存在:%s", companyExist.getName()); 
				}
				company.setName(form.getName());
				company.setUpdate(CompanyModel.FIELD_NAME);
			}else {
				throw new HsysException("请输入50字以内的公司名");
			}
		}
		
		//地址长度不得大于50个字
		if(!(company.getAddress()==null?form.getAddress()==null:company.getAddress().equals(form.getAddress()))){
			if(form.getAddress().length()<=50){
				company.setAddress(form.getAddress());
				company.setUpdate(CompanyModel.FIELD_ADDRESS);
			}else {
				throw new HsysException("请输入50字以内的地址");
			}
		}
		
		//电话检测
		if(!(company.getPhoneNum()==null?form.getPhoneNum()==null:company.getPhoneNum().equals(form.getPhoneNum()))){
			if(form.getPhoneNum().length()<=50){
				company.setPhoneNum(form.getPhoneNum());
				company.setUpdate(CompanyModel.FIELD_PHONENUM);
			}else {
				throw new HsysException("请输入50字以内的电话");
			}
		}
		
		if(company.hasUpdate())
		{
			companyService.update(company);
		}
	}
	
	public CompanyModel get(CompanyJsonGetForm form) {
		//根据id取得公司信息
		CompanyModel company = companyService.queryById(form.getId());
		if(company == null) {
			throw new HsysException("该公司信息不存在。");
		}
		return company;
	}

	@Transactional
	public void delete(CompanyJsonDeleteForm form) {
		//权限判定
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		int[] ids = form.getIds();
		//初始化已被利用的公司列表
		List<String> errCompanies = new ArrayList<>();
		for(int id : ids) {
			//排他检证
			CompanyModel company = companyService.queryById(id);
			if(company == null){
				throw new HsysException("该数据不存在");
			}
			UserModel user = new UserModel();
			user.setCond(UserModel.COND_COMPANY_ID, id);
			//公司对应id是否在用户表中被利用
			List<UserModel> users = userService.queryList(user);
			//存在已被用户利用的公司
			if(!HsysList.isEmpty(users))
			{
				CompanyModel errcompany = companyService.queryById(id);
				//添加已被利用的公司名
				errCompanies.add(errcompany.getName());
			}else {
				companyService.deleteById(id);
			}
		}
		//存在已被用户利用的公司
		if(!errCompanies.isEmpty())
		{
			StringBuilder bld = new StringBuilder("无法删除！存在已被用户使用的公司: ");
			String str = StringUtils.join(errCompanies, ',');
			throw new HsysException(bld.toString()+str);
		}
	}
}
