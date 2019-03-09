package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.models.CompanyModel;
import com.hsys.services.CompanyService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/06
 */
@Component
public class CompanyBusiness {
	@Autowired
	private CompanyService companyService;
	
	public List<CompanyModel> getCompanies() {
		return companyService.queryList(new CompanyModel());
	}
}
