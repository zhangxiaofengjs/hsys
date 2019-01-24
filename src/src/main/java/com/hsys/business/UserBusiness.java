package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.business.forms.UserHtmlListForm;
import com.hsys.business.forms.UserJsonUpdateForm;
import com.hsys.common.HsysList;
import com.hsys.exception.HsysException;
import com.hsys.models.GroupModel;
import com.hsys.models.UserModel;
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
	
	public List<UserModel> getUsers(UserHtmlListForm userForm) {
		UserModel user = new UserModel();
		user.setNo(userForm.getNo());
		user.setCond(UserModel.COND_FUZZY_NO, true);

		List<UserModel> list = userService.queryList(user);
		
		for(UserModel u : list) {
			GroupModel group = groupService.queryByUserId(u.getId());
			u.setGroup(group);
		}
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

		user.setPassword("123");
		user.setUpdate(UserModel.FIELD_PASSWORD);
		userService.update(user);
	}

	public UserModel getDetail(String no) {
		UserModel user = new UserModel();
		user.setNo(no);
		user.setCond(UserModel.COND_FUZZY_NO, true);

		UserModel userRet = userService.queryOne(user);
		if(userRet == null) {
			user.setCond(UserModel.COND_FUZZY_NO, false);
			List<UserModel> userListRet = userService.queryList(user);
			userRet = HsysList.first(userListRet);
		}

		if(userRet == null) {
			userRet = new UserModel();
		} else {
			GroupModel group = groupService.queryByUserId(userRet.getId());
			userRet.setGroup(group);
		}
		return userRet;
	}

	public void update(UserJsonUpdateForm userUpdateForm) {
		UserModel user = userService.queryById(userUpdateForm.getId());
		if(user == null) {
			throw new HsysException("该工号不存在。id=" + userUpdateForm.getId()); 
		}

		if("e_name".equals(userUpdateForm.getField())) {
			user.setName(userUpdateForm.getValue());
			user.setUpdate(UserModel.FIELD_NAME);
		}
		else if("e_group".equals(userUpdateForm.getField())) {
			int groupId = Integer.parseInt(userUpdateForm.getValue());
			groupService.setUserGroup(user.getId(), groupId);
			return;
		}
		userService.update(user);
	}
}
