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
}
