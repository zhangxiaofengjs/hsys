package com.hsys.business;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.forms.RestHtmlListForm;
import com.hsys.business.forms.RestJsonApproveForm;
import com.hsys.business.forms.RestJsonDeleteForm;
import com.hsys.business.forms.RestJsonUpdateForm;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.RestModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.RestType;
import com.hsys.services.RestServices;

/**
 * @author: hancaipeng
 * @version: 2019/01/22
 */
@Component
public class RestBusiness {
	@Autowired
	private RestServices restService;

	@SuppressWarnings("deprecation")
	public List<RestModel> getRests(RestHtmlListForm restForm) {
			RestModel rest = new RestModel();
		
		if (HsysString.isNullOrEmpty(restForm.getUserNo()) == true) {
			if (HsysString.isNullOrEmpty(restForm.getDateStart()) == true && HsysString.isNullOrEmpty(restForm.getDateEnd()) == true) {
				Date nowDate = new Date();
				Date endDate = new Date();
				if(nowDate.getDate() < 21) {
					if(nowDate.getMonth() == 1) {
						nowDate.setMonth(12);
						nowDate.setYear(nowDate.getYear()-1);
					}else {
						nowDate.setMonth(nowDate.getMonth()-1);
					}
				}else {
					if(nowDate.getMonth() == 12) {
						endDate.setMonth(1);
						endDate.setYear(nowDate.getYear()+1);
						
					}else {
						endDate.setMonth(endDate.getMonth()+1);
					}
				}
				nowDate.setDate(21);
				nowDate.setHours(0);
				nowDate.setMinutes(0);
				endDate.setDate(20);
				endDate.setHours(23);
				endDate.setMinutes(59);
				rest.setDateStart(nowDate);
				rest.setDateEnd(endDate);
				rest.setCond(RestModel.CONID_DATE_START, true);
				rest.setCond(RestModel.CONID_DATE_END, true);
			}
		}else {
			UserModel user = new UserModel();
			user.setNo(restForm.getUserNo());
			rest.setUser(user);
			rest.setCond(RestModel.COND_USER_NO, true);
		}
		if(HsysString.isNullOrEmpty(restForm.getDateStart()) == false) {
			rest.setDateStart(HsysDate.tryParse(restForm.getDateStart(), "yyyy-MM-dd"));
			rest.setCond(RestModel.CONID_DATE_START, true);
		}
		if(HsysString.isNullOrEmpty(restForm.getDateEnd()) == false) {
			rest.setDateEnd(HsysDate.tryParse(restForm.getDateEnd(),"yyyy-MM-dd"));
			rest.setCond(RestModel.CONID_DATE_END, true);
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
		if(rest.getDateStart().getMinutes()%15 != 0 ) {
			throw new HsysException("时间只能为15的倍数");
		}
		if(rest.getDateEnd().getMinutes()%15 != 0 ) {
			throw new HsysException("时间只能为15的倍数");
		}
		if (rest.getLen() > 8 || rest.getLen() < 1) {
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
			RestModel rest = restService.queryById(id);
			if(rest == null) {
				throw new HsysException("该数据不存在");
			}
			if(rest.getStatus() != 0) {
				throw new HsysException("已经批准的数据不能删除");
			}
			restService.deleteById(id);
		}
	}
	
	public void approve(RestJsonApproveForm form) {
		int[] ids = form.getIds();
		for(int id : ids) {
			RestModel rest = restService.queryById(id);
			if(rest.getStatus() != 0) {
				throw new HsysException("已经批准的数据不能批准");
			}
			UserModel approveUser = new UserModel();
			approveUser.setId(HsysSecurityContextHolder.getLoginUserId());
			rest.setApprovalUser(approveUser);
			rest.setApprovalDate(new Date());
			rest.setStatus(1);
			rest.setUpdate(RestModel.FIELD_APPROVE);
			restService.update(rest);
		}
	}

}
