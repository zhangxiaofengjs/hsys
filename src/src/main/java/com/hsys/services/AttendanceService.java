package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.AttendanceMapper;
import com.hsys.models.AttendanceModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
@Service
public class AttendanceService {
	@Autowired
    private AttendanceMapper attendanceMapper;
	
	public List<AttendanceModel> queryList(AttendanceModel a) {
		return attendanceMapper.queryList(a);
	}
	
	public void add(AttendanceModel a) {
		attendanceMapper.add(a);
	}

	public AttendanceModel queryOne(AttendanceModel a) {
		List<AttendanceModel> us = queryList(a);
		if(us.size() == 1) {
			return us.get(0);
		}
		return null;
	}

	public void update(AttendanceModel a) {
		attendanceMapper.update(a);
	}
}
