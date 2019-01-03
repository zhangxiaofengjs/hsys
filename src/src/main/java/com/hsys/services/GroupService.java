package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.mappers.GroupMapper;
import com.hsys.models.GroupModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
@Service
public class GroupService {
	@Autowired
    private GroupMapper groupMapper;
	
	public List<GroupModel> queryList(GroupModel group) {
		return groupMapper.queryList(group);
	}
	
	public void add(GroupModel group) {
		groupMapper.add(group);
	}
	
	public void update(GroupModel group) {
		groupMapper.update(group);
	}

	public GroupModel queryByUserId(int id) {
		GroupModel group = new GroupModel();
		group.setCond(GroupModel.COND_USER_ID, id);

		return queryOne(group);
	}
	
	public GroupModel queryById(int id) {
		GroupModel u = new GroupModel();
		u.setId(id);
		List<GroupModel> us = queryList(u);
		
		if(us.size() == 1) {
			return us.get(0);
		}
		return null;
	}
	
	public GroupModel queryOne(GroupModel group) {
		List<GroupModel> us = queryList(group);
		
		if(us.size() ==1) {
			return us.get(0);
		}
		return null;
	}

	public void setUserGroup(int userId, int grpId) {
		GroupModel group = queryByUserId(userId);
		if(group == null) {
			groupMapper.addUserGroup(userId, grpId);
		} else {
			groupMapper.updateUserGroup(userId, grpId);
		}
	}
}
