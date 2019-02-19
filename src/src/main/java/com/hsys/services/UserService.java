package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.UserMapper;
import com.hsys.models.UserModel;
import com.hsys.models.UserRoleModel;
import com.hsys.models.enums.BoolFlag;

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
		u.setCond(UserModel.COND_ID, true);

		return queryOne(u);
	}
	
	public UserModel queryByNo(String no) {
		UserModel u = new UserModel();
		u.setNo(no);
		u.setCond(UserModel.COND_NO, true);

		return queryOne(u);
	}
	
	public UserModel queryByNoWithPassword(String no) {
		UserModel u = new UserModel();
		u.setNo(no);
		u.setCond(UserModel.COND_NO, true);
		u.setCond(UserModel.FIELD_PASSWORD, true);
	
		return queryOne(u);
	}

	public UserModel queryOne(UserModel user) {
		List<UserModel> us = queryList(user);
		
		if(us.size() == 1) {
			return us.get(0);
		}
		return null;
	}

	public void updateRole(int id, String role, boolean enable) {
		UserRoleModel ur = new UserRoleModel();
		ur.setRole(role);
		ur.setEnable(enable ? BoolFlag.TRUE : BoolFlag.FALSE);
		ur.setCond(UserRoleModel.COND_USER_ID, id);
		userMapper.updateRole(ur);
	}

	public void addRole(int id, String role) {
		UserRoleModel ur = new UserRoleModel();
		ur.setRole(role);
		ur.setEnable(BoolFlag.TRUE);
		ur.setCond(UserRoleModel.COND_USER_ID, id);
		userMapper.addRole(ur);
	}
}
