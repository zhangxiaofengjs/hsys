package com.hsys.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.business.forms.UserHtmlDetailForm;
import com.hsys.business.forms.UserHtmlListForm;
import com.hsys.business.forms.UserJsonUpdateForm;
import com.hsys.common.HsysDate;
import com.hsys.exception.HsysException;
import com.hsys.models.UserModel;
import com.hsys.security.HsysPasswordEncoder;
import com.hsys.services.GroupService;
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
	
	public List<UserModel> getUsers(UserHtmlListForm userForm) {
		UserModel user = new UserModel();
		user.setNo(userForm.getNo());
		user.setCond(UserModel.COND_FUZZY_NO, true);

		List<UserModel> list = userService.queryList(user);
		return list;
	}

	public void add(UserModel user) {
		UserModel userExist = userService.queryByNo(user.getNo());

		//检测工号是否已经存在
		if(userExist != null) {
			throw new HsysException("该工号存在:%s, %s", userExist.getNo(), userExist.getName()); 
		}

		userService.add(user);
	}

	public void initPwd(UserModel user) {
		UserModel userExist = userService.queryById(user.getId());
		if(userExist == null) {
			throw new HsysException("该工号不存在。id=" + user.getId()); 
		}

		String pwd = encoder.encode("123");
		user.setPassword(pwd);
		user.setUpdate(UserModel.FIELD_PASSWORD);
		userService.update(user);
	}

	public UserModel getDetail(UserHtmlDetailForm form) {
		UserModel user = userService.queryByNo(form.getNo());
		if(user == null) {
			throw new HsysException("该工号不存在。id=" + form); 
		}

		return user;
	}

	public void update(UserJsonUpdateForm userUpdateForm) {
		UserModel user = userService.queryById(userUpdateForm.getId());
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
		} else if("e_degree".equals(userUpdateForm.getField())) {
			int degree = Integer.parseInt(userUpdateForm.getValue());
			user.setDegree(degree);
			user.setUpdate(UserModel.FIELD_DEGREE);
		} else if("e_mail".equals(userUpdateForm.getField())) {
			user.setMail(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_MAIL);
		} else if("e_major".equals(userUpdateForm.getField())) {
			user.setMajor(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_MAJOR);
		} else if("e_place".equals(userUpdateForm.getField())) {
				user.setPlace(userUpdateForm.getValue());
				user.setUpdate(UserModel.FIELD_PLACE);
		} else if("e_phoneNumber".equals(userUpdateForm.getField())) {
			user.setPhoneNumber(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_PHONE_NUMBER);
		} else if("e_address".equals(userUpdateForm.getField())) {
			user.setAddress(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_ADDRESS);
		} else if("e_idNumber".equals(userUpdateForm.getField())) {
			user.setIdNumber(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_ID_NUMBER);
		} else if("e_school".equals(userUpdateForm.getField())) {
			user.setSchool(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_SCHOOL);
		} else if("e_graduateDate".equals(userUpdateForm.getField())) {
			Date date = HsysDate.parse(userUpdateForm.getValue(), "yyyy-MM-dd");
			user.setGraduateDate(date);
			user.setUpdate(UserModel.FIELD_GRADUATE_DATE);
		} else if("e_enterDate".equals(userUpdateForm.getField())) {
			Date date = HsysDate.parse(userUpdateForm.getValue(), "yyyy-MM-dd");
			user.setEnterDate(date);
			user.setUpdate(UserModel.FIELD_ENTER_DATE);
		} else if("e_exitDate".equals(userUpdateForm.getField())) {
			Date date = HsysDate.parse(userUpdateForm.getValue(), "yyyy-MM-dd");
			user.setExitDate(date);
			user.setUpdate(UserModel.FIELD_EXIT_DATE);
		} else if("e_group".equals(userUpdateForm.getField())) {
			int groupId = Integer.parseInt(userUpdateForm.getValue());
			groupService.setUserGroup(user.getId(), groupId);
			return;
		}
		
		userService.update(user);
	}
}
