package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.ExtraTimeModel;


/**
 * @author: 18651304595@163.com
 * @version: 2019/01/28
 */
@Mapper
public interface ExtraTimeMapper {
	List<ExtraTimeModel> queryList(ExtraTimeModel extraTime);
	void add(ExtraTimeModel extra);
	void delete(ExtraTimeModel extra);
	void update(ExtraTimeModel extraTime);
}
