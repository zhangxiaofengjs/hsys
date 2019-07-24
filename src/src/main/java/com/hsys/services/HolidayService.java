package com.hsys.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.HolidayMapper;
import com.hsys.models.HolidayModel;

/**
 * @author: wu
 * @version: 2019/07/17
 */
@Service
public class HolidayService {
	@Autowired
	private HolidayMapper holidayMapper;
	
	public List<HolidayModel> queryList(HolidayModel h) {
		return holidayMapper.queryList(h);
	}
	public HolidayModel queryById(int id) {
		HolidayModel holiday = new HolidayModel();
		holiday.setId(id);
		holiday.setCond(HolidayModel.COND_ID, true);
		List<HolidayModel> holidays = queryList(holiday);
		
		if(holidays.size() != 0) {
			return  holidays.get(0);
		}
		return null;
	}
	public HolidayModel queryByDate(Date date) {
		HolidayModel holiday = new HolidayModel();
		holiday.setDate(date);
		holiday.setCond(HolidayModel.COND_DATE, date);
		List<HolidayModel> holidays = queryList(holiday);
		if(holidays.size() != 0) {
			return  holidays.get(0);
		}
		return null;
	}
	public void deleteById(int id) {
		HolidayModel holiday = new HolidayModel();
		holiday.setId(id);
		holiday.setCond(HolidayModel.COND_ID, true);
		holidayMapper.delete(holiday);
	}
	
	public void add(HolidayModel holiday) {
		holidayMapper.add(holiday);		
	}
	public void update(HolidayModel holiday) {
		holidayMapper.update(holiday);
	}
}
