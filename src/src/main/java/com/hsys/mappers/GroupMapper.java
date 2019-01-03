package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hsys.models.GroupModel;


/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
@Mapper
public interface GroupMapper {
	List<GroupModel> queryList(GroupModel group);

	void add(GroupModel group);

	void update(GroupModel group);

	void addUserGroup(@Param("userId") int userId, @Param("groupId")int grpId);
	
	void updateUserGroup(@Param("userId") int userId, @Param("groupId")int grpId);
}
