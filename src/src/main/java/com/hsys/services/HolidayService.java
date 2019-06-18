package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.HolidayMapper;
import com.hsys.models.HolidayModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/04/24
 */
@Service
public class HolidayService {
	@Autowired
	private HolidayMapper holidayMapper;
	
	public List<HolidayModel> queryList(HolidayModel h) {
		return holidayMapper.queryList(h);
	}
}
