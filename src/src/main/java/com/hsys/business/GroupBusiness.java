package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.business.beans.GroupTreeNodeBean;
import com.hsys.common.HsysList;
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

	public List<GroupTreeNodeBean> getChildrenGroups(GroupTreeNodeBean bean) {
		GroupModel group = new GroupModel();
		group.setCond(GroupModel.COND_PARENT_ID, bean.getValue());

		List<GroupTreeNodeBean> list = HsysList.New(); 
		
		List<GroupModel> groups = groupService.queryList(group);
		for(GroupModel g : groups) {
			GroupTreeNodeBean b = new GroupTreeNodeBean();
			b.setText(g.getName());
			b.setValue(g.getId());
			
			list.add(b);
		}
		return list;
	}
}
