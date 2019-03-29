package com.hsys.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.forms.RestHtmlListForm;
import com.hsys.business.forms.RestJsonAddForm;
import com.hsys.business.forms.RestJsonApproveForm;
import com.hsys.business.forms.RestJsonCancelRequestForm;
import com.hsys.business.forms.RestJsonDeleteForm;
import com.hsys.business.forms.RestJsonGetForm;
import com.hsys.business.forms.RestJsonRejectForm;
import com.hsys.business.forms.RestJsonUpdateForm;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysList;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.ExtraTimeModel;
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

	public List<RestModel> getRests(RestHtmlListForm form) {
		if(form.isApprove()) {
			//审核页面，检查审核权限
			if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.REST_APPROVE)) {
				form.setApprove(false);
				return HsysList.New();
			}
		}
		else if(form.isView()) {
			//检查一览权限
			if(!HsysSecurityContextHolder.isLoginUserHasAnyRole(ROLE.REST_LIST, ROLE.REST_LIST_ALL)) {
				form.setView(false);
				return HsysList.New();
			}
		} else if(form.isUser()) {
			form.setUserNo(HsysSecurityContextHolder.getLoginUser().getNo());
		} else {
			return HsysList.New();
		}

		RestModel rest = new RestModel();
		if (!HsysString.isNullOrEmpty(form.getUserNo())) {
			UserModel user = new UserModel();
			user.setNo(form.getUserNo());
			rest.setUser(user);
			rest.setCond(RestModel.COND_USER_NO, true);
			rest.setCond(RestModel.COND_FUZZY_USER_NO, true);
		}
		
		Date d1 = form.getDateStart();
		d1 = HsysDate.startOfDay(d1);
		rest.setDateStart(d1);
		rest.setCond(RestModel.COND_DATE_START, true);
		
		Date d2 = form.getDateEnd();
		d2 = HsysDate.endOfDay(d2);
		rest.setDateEnd(d2);
		rest.setCond(RestModel.COND_DATE_END, true);
		
		if(form.isApprove()) {
			List<Integer> statuss = HsysList.New();
			statuss.add(RestStatus.Regist);
			statuss.add(RestStatus.CancelRequest);
			rest.setCond(RestModel.COND_STATUS_MULTI, statuss);
		}

		if((form.isApprove() || form.isView()) &&
			!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.REST_LIST_ALL)) {
			rest.setCond(ExtraTimeModel.COND_GROUP_ID,
			HsysSecurityContextHolder.getLoginUser().getGroup().getId());
		}

		return restService.queryList(rest);
	}

	public void add(RestJsonAddForm form) {
		RestModel rest = new RestModel();
		rest.setDateEnd(form.getDateEnd());
		rest.setDateStart(form.getDateStart());
		rest.setLen(form.getLen());
		rest.setSummary(form.getSummary());
		rest.setType(form.getType());
		
		UserModel user;
		if(form.getUserId() == 0) {
			//当前用户
			user = HsysSecurityContextHolder.getLoginUser();
		} else {
			user = new UserModel();
			user.setId(form.getUserId());
		}
		rest.setUser(user);

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
	}
	
	@Transactional
	public void approve(RestJsonApproveForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.REST_APPROVE)) {
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
			
			rest.setApprovalUser(HsysSecurityContextHolder.getLoginUser());
			rest.setApprovalTime(HsysDate.now());
			rest.setStatus(rest.getStatus() == RestStatus.CancelRequest ? RestStatus.Cancel : RestStatus.Approval);
			rest.setUpdate(RestModel.FIELD_APPROVAL_USER_ID);
			rest.setUpdate(RestModel.FIELD_STATUS);
			restService.update(rest);
		}
	}

	@Transactional
	public void reject(RestJsonRejectForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.REST_APPROVE)) {
			throw new HsysException("无权限");
		}

		int[] ids = form.getIds();
		for(int id : ids) {
			RestModel rest = restService.queryById(id);
			if(rest == null) {
				throw new HsysException("该数据不存在");
			}
			if(rest.getStatus() == RestStatus.Reject) {
				throw new HsysException("已经驳回的数据不能驳回");
			}
			
			rest.setApprovalUser(HsysSecurityContextHolder.getLoginUser());
			rest.setApprovalTime(HsysDate.now());
			rest.setStatus(rest.getStatus() == RestStatus.CancelRequest ? RestStatus.Approval : RestStatus.Reject);
			rest.setUpdate(RestModel.FIELD_APPROVAL_USER_ID);
			rest.setUpdate(RestModel.FIELD_STATUS);
			restService.update(rest);
		}
	}

	@Transactional
	public void cancelRequest(RestJsonCancelRequestForm form) {
		int[] ids = form.getIds();

		for(int id : ids) {
			RestModel rest = restService.queryById(id);
			if(rest == null) {
				throw new HsysException("该数据不存在");
			}
			
			if(rest.getStatus() != RestStatus.Approval) {
				throw new HsysException("已经批准的数据才能取消");
			}
			rest.setApprovalUser(HsysSecurityContextHolder.getLoginUser());
			rest.setApprovalTime(HsysDate.now());
			rest.setStatus(RestStatus.CancelRequest);
			rest.setUpdate(RestModel.FIELD_APPROVAL_USER_ID);
			rest.setUpdate(RestModel.FIELD_STATUS);
			restService.update(rest);
		}
	}
}
