package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.UserModel;


/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/26
 */
@Mapper
public interface UserMapper {
	List<UserModel> queryList(UserModel user);

	void add(UserModel user);

	void update(UserModel user);

	void changePassword(UserModel user);
}
