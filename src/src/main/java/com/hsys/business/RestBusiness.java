package com.hsys.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.beans.RestListBean;
import com.hsys.business.forms.RestHtmlListForm;
import com.hsys.business.forms.RestJsonAddForm;
import com.hsys.business.forms.RestJsonApproveForm;
import com.hsys.business.forms.RestJsonCancelRequestForm;
import com.hsys.business.forms.RestJsonDeleteForm;
import com.hsys.business.forms.RestJsonGetForm;
import com.hsys.business.forms.RestJsonRejectForm;
import com.hsys.business.forms.RestJsonUpdateForm;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysString;
import com.hsys.exception.HsysException;
import com.hsys.models.GroupModel;
import com.hsys.models.RestModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
import com.hsys.models.enums.RestStatus;
import com.hsys.models.enums.RestType;
import com.hsys.services.GroupService;
import com.hsys.services.RestService;

/**
 * @author: hancaipeng
 * @version: 2019/01/22
 */
@Component
public class RestBusiness {
	@Autowired
	private RestService restService;
	@Autowired
	private GroupService groupService;
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
		
		if ( rest.getLen() < 1) {
			throw new HsysException("请假时长超出范围");
		}
		
		Date date0 = rest.getDateStart();
		Date date1 = rest.getDateEnd();
		int days = (int) ((date1.getTime() - date0.getTime()) / (1000*3600*24)); //计算两个日期的毫秒数，他们的差除以一天的毫秒数，即可得到我们想要的两个日期相差的天数。
	
		if( (days+1)*8 < rest.getLen()) {
			throw new  HsysException((days+1)+"天请假时间不能超过"+(days+1)*8+"小时");
		}
		
		if (rest.getType() == 0) {
			if (rest.getLen() %4!=0) {
				throw new HsysException("休假时长只能是4小时的倍数");
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
	
	
	public RestListBean getRestListBean(RestHtmlListForm form) {
		RestListBean bean = new RestListBean();
		if(form.isApprove()) {
			//审核页面，检查审核权限
			if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.REST_APPROVE)) {
				form.setApprove(false);
				return bean;
			}
		}
		else if(form.isView()) {
			//检查一览权限
			if(!HsysSecurityContextHolder.isLoginUserHasAnyRole(ROLE.REST_LIST, ROLE.REST_LIST_ALL)) {
				form.setView(false);
				return bean;
			}
		} else if(form.isUser()) {
			form.setUserNo(HsysSecurityContextHolder.getLoginUser().getNo());
		} else {
			return bean;
		}
		
		RestModel rest = new RestModel();
		if (!HsysString.isNullOrEmpty(form.getUserNo())) {
			UserModel user = new UserModel();
			user.setNo(form.getUserNo());
			rest.setUser(user);
			rest.setCond(RestModel.COND_USER_NO, true);
			rest.setCond(RestModel.COND_FUZZY_USER_NO, true);
		}
		
		if(form.isApprove()) {
			rest.setStatus(RestStatus.Regist);
			rest.setCond(RestModel.FIELD_STATUS, true);
		}

		if((form.isApprove() || form.isView()) &&
			!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.REST_LIST_ALL)) {
			rest.setCond(RestModel.COND_GROUP_ID,
				HsysSecurityContextHolder.getLoginUser().getGroup().getId());
		}
		
		if(form.getGroupId() != 0) {
			List<Integer> groupIds = groupService.queryChildrenIdsById(form.getGroupId());
			groupIds.add(form.getGroupId());
			rest.setCond(RestModel.COND_GROUP_IDS, groupIds);

			//页面需要显示名字，form再设定
			GroupModel group = groupService.queryById(form.getGroupId());
			if(group != null) {
				form.setGroupName(group.getName());
			}
		}
		
		Date d1 = form.getDateStart();
		d1 = HsysDate.startOfDay(d1);
		rest.setDateStart(d1);
		rest.setCond(RestModel.COND_DATE_START, true);
		
		Date d2 = form.getDateEnd();
		d2 = HsysDate.endOfDay(d2);
		rest.setDateEnd(d2);
		rest.setCond(RestModel.COND_DATE_END, true);

		List<RestModel> list = restService.queryList(rest);
		
		bean.setList(list);
		
		for(RestModel rx : list) {
			switch(rx.getType()) {
			case RestType.Vacation:
				bean.addSumVacation(rx.getLen());
				break;
			case RestType.Sick:
				bean.addSumSick(rx.getLen());
				break;
			case RestType.Leave:
				bean.addSumLeave(rx.getLen());
				break;
			case RestType.Marriage:
				bean.addSumMarriage(rx.getLen());
				break;
			case RestType.Funeral:
				bean.addSumFuneral(rx.getLen());
				break;
			case RestType.Public:
				bean.addSumPublic(rx.getLen());
				break;
			}
		}
		return bean;
	}

}
