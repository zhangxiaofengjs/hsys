package com.hsys.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.beans.TreeNodeBean;
import com.hsys.business.forms.GroupJsonDeleteForm;
import com.hsys.business.forms.GroupJsonGetExceptChild;
import com.hsys.business.forms.GroupJsonGetForm;
import com.hsys.business.forms.GroupJsonUpdateForm;
import com.hsys.common.HsysList;
import com.hsys.exception.HsysException;
import com.hsys.models.GroupModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
import com.hsys.services.GroupService;
import com.hsys.services.UserService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
@Component
public class GroupBusiness {
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	
	public List<GroupModel> getGroups() {
		return groupService.queryList(new GroupModel());
	}	
	
	public List<GroupModel> getGroupsExceptChild(GroupJsonGetExceptChild form) {
		List<GroupModel> groupAll = groupService.queryList(new GroupModel());
		//查询正在被编集的group所有的子group
		List<GroupModel> groupChild = groupService.queryChildrenById(form.getId());	
		
		//如果该group没有子group，那么任何group都可以是它的上级组织，返回groupAll
		if(groupChild.size()==0) {
			return groupAll;
		}
		
		//如果该group有子group,那么它的子group不返回
		List<GroupModel> groupRest = HsysList.New();
		for (GroupModel allItem : groupAll) {
			int count = 0;
			
			for (GroupModel childItem : groupChild) {
				if(allItem.getId() == childItem.getId())
					break;
				else
					count++;
			}
			if (count == groupChild.size())
				groupRest.add(allItem);
		}
		return groupRest;	
	}	
	
	public List<TreeNodeBean> getChildrenGroups(TreeNodeBean bean) {
		GroupModel group = new GroupModel();
		group.setCond(GroupModel.COND_PARENT_ID, bean.getValue());

		List<TreeNodeBean> list = HsysList.New(); 
		
		List<GroupModel> groups = groupService.queryList(group);
		for(GroupModel g : groups) {
			TreeNodeBean b = new TreeNodeBean();
			b.setText(g.getName());
			b.setValue(g.getId());
			
			list.add(b);
		}
		return list;
	}
	
	public void add(GroupModel group) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		
		GroupModel groupExist = groupService.queryByName(group.getName());
		
		if(groupExist != null) {
			throw new HsysException("该组织存在:%s", groupExist.getName()); 
		}
		
		GroupModel parent = groupService.queryById(group.getParent().getId());
		if(parent != null) {
			group.setLevel(parent.getLevel()+1);
		}else {
			group.setLevel(0);
		}
		groupService.add(group);		
	}
	
	@Transactional
	public void delete(GroupJsonDeleteForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		
		int[] ids = form.getIds();
		for(int id : ids) {
			GroupModel groupModel = groupService.queryById(id);
			if(groupModel == null) {
				throw new HsysException("该数据不存在");
			}
			
			UserModel user = new UserModel();
			user.setCond(UserModel.COND_GROUP_ID, id);
			List<UserModel> users = userService.queryList(user);
			if(!HsysList.isEmpty(users)) {
				throw new HsysException("删除失败：有员工[" + users.get(0).getName() + "...]属于该部门。");
			}
			
			List<GroupModel> groups = groupService.queryByParentId(id);
			if(!groups.isEmpty()) {
				throw new HsysException("删除失败：有部门属于该部门。");
			}
			
			groupService.deleteById(id);
		}
	}
	
	public GroupModel getGroup(GroupJsonGetForm form) {
		GroupModel groupModel = groupService.queryById(form.getId());
		if(groupModel == null) {
			throw new HsysException("该组织不存在。");
		}
		return groupModel;
	}
	
	public void update(GroupJsonUpdateForm form) {
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		
		//根据id，查出当前编集的组织信息
		GroupModel groupModel = groupService.queryById(form.getId());
		
		if(groupModel == null) {
			throw new HsysException("该组织不存在。");
		}
		
		//修改组织名
		if(!groupModel.getName().equals(form.getName())) {
			if(form.getName().length() <20 && form.getName().length()>0) {
				groupModel.setName(form.getName());
				groupModel.setUpdate(GroupModel.FIELD_NAME);
			}else {
				throw new HsysException("组织名不能为空，且应小于20字");
			}
		}
		
		//修改组织
		if(groupModel.getParent().getId() != form.getParent().getId()) {
			List<GroupModel> groups = groupService.queryChildrenById(form.getId());
			for (GroupModel group : groups) {
				if(group.getId() == form.getParent().getId()) {
					throw new HsysException(group.getName() +"是该部门的子部门。更新失败。");
				}
			}
			GroupModel parent = groupService.queryById(form.getParent().getId());
			if(parent != null) {
				groupModel.setLevel(parent.getLevel()+1);
			}else {
				groupModel.setLevel(0);
			}
			groupModel.setUpdate(GroupModel.FIELD_LEVEL);
			groupModel.setUpdateParentGroup(form.getParent());
		}
		
		//如果没有做修改
		if(groupModel.getName().equals(form.getName()) && groupModel.getParent().getId()== form.getParent().getId()) {
			throw new HsysException("没有做任何修改。");
		}
		groupService.update(groupModel);
	}
	
}
