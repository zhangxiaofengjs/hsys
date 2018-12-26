package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hsys.mappers.UserMapper;
import com.hsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/26
 */
@Service
public class UserService {
	@Autowired
    private UserMapper userMapper;
	
	public List<UserModel> queryList(UserModel user) {
		return userMapper.queryList(user);
	}
	
	public void add(UserModel user) {
		userMapper.add(user);
	}
	
	public void update(UserModel user) {
		userMapper.update(user);
	}

	public UserModel queryById(int id) {
		UserModel u = new UserModel();
		u.setId(id);
		List<UserModel> us = queryList(u);
		
		if(us.size() == 1) {
			return us.get(0);
		}
		return null;
	}
	
	public UserModel queryOne(UserModel user) {
		List<UserModel> us = queryList(user);
		
		if(us.size() ==1) {
			return us.get(0);
		}
		return null;
	}
}
