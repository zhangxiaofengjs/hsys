package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.exception.HsysException;
import com.hsys.models.UserModel;
import com.hsys.services.UserService;

@Component
public class UserBusiness {
	@Autowired
    private UserService userService;
	
	public List<UserModel> getAllUser() {
		List<UserModel> list = userService.queryList(new UserModel());
		
		return list;
	}

	public void add(UserModel user) {
		//check existance
		if(false) {
			throw new HsysException("该工号存在:%s", user.getNo()); 
		}
		
		userService.add(user);
	}
}
