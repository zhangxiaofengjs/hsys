package com.hsys.mappers;

/**
 * @author: hancaipeng
 * @version: 2019/01/22
 */
import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.hsys.models.RestModel;

@Mapper
public interface RestMapper {
	List<RestModel> queryList(RestModel group);
	
	void add(RestModel rest);

	void update(RestModel rest);
	
	void delete(RestModel rest);
	
	void approve(RestModel rest);
}
