package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.forms.HolidayAddForm;
import com.hsys.business.forms.HolidayDeleteForm;
import com.hsys.business.forms.HolidayGetForm;
import com.hsys.business.forms.HolidayHtmlListForm;
import com.hsys.business.forms.HolidayUpdateForm;
import com.hsys.exception.HsysException;
import com.hsys.models.HolidayModel;
import com.hsys.models.enums.ROLE;
import com.hsys.services.HolidayService;

/**
 * @author: wu
 * @version: 2019/07/17
 */
@Component
public class HolidayBusiness {
	@Autowired
    private HolidayService holidayService;

	public List<HolidayModel> getHolidays(HolidayHtmlListForm holidayHtmlListForm) {
		// TODO Auto-generated method stub
		HolidayModel holiday = new HolidayModel();
		holiday.setCond(HolidayModel.COND_DATE_START, holidayHtmlListForm.getStartDate());
		holiday.setCond(HolidayModel.COND_DATE_END, holidayHtmlListForm.getEndDate());
		List<HolidayModel> list = holidayService.queryList(holiday);
		return list;
	}

	public void add(HolidayAddForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.HOLIDAY_EDIT)) {
			throw new HsysException("权限不足"); 
		}
		HolidayModel holidayExist = holidayService.queryByDate(form.getDate());
		//检测假日是否已经存在
		if(holidayExist != null) {
			throw new HsysException("该日期已存在"); 
		}
		HolidayModel addHoliday = new HolidayModel();
		addHoliday.setComment(form.getComment());
		addHoliday.setType(form.getType());
		addHoliday.setDate(form.getDate());	
		holidayService.add(addHoliday);
	}
	public void update(HolidayUpdateForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.HOLIDAY_EDIT)) {
			throw new HsysException("权限不足"); 
		}
		HolidayModel holiday = holidayService.queryById(form.getId());
		if(holiday == null) {
			throw new HsysException("该假日记录不存在。");
		}
		if(holiday.getDate().equals(form.getDate())) {
			if(holiday.getType()==form.getType() && holiday.getComment().equals(form.getComment())) {
				throw new HsysException("该日期已存在"); 
			}
		} else {
			holiday.setDate(form.getDate());
			holiday.setUpdate(HolidayModel.FIELD_DATE);
		}
		
		if(!holiday.getComment().equals(form.getComment())) {
			holiday.setComment(form.getComment());
			holiday.setUpdate(HolidayModel.FIELD_COMMENT);
		}
		
		if(holiday.getType()!=form.getType()) {
			holiday.setType(form.getType());
			holiday.setUpdate(HolidayModel.FIELD_TYPE);
		}
		
		if(holiday.hasUpdate()) {
			holidayService.update(holiday);
		}
	}
	@Transactional
	public void delete(HolidayDeleteForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.HOLIDAY_EDIT)) {
			throw new HsysException("权限不足"); 
		}
		int[] ids = form.getIds();
		for(int id : ids) {
			HolidayModel holidayModel = holidayService.queryById(id);
			if(holidayModel == null) {
				throw new HsysException("该数据不存在");
			}
			holidayService.deleteById(id);
		}
	}
	
	public HolidayModel getHoliday(HolidayGetForm form) {
		HolidayModel holiday= holidayService.queryById(form.getId());
		if(holiday == null) {
			throw new HsysException("该假日记录不存在。");
		}
		return holiday;
	}
}
