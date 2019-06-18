package com.hsys.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hsys.HsysApplicationContext;
import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.beans.ExtraTimeListBean;
import com.hsys.business.forms.ExtraTimeAddForm;
import com.hsys.business.forms.ExtraTimeApprovalForm;
import com.hsys.business.forms.ExtraTimeDeleteForm;
import com.hsys.business.forms.ExtraTimeDownloadForm;
import com.hsys.business.forms.ExtraTimeGetForm;
import com.hsys.business.forms.ExtraTimeListForm;
import com.hsys.business.forms.ExtraTimeUpdateForm;
import com.hsys.business.forms.UserBasicExtraTimeForm;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysIO;
import com.hsys.common.HsysString;
import com.hsys.config.HsysConfig;
import com.hsys.exception.HsysException;
import com.hsys.io.ExtraTimeWriter;
import com.hsys.models.ExtraTimeModel;
import com.hsys.models.GroupModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.BoolFlag;
import com.hsys.models.enums.ExtraTimeStatus;
import com.hsys.models.enums.ExtraTimeType;
import com.hsys.models.enums.OrderFlag;
import com.hsys.models.enums.ROLE;
import com.hsys.services.ExtraTimeService;
import com.hsys.services.GroupService;

@Component
public class ExtraTimeBusiness {
	@Autowired
    private ExtraTimeService extraTimeService;
	@Autowired
	private ExtraTimeWriter writer;
	@Autowired
	HsysConfig config;
	@Autowired
	private GroupService groupService;
	private void check(ExtraTimeModel extraTime) {
		Date date = new Date();
		if(extraTime.getDate().compareTo(date) == 1) {
			throw new HsysException("加班日期不得大于当前日期"); 
		}
		
		if(extraTime.getStartTime().compareTo(extraTime.getEndTime()) == 1) {
			throw new HsysException("结束时间不得小于开始时间"); 
		}
		
		if(extraTime.getLength()%0.5 !=0 ) {
			throw new HsysException("加班时长最小单位0.5小时"); 
		}
		
		if(extraTime.getLength()<0.5 || extraTime.getLength()>16) {
			throw new HsysException("加班时长超出范围"); 
		}
		
		if (extraTime.getType() > ExtraTimeType.Holiday || extraTime.getType() < ExtraTimeType.Normal) {
			throw new HsysException("种类超出范围");
		}
		
		if(HsysString.isNullOrEmpty(extraTime.getComment())) {
			throw new HsysException("请填写加班备注");
		}
	}
	
	public void add(ExtraTimeAddForm form) {
		ExtraTimeModel extraTime = new ExtraTimeModel();
		int[] meals = form.getMeal();
		extraTime.setMealLunch(BoolFlag.FALSE);
		extraTime.setMealSupper(BoolFlag.FALSE);
		if(meals != null) {
			for(int meal : meals) {
				if(meal == ExtraTimeUpdateForm.MEAL_LUNCH) {
					extraTime.setMealLunch(BoolFlag.TRUE);
					extraTime.setUpdate(ExtraTimeModel.FIELD_MEAL_LUNCH);
				} else if(meal == ExtraTimeUpdateForm.MEAL_SUPPER) {
					extraTime.setMealSupper(BoolFlag.TRUE);
					extraTime.setUpdate(ExtraTimeModel.FIELD_MEAL_SUPPER);
				}
			}
		}
		extraTime.setComment(form.getComment());
		extraTime.setStatus(ExtraTimeStatus.Regist);
		extraTime.setType(form.getType());
		extraTime.setLength(form.getLength());
		extraTime.setDate(form.getDate());
		extraTime.setStartTime(form.getStartTime());
		extraTime.setEndTime(form.getEndTime());
		
		UserModel user;
		if(form.getUserId() == 0) {
			//当前用户
			user = HsysSecurityContextHolder.getLoginUser();
		} else {
			user = new UserModel();
			user.setId(form.getUserId());
		}
		extraTime.setUser(user);
		
		check(extraTime);
		extraTimeService.add(extraTime);
	}
	
	public void add(UserBasicExtraTimeForm form) {
		ExtraTimeModel extraTime = new ExtraTimeModel();
		extraTime.setDate(HsysDate.Today());
		extraTime.setComment(form.getComment());
		extraTime.setEndTime(form.getEndTime());
		float len = HsysDate.hours(form.getStartTime(), form.getEndTime());
		if(HsysDate.isWeekend(HsysDate.Today())) {
			//周末自动扣除半小时午餐
			len -= 0.5;
		}
		extraTime.setLength(len);
		extraTime.setStartTime(form.getStartTime());
		if(form.getMeal() == UserBasicExtraTimeForm.MEAL_LUNCH) {
			extraTime.setMealLunch(BoolFlag.TRUE);
		} else if(form.getMeal() == UserBasicExtraTimeForm.MEAL_SUPPER) {
			extraTime.setMealSupper(BoolFlag.TRUE);
		}
		
		if(HsysDate.isWeekend(HsysDate.Today())) {
			extraTime.setType(ExtraTimeType.Weekend);
		} else {
			extraTime.setType(ExtraTimeType.Normal);
		}
		extraTime.setUser(HsysSecurityContextHolder.getLoginUser());

		check(extraTime);
		extraTimeService.add(extraTime);
	}

	public void delete(ExtraTimeDeleteForm form) {
		int[] ids = form.getIds();
		for(int id : ids) {
			ExtraTimeModel extraTimeModel = extraTimeService.queryById(id);
			if(extraTimeModel == null) {
				throw new HsysException("该数据不存在");
			}
			if(extraTimeModel.getStatus() != ExtraTimeStatus.Regist) {
				throw new HsysException("已经批准的数据不能删除。");
			}
			extraTimeService.deleteById(id);
		}
	}

	public void update(ExtraTimeUpdateForm form) {
		ExtraTimeModel extraTime = extraTimeService.queryById(form.getId());
		if(extraTime == null) {
			throw new HsysException("该加班记录不存在。");
		}
		
		if(extraTime.getStatus() == ExtraTimeStatus.APPROVED) {
			throw new HsysException("已批准不可更改。"); 
		}
		
		int[] meals = form.getMeal();
		int bLunch = BoolFlag.FALSE;
		int bSupper = BoolFlag.FALSE;
		if(meals != null) {
			for(int meal : meals) {
				if(meal == ExtraTimeUpdateForm.MEAL_LUNCH) {
					bLunch = BoolFlag.TRUE;
				}
				if(meal == ExtraTimeUpdateForm.MEAL_SUPPER) {
					bSupper = BoolFlag.TRUE;
				}
			}
		}

		if( bLunch != extraTime.getMealLunch()) {
			extraTime.setMealLunch(bLunch);
			extraTime.setUpdate(ExtraTimeModel.FIELD_MEAL_LUNCH);
		}
		if( bSupper != extraTime.getMealSupper()) {
			extraTime.setMealSupper(bSupper);
			extraTime.setUpdate(ExtraTimeModel.FIELD_MEAL_SUPPER);
		}

		if(extraTime.getLength() != form.getLength()) {
			extraTime.setLength(form.getLength());
			extraTime.setUpdate(ExtraTimeModel.FIELD_LENGTH);
		}
		
		if(!extraTime.getDate().equals(form.getDate())) {
			extraTime.setDate(form.getDate());
			extraTime.setUpdate(ExtraTimeModel.FIELD_DATE);
		}
		
		if(!extraTime.getStartTime().equals(form.getStartTime())) {
			extraTime.setStartTime(form.getStartTime());
			extraTime.setUpdate(ExtraTimeModel.FIELD_START_TIME);
		}
		
		if(!extraTime.getEndTime().equals(form.getEndTime())) {
			extraTime.setEndTime(form.getEndTime());
			extraTime.setUpdate(ExtraTimeModel.FIELD_END_TIME);
		}
		
		if(!extraTime.getComment().equals(form.getComment())) {
			extraTime.setComment(form.getComment());
			extraTime.setUpdate(ExtraTimeModel.FIELD_COMMENT);
		}
		
		if(extraTime.getType()!=form.getType()) {
			extraTime.setType(form.getType());
			extraTime.setUpdate(ExtraTimeModel.FIELD_TYPE);
		}
		
		check(extraTime);
		
		if(extraTime.hasUpdate()) {
			extraTimeService.update(extraTime);
		}
	}

	public ExtraTimeModel getExtratime(ExtraTimeGetForm form) {
		ExtraTimeModel extraTime = extraTimeService.queryById(form.getId());
		if(extraTime == null) {
			throw new HsysException("该加班记录不存在。");
		}
		return extraTime;
	}

	//批准加班
	public void approval(ExtraTimeApprovalForm form) {
			
		int[] ids = form.getIds();
		
		for(int id:ids) {
			ExtraTimeModel extraTime = extraTimeService.queryById(id);
			if(extraTime.getStatus() == ExtraTimeStatus.APPROVED) {
				throw new HsysException("不可重复批准。");
			}
			extraTime.setAppUser(HsysSecurityContextHolder.getLoginUser());
			extraTime.setApprovalTime(HsysDate.now()); //插入批准日期
			extraTime.setStatus(ExtraTimeStatus.APPROVED);
			extraTime.setUpdate(ExtraTimeModel.FIELD_APPROVAL_USER_ID);
			extraTime.setUpdate(ExtraTimeModel.FIELD_STATUS);
			extraTime.setUpdate(ExtraTimeModel.FIELD_APPROVAL_TIME);
			extraTimeService.update(extraTime);
		}
	}

	public ExtraTimeListBean getExtraTimeListBean(ExtraTimeListForm form) {
		ExtraTimeListBean bean = new ExtraTimeListBean();
		
		if(form.isUser()) {
			form.setUserNo(HsysSecurityContextHolder.getLoginUser().getNo());
		}
		else if(form.isView()) {
			//没权限
			if(!HsysSecurityContextHolder.isLoginUserHasAnyRole(ROLE.EXTRATIME_LIST_ALL, ROLE.EXTRATIME_LIST)) {
				return bean;
			}
		}
		else if(form.isApprove()) {
			if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.EXTRATIME_APPROVE)) {
				return bean;
			}
		} else {
			return bean;
		}
		
		ExtraTimeModel extraTime = new ExtraTimeModel();
		if (!HsysString.isNullOrEmpty(form.getUserNo())) {
			UserModel user = new UserModel();
			user.setNo(form.getUserNo());
			extraTime.setUser(user);
			extraTime.setCond(ExtraTimeModel.COND_USER_NO, true);
			extraTime.setCond(ExtraTimeModel.COND_FUZZY_USER_NO, true);
		}
		
		if(form.isApprove()) {
			extraTime.setStatus(ExtraTimeStatus.Regist);
			extraTime.setCond(ExtraTimeModel.COND_STATUS, true);
		}

		if((form.isApprove() || form.isView()) &&
			!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.EXTRATIME_LIST_ALL)) {
				extraTime.setCond(ExtraTimeModel.COND_GROUP_ID,
				HsysSecurityContextHolder.getLoginUser().getGroup().getId());
		}
		
		if(form.getGroupId() != 0) {
			List<Integer> groupIds = groupService.queryChildrenIdsById(form.getGroupId());
			groupIds.add(form.getGroupId());
			extraTime.setCond(ExtraTimeModel.COND_GROUP_IDS, groupIds);

			//页面需要显示名字，form再设定
			GroupModel group = groupService.queryById(form.getGroupId());
			if(group != null) {
				form.setGroupName(group.getName());
			}
		}
		extraTime.setCond(ExtraTimeModel.COND_START_DATE, form.getStartDate());
		extraTime.setCond(ExtraTimeModel.COND_END_DATE, form.getEndDate());

		extraTime.addSortOrder(ExtraTimeModel.ORDER_USER_NO, OrderFlag.ASC);
		extraTime.addSortOrder(ExtraTimeModel.ORDER_DATE, OrderFlag.ASC);
		List<ExtraTimeModel> list = extraTimeService.queryList(extraTime);
		
		bean.setList(list);
		
		for(ExtraTimeModel ex : list) {
			switch(ex.getType()) {
			case ExtraTimeType.Normal:
				bean.addSumNormal(ex.getLength());
				break;
			case ExtraTimeType.Holiday:
				bean.addSumHoliday(ex.getLength());
				break;
			case ExtraTimeType.Weekend:
				bean.addSumWeekend(ex.getLength());
				break;
			}
		}
		return bean;
	}

	public ResponseEntity<byte[]> download(ExtraTimeDownloadForm form) throws IOException {
		Resource resource = HsysApplicationContext.getResource("classpath:/attachments/extra-time-template.xlsx"); 
		InputStream is = resource.getInputStream();

		String tempFile = config.getUploadFolder().getTempFolder() + "\\" + 
				HsysDate.format(form.getStartDate(), "yyyyMMdd") + "_" +
				HsysDate.format(form.getEndDate(), "yyyyMMdd") + ".xlsx";
		
		writer.setUserNo(null);
		writer.setGroupId(0);
		if(form.isUser() ||
			!HsysSecurityContextHolder.isLoginUserHasAnyRole(ROLE.EXTRATIME_LIST, ROLE.EXTRATIME_LIST_ALL)) {
			writer.setUserNo(HsysSecurityContextHolder.getLoginUser().getNo());
		} else {
			if(!HsysString.isNullOrEmpty(form.getUserNo())) {
				writer.setUserNo(form.getUserNo());
			}
			if(!HsysSecurityContextHolder.isLoginUserHasAnyRole(ROLE.EXTRATIME_LIST_ALL)) {
				writer.setGroupId(HsysSecurityContextHolder.getLoginUser().getGroup().getId());
			}
		}
		writer.setStartDate(form.getStartDate());
		writer.setEndDate(form.getEndDate());
		writer.setTemplateFileStream(is);
		writer.write(tempFile);

		is.close();
		
		ResponseEntity<byte[]> response = HsysIO.downloadHttpFile(tempFile);
		HsysIO.delete(tempFile);
		
		return response;
	}
}

