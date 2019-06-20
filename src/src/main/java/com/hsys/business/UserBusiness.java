package com.hsys.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hsys.HsysApplicationContext;
import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.beans.UserBasicBean;
import com.hsys.business.beans.UserDetailBean;
import com.hsys.business.forms.UserDownloadForm;
import com.hsys.business.forms.UserHtmlDetailForm;
import com.hsys.business.forms.UserHtmlListForm;
import com.hsys.business.forms.UserJsonChangePwdForm;
import com.hsys.business.forms.UserJsonGetForm;
import com.hsys.business.forms.UserJsonInitPwdForm;
import com.hsys.business.forms.UserJsonUpdateForm;
import com.hsys.common.HsysDate;
import com.hsys.common.HsysIO;
import com.hsys.common.HsysList;
import com.hsys.common.HsysString;
import com.hsys.config.HsysConfig;
import com.hsys.exception.HsysException;
import com.hsys.io.UserWriter;
import com.hsys.models.CompanyModel;
import com.hsys.models.ExtraTimeModel;
import com.hsys.models.GroupModel;
import com.hsys.models.HolidayModel;
import com.hsys.models.RestModel;
import com.hsys.models.SchoolModel;
import com.hsys.models.UserModel;
import com.hsys.models.UserRoleModel;
import com.hsys.models.enums.BoolFlag;
import com.hsys.models.enums.ROLE;
import com.hsys.security.HsysPasswordEncoder;
import com.hsys.services.ExtraTimeService;
import com.hsys.services.GroupService;
import com.hsys.services.HolidayService;
import com.hsys.services.RestService;
import com.hsys.services.UserService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
@Component
public class UserBusiness {
	@Autowired
    private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private HsysPasswordEncoder encoder;
	@Autowired
	private ExtraTimeService extraTimeService;
	@Autowired
	private RestService restServices;
	@Autowired
	private HolidayService holidayService;
	@Autowired
	HsysConfig config;
	@Autowired
	UserWriter writer;
	public List<UserDetailBean> getUsers(UserHtmlListForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.USER_EDIT)) {
			form.setView(true);
		}

		UserModel user = new UserModel();
		if(!HsysString.isNullOrEmpty(form.getNo())) {
			user.setNo(form.getNo());
			user.setCond(UserModel.COND_NO, true);
			user.setCond(UserModel.COND_FUZZY_NO, true);
		}
		
		if(form.isView() || form.getExitState() == 1) {
			user.setCond(UserModel.COND_EXIT_DATE_NULL, true);
		} else if(form.getExitState() == 2) {
			user.setCond(UserModel.COND_EXIT_DATE_NOT_NULL, true);
		}

		if(form.isView()) {
			//参考画面，不显示admin
			user.setCond(UserModel.COND_HIDDEN_FALG, BoolFlag.FALSE);
		}
		
		if(form.getGroupId() != 0) {
			List<Integer> groupIds = groupService.queryChildrenIdsById(form.getGroupId());
			groupIds.add(form.getGroupId());
			user.setCond(UserModel.COND_GROUP_IDS, groupIds);

			//页面需要显示名字，form再设定
			GroupModel group = groupService.queryById(form.getGroupId());
			if(group != null) {
				form.setGroupName(group.getName());
			}
		}
		
		List<UserModel> list = userService.queryList(user);
		List<UserDetailBean> listRet = HsysList.New();
		HashMap<Integer, String> groupNameCache = new HashMap<Integer, String>();
		for(UserModel u : list) {
			UserDetailBean bean = new UserDetailBean();
			BeanUtils.copyProperties(u, bean);
			
			GroupModel grp = u.getGroup();
			if(grp != null) {
				if(!groupNameCache.containsKey(grp.getId())) {
					String grpName = getGroupFullName(grp);
					groupNameCache.put(grp.getId(), grpName);
				}
				bean.setGroupFullName(groupNameCache.get(grp.getId()));
			}
			
			listRet.add(bean);
		}
		return listRet;
	}
	
	private String getGroupFullName(GroupModel group) {
		if(group == null) {
			return "";
		}
		String str = group.getName();
		GroupModel parent = groupService.queryParent(group.getId());
		while(parent != null) {
			str = parent.getName()  + " > " + str;
			parent = groupService.queryParent(parent.getId());
		}
		
		return str;
	}
	
	public List<UserModel> getJsonUsers(UserJsonGetForm userForm) {
		UserModel user = new UserModel();
		user.setNo(userForm.getNo());
		user.setCond(UserModel.COND_FUZZY_NO, true);
		user.setCond(UserModel.COND_EXIT_DATE_NULL, true);

		List<UserModel> list = userService.queryList(user);
		return list;
	}

	public void add(UserModel user) {
		
		UserModel userExist = userService.queryByNo(user.getNo());

		//检测工号是否已经存在
		if(userExist != null) {
			throw new HsysException("该工号存在:%s, %s", userExist.getNo(), userExist.getName()); 
		}
		if (user.getName().length()<9) {
			String pwd = encoder.encode("123");
			user.setPassword(pwd);
	
			userService.add(user);
		}else {
			throw new HsysException("姓名长度不超过8");
		}
		
	}
	
	//初始化密码
	public void initPwd(UserJsonInitPwdForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.USER_EDIT)) {
			throw new HsysException("权限不足");
		}
		
		int[] ids = form.getIds();
		
		for(int id : ids) {
			UserModel user = new UserModel();
			user.setId(id);
			String pwd = encoder.encode("123");
			user.setPassword(pwd);
			user.setUpdate(UserModel.FIELD_PASSWORD);
			userService.update(user);
		}
	}

	public UserDetailBean getDetail(UserHtmlDetailForm form) {
		UserModel user = userService.queryByNo(form.getNo());
		if(user == null) {
			throw new HsysException("该工号不存在。id=" + form); 
		}

		UserDetailBean bean = new UserDetailBean();
		BeanUtils.copyProperties(user, bean);
		String grpName = getGroupFullName(user.getGroup());
		bean.setGroupFullName(grpName);
		return bean;
	}

	public void update(UserJsonUpdateForm userUpdateForm) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.USER_EDIT)) {
			throw new HsysException("权限不足"); 
		}
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.USER_FULL_INFO)) {
			userUpdateForm.setView(true);
		}
		
		UserModel user = new UserModel();
		user.setId(userUpdateForm.getId());
		user.setCond(UserModel.COND_ID, true);
		user.setCond(UserModel.COND_CONTAINS_DISABLE_ROLE, true);
		user = userService.queryOne(user);
		if(user == null) {
			throw new HsysException("该工号不存在。id=" + userUpdateForm.getId()); 
		}
		
		if("e_name".equals(userUpdateForm.getField())) {
			user.setName(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_NAME);
		} else if("e_sex".equals(userUpdateForm.getField())) {
			int sex = Integer.parseInt(userUpdateForm.getValue());
			user.setSex(sex);
			user.setUpdate(UserModel.FIELD_SEX);
		}else if ("e_spelling".equals(userUpdateForm.getField())) {
			if(userUpdateForm.getValue().length()<51) {
				user.setSpelling(userUpdateForm.getValue());
				user.setUpdate(UserModel.FIELD_SEPLLING);
			}else {
				throw new HsysException("拼写过长");
			}
		} else if("e_degree".equals(userUpdateForm.getField())) {
			int degree = Integer.parseInt(userUpdateForm.getValue());
			user.setDegree(degree);
			user.setUpdate(UserModel.FIELD_DEGREE);	
		} else if("e_mail".equals(userUpdateForm.getField())) {
			if (HsysString.isEmail(userUpdateForm.getValue())&&userUpdateForm.getValue().length()<31) {
				user.setMail(userUpdateForm.getValue());
				user.setUpdate(UserModel.FIELD_MAIL);
			}else {
				throw new HsysException("邮箱格式不正确");
			}
		} else if("e_major".equals(userUpdateForm.getField())) {
			if(userUpdateForm.getValue().length()<17) {
				user.setMajor(userUpdateForm.getValue());
				user.setUpdate(UserModel.FIELD_MAJOR);
			}else {
				throw new HsysException("专业长度不得超过16");
			}
		} else if("e_place".equals(userUpdateForm.getField())) {
			user.setPlace(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_PLACE);
		} else if("e_companyName".equals(userUpdateForm.getField())) {
			int id = Integer.parseInt(userUpdateForm.getValue());
			CompanyModel c = new CompanyModel();
			c.setId(id);
			user.setCompany(c);
			user.setUpdate(UserModel.FIELD_COMPANY_NAME);
		} else if("e_phoneNumber".equals(userUpdateForm.getField())) {
			if(userUpdateForm.getValue().length()<24) {
				user.setPhoneNumber(userUpdateForm.getValue());
				user.setUpdate(UserModel.FIELD_PHONE_NUMBER);
			}else {
				throw new HsysException("手机号码长度不正确");
			}
		} else if("e_address".equals(userUpdateForm.getField())) {
			if(userUpdateForm.getValue().length()<21) {
				user.setAddress(userUpdateForm.getValue());
				user.setUpdate(UserModel.FIELD_ADDRESS);	
			}else {
				throw new HsysException("地址长度不得超过20" ); 
			}
		} else if("e_idNumber".equals(userUpdateForm.getField())) {
			if(HsysString.len(userUpdateForm.getValue()) != 18) {
				throw new HsysException("身份证号必须是18位");
			}
			user.setIdNumber(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_ID_NUMBER);
		} else if("e_school".equals(userUpdateForm.getField())) {
			int id = Integer.parseInt(userUpdateForm.getValue());
			SchoolModel s = new SchoolModel();
			s.setId(id);
			user.setSchool(s);
			user.setUpdate(UserModel.FIELD_SCHOOL);
		} else if("e_graduateDate".equals(userUpdateForm.getField())) {
			Date date = HsysDate.tryParse(userUpdateForm.getValue(), "yyyy-MM-dd");
			user.setGraduateDate(date);
			user.setUpdate(UserModel.FIELD_GRADUATE_DATE);
		} else if("e_enterDate".equals(userUpdateForm.getField()) ) {
			Date date1 = HsysDate.tryParse(userUpdateForm.getValue(), "yyyy-MM-dd");
			user.setEnterDate(date1);
			user.setUpdate(UserModel.FIELD_ENTER_DATE);
		}
		else if("e_exitDate".equals(userUpdateForm.getField())) {
			Date exitdate = HsysDate.tryParse(userUpdateForm.getValue(), "yyyy-MM-dd");		
			Date enterDate = user.getEnterDate();
			
			//离职日期在入职日期之后
			if(dateCheck(enterDate, exitdate)) {
				user.setExitDate(exitdate);	
				user.setUpdate(UserModel.FIELD_EXIT_DATE);
			}else {
				throw new HsysException("离职日期需在入职日期之后设定");
			}
		} else if("e_group".equals(userUpdateForm.getField())) {
			int groupId = Integer.parseInt(userUpdateForm.getValue());
			groupService.setUserGroup(user.getId(), groupId);
			return;
		} else if("e_role".equals(userUpdateForm.getField())) {
			String[] roles = userUpdateForm.getValue().split(",");
			List<String> rList =  Arrays.asList(roles);
			for(String r : ROLE.ALL) {
				UserRoleModel ur = UserBusiness.getRole(user.getRoles(), r);
				if(rList.contains(r)) {
					if(ur == null) {
						userService.addRole(user.getId(), r);
					} else {
						userService.updateRole(user.getId(), r, true);
					}
				} else {
					if(ur != null) {
						userService.updateRole(user.getId(), r, false);
					}
				}
			}
			return;
		} else {
			throw new HsysException("想定以外的更新。");
		}
		userService.update(user);
	}
	
	
	//比较入职日期和离职日期
	public  boolean dateCheck(Date enterdate,Date exitdate) {
		if (enterdate.compareTo(exitdate)<0) {
			return true;
		}else {
			return false;
		}	
	}
	
	public void changePwd(UserJsonChangePwdForm form) {
		UserModel user = HsysSecurityContextHolder.getLoginUser();
		if(user == null) {
			throw new HsysException("未登录。"); 
		}
		
		user = userService.queryByNoWithPassword(user.getNo());
		if(user == null) {
			throw new HsysException("该工号不存在。"); 
		}
		
		if(form.getPassword2() == null || !form.getPassword2().equals(form.getPassword3())) {
			throw new HsysException("新密码两次输入不一致。");
		}
		
		if(!encoder.matches(form.getPassword(), user.getPassword())) {
			throw new HsysException("当前密码错误。");
		}
		
		String pwd = encoder.encode(form.getPassword2());
		user.setPassword(pwd);
		user.setUpdate(UserModel.FIELD_PASSWORD);
		userService.update(user);
	}
	
	public static UserRoleModel getRole(List<UserRoleModel> roles, String role) {
		if(roles == null) {
			return null;
		}
		
		for(UserRoleModel r : roles) {
			if(r.getRole().equals(role)) {
				return r;
			}
		}
		return null;
	}
	
	public static boolean hasRole(List<UserRoleModel> roles, String role) {
		return getRole(roles, role) != null;
	}

	public UserBasicBean getBasicInfo() {
		Date startDate = HsysDate.startOfWorkMonth();
		Date endDate = HsysDate.endOfWorkMonth();
		
		UserBasicBean bean = new UserBasicBean();
		bean.setYear(HsysDate.format(HsysDate.Today(),"YYYY"));
		bean.setMonth(HsysDate.format(HsysDate.Today(), "MM"));
		bean.setDate(HsysDate.format(startDate, "MM/dd") + "～" + HsysDate.format(endDate, "MM/dd"));
		UserModel userModel = HsysSecurityContextHolder.getLoginUser();
		
		ExtraTimeModel extraTimeModel = new ExtraTimeModel();
		extraTimeModel.setCond(ExtraTimeModel.COND_USER_ID, userModel.getId());
		extraTimeModel.setCond(ExtraTimeModel.COND_START_DATE, startDate);
		extraTimeModel.setCond(ExtraTimeModel.COND_END_DATE, endDate);
		
		float totleExTime = extraTimeService.extraTimeTotal(extraTimeModel);
		bean.setExtraTimeTotal(totleExTime);
		
		RestModel restModel = new RestModel();
		restModel.setCond(RestModel.COND_USER_ID, userModel.getId());
		restModel.setCond(RestModel.COND_DATE_START, startDate);
		restModel.setCond(RestModel.COND_DATE_END, endDate);
		
		float totleReTime = restServices.restTimeTotal(restModel);
		bean.setRestTimeTotal(totleReTime);

		GroupModel group = HsysSecurityContextHolder.getLoginUser().getGroup();
		if(group != null) {
			bean.setGroupName(getGroupFullName(group));
		}
		
		return bean;
	}

	public List<HolidayModel> getHoliday() {
		HolidayModel h = new HolidayModel();
		List<HolidayModel> list = holidayService.queryList(h);
		for(HolidayModel holiday : list){
			holiday.setMonth(holiday.getDate().getMonth()+1);
		}
		return list;
	}

	public ResponseEntity<byte[]> download(UserDownloadForm form) throws IOException{
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.USER_FULL_INFO)) {
			form.setView(true);
		}
		Resource resource = HsysApplicationContext.getResource("classpath:/attachments/user-template.xlsx"); 
		InputStream is = resource.getInputStream();
		String tempFile = config.getUploadFolder().getTempFolder() + "\\" + "用户信息表.xlsx";	//生成文件名
		writer.setUserNo(form.getNo());
		writer.setTemplateFileStream(is);
		writer.setView(form.isView());
		writer.write(tempFile);
		is.close();
		ResponseEntity<byte[]> response = HsysIO.downloadHttpFile(tempFile);
		HsysIO.delete(tempFile);
		return response;
	}
}
