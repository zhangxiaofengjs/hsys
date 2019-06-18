package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.forms.SchoolDeleteForm;
import com.hsys.business.forms.SchoolGetForm;
import com.hsys.business.forms.SchoolUpdateForm;
import com.hsys.common.HsysList;
import com.hsys.exception.HsysException;
import com.hsys.models.SchoolModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
import com.hsys.services.SchoolService;
import com.hsys.services.UserService;

/**
 * @author: zyh950327@aliyun.com
 * @version: 2019/03/20
 */
@Component
public class SchoolBusiness {
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private UserService userService;
	
	public List<SchoolModel> getSchools() {
		return schoolService.queryList(new SchoolModel());
	}

	public void add(SchoolModel school) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		
		SchoolModel schoolExist = schoolService.queryByName(school.getName());
		//检测学校是否已经存在
		if(schoolExist != null) {
			throw new HsysException("该学校存在:%s, %s", schoolExist.getName(), schoolExist.getProvince()); 
		}
		schoolService.add(school);
	}

	@Transactional
	public void delete(SchoolDeleteForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		
		int[] ids = form.getIds();
		for(int id : ids) {
			SchoolModel schoolModel = schoolService.queryById(id);
			if(schoolModel == null) {
				throw new HsysException("该数据不存在");
			}else {
				UserModel user = new UserModel();
				user.setCond(UserModel.COND_SCHOOL_ID, id);
				List<UserModel> users = userService.queryList(user);
				if(!HsysList.isEmpty(users)) {
					throw new HsysException("删除失败：有员工[" + users.get(0).getName() + "...]属于该学校。");
				}
			}			
			schoolService.deleteById(id);
		}
	}

	public void update(SchoolUpdateForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		
		SchoolModel school = schoolService.queryById(form.getId());
		if(school == null) {
			throw new HsysException("该学校数据不存在。");
		}

		if(!school.getName().equals(form.getName())) {
			school.setName(form.getName());
			SchoolModel schoolExist = schoolService.queryByName(school.getName());
			//检测学校是否已经存在
			if(schoolExist != null) {
				throw new HsysException("该学校存在:%s, %s", schoolExist.getName(), schoolExist.getProvince()); 
			}else {	
				school.setUpdate(SchoolModel.FIELD_NAME);
			}
		}
	
		if(school.hasUpdate()) {
			schoolService.update(school);
		}
	}

	public SchoolModel getSchool(SchoolGetForm form) {
		SchoolModel school = schoolService.queryById(form.getId());
		if(school == null) {
			throw new HsysException("该学校不存在。");
		}
		return school;
	}
}
