package com.hsys.business;

/**
 * @author: hancaipeng
 * @version: 2019/01/22
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.business.forms.RestHtmlListForm;
import com.hsys.business.forms.RestJsonDeleteForm;
import com.hsys.business.forms.RestJsonUpdateForm;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.RestModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.RestType;
import com.hsys.services.RestServices;

@Component
public class RestBusiness {
	@Autowired
	private RestServices restService;

	public List<RestModel> getRests(RestHtmlListForm restForm) {
		RestModel rest = new RestModel();
			if (HsysString.isNullOrEmpty(restForm.getUserNo()) == false) {
				UserModel user = new UserModel();
				user.setNo(restForm.getUserNo());
				rest.setUser(user);
				rest.setCond(RestModel.COND_USER_NO, true);
			}
			if (restForm.getDate() != null) {
				rest.setDate(restForm.getDate());
				rest.setCond(RestModel.COND_DATE, true);
			}
		return restService.queryList(rest);
	}

	public void add(RestModel rest) {
		checkRest(rest);
		restService.add(rest);
	}
	
	public RestModel getRest(RestHtmlListForm restForm) {
		RestModel rest = restService.queryById(restForm.getId());
		if(rest == null) {
			throw new HsysException("该记录不存在。");
		}
		return rest;
	}
	
	public void update(RestJsonUpdateForm form) {
		RestModel rest = restService.queryById(form.getId());
		if(rest == null) {
			throw new HsysException("该记录不存在");
		}
		if(rest.getLen() != form.getLen()) {
			rest.setLen(form.getLen());
			rest.setUpdate(RestModel.FIELD_LEN);
		}
		if(rest.getDateStart() != form.getDateStart()) {
			rest.setDateStart(form.getDateStart());
			rest.setUpdate(RestModel.FIELD_DATE_START);
		}
		if(rest.getDateEnd() != form.getDateEnd()) {
			rest.setDateEnd(form.getDateEnd());
			rest.setUpdate(RestModel.FIELD_DATE_END);
		}
		if(rest.getType() != form.getType()) {
			rest.setType(form.getType());
			rest.setUpdate(RestModel.FIELD_TYPE);
		}
		if(!rest.getSummary().equals(form.getSummary())) {
			rest.setSummary(form.getSummary());
			rest.setUpdate(RestModel.FIELD_SUMMARY);
		}
		checkRest(rest);
		restService.update(rest);
	}
	
	public void checkRest(RestModel rest) {
		if (rest.getUser() == null) {
			throw new HsysException("不存在用户");
		}
		if (rest.getDateStart() == null || rest.getDateEnd() == null || rest.getLen() == 0) {
			throw new HsysException("时间不能为空");
		}
		if (rest.getType() > RestType.Public || rest.getType() < RestType.Vacation) {
			throw new HsysException("种类超出范围");
		}
		if (rest.getDateStart().compareTo(rest.getDateEnd()) == 1) {
			throw new HsysException("结束时间不得小于开始时间");
		}
		/*
		 * if(rest.getStart().getMinutes() != rest.getStart().getMinutes()) { throw new
		 * HsysException("请假时间只能是四个小时或八个小时"); } if(rest.getStart().getMinutes()%15 != 0
		 * ) { throw new HsysException("时间不为15倍数"); } if(rest.getStart().getHours()<12)
		 * {
		 * 
		 * rest.getEnd().setHours(rest.getStart().getHours()+rest.getTime()+1);
		 * rest.getEnd().setMinutes(rest.getStart().getMinutes()); }else {
		 * rest.getEnd().setHours(rest.getStart().getHours()+rest.getTime());
		 * rest.getEnd().setMinutes(rest.getStart().getMinutes()); }
		 */
		if (rest.getLen() > 8 || rest.getLen() < 4) {
			throw new HsysException("请假时长超出范围");
		}
		if (rest.getType() == 0) {
			if (rest.getLen() != 4 && rest.getLen() != 8) {
				throw new HsysException("时长只能是四小时或八小时");
			}
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED) 
	public void delete(RestJsonDeleteForm form) {
		int[] ids = form.getIds();
		for(int id : ids) {
			restService.deleteById(id);
		}
	}
}
