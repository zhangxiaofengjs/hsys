package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.CompanyModel;


/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/06
 */
@Mapper
public interface CompanyMapper {
	List<CompanyModel> queryList(CompanyModel user);
}
