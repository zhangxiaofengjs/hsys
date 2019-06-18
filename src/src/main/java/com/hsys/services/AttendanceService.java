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
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.isCount());
		Page<AttendanceModel> pageResult = (Page<AttendanceModel>) attendanceMapper.queryList(page.getParam());

		page.setPageInfo(pageResult);

		return page;
	}
	

	public List<AttendanceModel> queryList(AttendanceModel att) {
		HsysPageInfo<AttendanceModel> pageInfo = new HsysPageInfo<AttendanceModel>();
		pageInfo.setParam(att);
		pageInfo.setCount(false);//无需计数查询总数
		return queryList(pageInfo);
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
	
	public void update(List<AttendanceModel> list) {
		attendanceMapper.updateBatch(list);
	}

	public void add(List<AttendanceModel> list) {
		attendanceMapper.addBatch(list);
	}
}
