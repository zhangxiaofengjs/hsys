package com.hsys.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.forms.RestHtmlListForm;
import com.hsys.business.forms.RestJsonApproveForm;
import com.hsys.business.forms.RestJsonDeleteForm;
import com.hsys.business.forms.RestJsonGetForm;
import com.hsys.business.forms.RestJsonRejectForm;
import com.hsys.business.forms.RestJsonUpdateForm;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.RestModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
import com.hsys.models.enums.RestStatus;
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

	public List<RestModel> getRests(RestHtmlListForm restForm) {
		RestModel rest = new RestModel();

		if (!HsysString.isNullOrEmpty(restForm.getUserNo())) {
			UserModel user = new UserModel();
			user.setNo(restForm.getUserNo());
			rest.setUser(user);
			rest.setCond(RestModel.COND_USER_NO, true);
			rest.setCond(RestModel.COND_FUZZY_USER_NO, true);
		}
		
		Date d1 = restForm.getDateStart();
		d1 = HsysDate.startOfDay(d1);
		rest.setDateStart(d1);
		rest.setCond(RestModel.COND_DATE_START, true);
		
		Date d2 = restForm.getDateEnd();
		d2 = HsysDate.endOfDay(d2);
		rest.setDateEnd(d2);
		rest.setCond(RestModel.COND_DATE_END, true);
		
		return restService.queryList(rest);
	}

	public void add(RestModel rest) {
		checkRest(rest);
		restService.add(rest);
	}
	
	public RestModel getRest(RestJsonGetForm form) {
		RestModel rest = restService.queryById(form.getId());
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
		if(rest.getStatus() != RestStatus.Regist) {
			throw new HsysException("已经审核的数据不能修改");
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
		
		if(!HsysDate.isQuarterMinute(rest.getDateStart()) ||
			!HsysDate.isQuarterMinute(rest.getDateEnd())) {
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
	
	@Transactional
	public void delete(RestJsonDeleteForm form) {
		int[] ids = form.getIds();
		for(int id : ids) {
			RestModel rest = restService.queryById(id);
			if(rest == null) {
				throw new HsysException("该数据不存在");
			}
			if(rest.getStatus() != RestStatus.Regist) {
				throw new HsysException("已经审核的数据不能删除。");
			}
			restService.deleteById(id);
		}
		for(int id : ids) {
			restService.deleteById(id);
		}
	}
	
	public void approve(RestJsonApproveForm form) {
		if(!HsysSecurityContextHolder.getLoginUserHasRole(ROLE.REST_APPROVE)) {
			throw new HsysException("无权限");
		}
		int[] ids = form.getIds();
		for(int id : ids) {
			RestModel rest = restService.queryById(id);
			if(rest == null) {
				throw new HsysException("该数据不存在");
			}
			if(rest.getStatus() == RestStatus.Approval) {
				throw new HsysException("已经批准的数据不能批准");
			}
		}
		for(int id : ids) {
			RestModel rest = restService.queryById(id);
			UserModel approveUser = new UserModel();
			approveUser.setId(HsysSecurityContextHolder.getLoginUserId());
			rest.setApprovalUser(approveUser);
			rest.setApprovalDate(new Date());
			rest.setStatus(RestStatus.Approval);
			rest.setUpdate(RestModel.FIELD_APPROVE);
			restService.update(rest);
		}
	}

	public void reject(RestJsonRejectForm form) {
		if(!HsysSecurityContextHolder.getLoginUserHasRole(ROLE.REST_APPROVE)) {
			throw new HsysException("无权限");
		}

		int[] ids = form.getIds();
		for(int id : ids) {
			RestModel rest = restService.queryById(id);
			if(rest == null) {
				throw new HsysException("该数据不存在");
			}
		}

		for(int id : ids) {
			RestModel rest = restService.queryById(id);
			UserModel approveUser = new UserModel();
			approveUser.setId(HsysSecurityContextHolder.getLoginUserId());
			rest.setApprovalUser(approveUser);
			rest.setApprovalDate(new Date());
			rest.setStatus(RestStatus.Reject);
			rest.setUpdate(RestModel.FIELD_APPROVE);
			restService.update(rest);
		}
	}
}
