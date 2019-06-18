package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.CompanyMapper;
import com.hsys.models.CompanyModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/06
 */
@Service
public class CompanyService {
	@Autowired
    private CompanyMapper companyMapper;
	
	public List<CompanyModel> queryList(CompanyModel c) {
		return companyMapper.queryList(c);
	}
	
	//通过名称检索
	public CompanyModel queryByName(String name) {
		CompanyModel company = new CompanyModel();
		company.setName(name);
		company.setCond(CompanyModel.COND_NAME, true);
		List<CompanyModel> companylist = queryList(company);
		if(companylist.size() == 1) {
			return companylist.get(0);
		}
		return null;
	}
	
	//通过id检索
	public CompanyModel queryById(int id) {
		CompanyModel company = new CompanyModel();
		company.setId(id);
		company.setCond(CompanyModel.COND_ID, true);
		List<CompanyModel> list = queryList(company);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}
	
	//添加
	public void add(CompanyModel c) {
		companyMapper.add(c);
	}
	
	//编辑
	public void update(CompanyModel c) {
		companyMapper.update(c);
	}
	
	//通过id删除
	public void deleteById(int id) {
		CompanyModel company = new CompanyModel();
		company.setId(id);
		companyMapper.delete(company);
	}
}
