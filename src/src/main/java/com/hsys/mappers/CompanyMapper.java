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
	//表示
	List<CompanyModel> queryList(CompanyModel c);
	//追加
	void add(CompanyModel c);
	//編集
	void update(CompanyModel c);
	//削除
	void delete(CompanyModel c);
}
