package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.common.HsysList;
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
		groupMapper.updateGroup(group);
	}
	

	public GroupModel queryByUserId(int id) {
		GroupModel group = new GroupModel();
		group.setCond(GroupModel.COND_USER_ID, id);

		return queryOne(group);
	}
	
	public GroupModel queryById(int id) {
		GroupModel u = new GroupModel();
		u.setId(id);
		u.setCond(GroupModel.COND_ID, true);
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

	public GroupModel queryParent(int id) {
		return queryParent(id, -1);
	}
	
	/*
	 * 查找指定level的亲
	 */
	public GroupModel queryParent(int id, int level) {
		GroupModel group = queryById(id);
		if(group == null) {
			return null;
		}

		GroupModel parent = group.getParent();
		group = queryById(parent.getId());
		if(group == null) {
			return null;
		}
		
		if(level == -1 || group.getLevel() == level) {
			return group;
		}
		
		return queryParent(group.getId(), level);
	}

	public List<GroupModel> queryChildrenById(int groupId) {
		GroupModel group = new GroupModel();
		group.setCond(GroupModel.COND_PARENT_ID, groupId);

		List<GroupModel> rets = HsysList.New();
		
		List<GroupModel> groups = queryList(group);
		if(groups != null) {
			for(GroupModel g : groups) {
				rets.add(g);
				rets.addAll(queryChildrenById(g.getId()));
			}
		}
		
		return rets;
	}
	
	public List<Integer> queryChildrenIdsById(int groupId) {
		List<GroupModel> groups = queryChildrenById(groupId);
		
		List<Integer> groupIds = HsysList.New();
		for(GroupModel g : groups) {
			groupIds.add(g.getId());
		}
		return groupIds;
	}
	
	//通过组织名查询
	public GroupModel queryByName(String name) {
		GroupModel group = new GroupModel();
		group.setName(name);
		group.setCond(GroupModel.COND_NAME, true);
		
		List<GroupModel> us = queryList(group);
		if(us.size() == 1) {
			return us.get(0);
		}
		return null;
		
	}
	
	//通过id删除
	public void deleteById(int id) {
		GroupModel group = new GroupModel();
		group.setId(id);
		group.setCond(GroupModel.COND_ID, true);
		groupMapper.delete(group);		
	}
	
	//查询组织id
	public List<GroupModel> queryByParentId(int id){
		GroupModel groupModel = new GroupModel();
		groupModel.setParentId(id);
		groupModel.setCond(GroupModel.COND_PARENT_ID, id);
		
		return queryList(groupModel);
	}
	
}
