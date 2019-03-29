package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hsys.business.beans.HsysPageInfo;
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
	
	public HsysPageInfo<AttendanceModel> queryList(HsysPageInfo<AttendanceModel> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), true);
		Page<AttendanceModel> pageResult = (Page<AttendanceModel>) attendanceMapper.queryList(page.getParam());

		page.setPageInfo(pageResult);

		return page;
	}
	
	public void add(AttendanceModel a) {
		attendanceMapper.add(a);
	}

	public AttendanceModel queryOne(AttendanceModel a) {
		HsysPageInfo<AttendanceModel> pageInfo = new HsysPageInfo<AttendanceModel>();
		pageInfo.setParam(a);
		List<AttendanceModel> us = queryList(pageInfo);
		if(us.size() == 1) {
			return us.get(0);
		}
		return null;
	}

	public void update(AttendanceModel a) {
		attendanceMapper.update(a);
	}
}
