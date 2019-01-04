package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.AttendanceModel;


/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
@Mapper
public interface AttendanceMapper {

	List<AttendanceModel> queryList(AttendanceModel group);

	void add(AttendanceModel group);

	void update(AttendanceModel a);
	
}
