package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.hsys.models.RestModel;

@Mapper
public interface RestMapper {
	List<RestModel> queryList(RestModel group);
	
	void add(RestModel rest);

}
