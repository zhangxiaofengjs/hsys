package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.models.GroupModel;
import com.hsys.services.GroupService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
@Component
public class GroupBusiness {
	@Autowired
	private GroupService groupService;
	
	public List<GroupModel> getGroups() {
		return groupService.queryList(new GroupModel());
	}
}
