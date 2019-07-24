package com.hsys.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.HsysSecurityContextHolder;
import com.hsys.business.forms.ProjectJsonDeleteForm;
import com.hsys.business.forms.ProjectJsonGetForm;
import com.hsys.business.forms.ProjectJsonUpdateForm;
import com.hsys.business.forms.ProjectLeaderAddForm;
import com.hsys.business.forms.ProjectLeaderDeleteForm;
import com.hsys.common.HsysList;
import com.hsys.exception.HsysException;
import com.hsys.models.ExpenseReceiptModel;
import com.hsys.models.ProjectModel;
import com.hsys.models.UserModel;
import com.hsys.models.enums.ROLE;
import com.hsys.services.ExpenseReceiptService;
import com.hsys.services.ProjectService;
import com.hsys.services.UserService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/07
 */
@Component
public class ProjectBusiness {
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ExpenseReceiptService expenseReceiptService;
	
	@Autowired
	private UserService userService;
	
	public List<ProjectModel> getProjects() {
		List<ProjectModel> projects = projectService.queryList(new ProjectModel());
		return projects;
	}

	public void addLeader(ProjectLeaderAddForm form) {
		//权限判定
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		//未选择负责者的场合
		if(form.getUserId()==0)
		{
			throw new HsysException("请选择负责者"); 
		}
		
		int projectId = form.getProjectId();
		int userId = form.getUserId();
		ProjectModel project = new ProjectModel();
		project.setCond(ProjectModel.COND_PROJECT_ID, projectId);
		project.setCond(ProjectModel.COND_LEADER_ID, userId);
		List<UserModel> leaderExist = projectService.queryProjectLeaders(project);
		//检测负责者是否已经存在
		if(!HsysList.isEmpty(leaderExist)) {
			throw new HsysException("该负责者已存在"); 
		}
		projectService.addLeader(projectId, userId);
	}

	public void deleteLeader(ProjectLeaderDeleteForm form) {
		//权限判定
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		
		int projectId = form.getProjectId();
		int userId = form.getUserId();
		ProjectModel project = new ProjectModel();
		project.setCond(ProjectModel.COND_PROJECT_ID, projectId);
		project.setCond(ProjectModel.COND_LEADER_ID, userId);
		List<UserModel> leaderExist = projectService.queryProjectLeaders(project);
		//检测负责者是否存在
		if(HsysList.isEmpty(leaderExist)) {
			throw new HsysException("该数据不存在"); 
		}
		projectService.deleteLeader(projectId, userId);		
	}

	public void addProject(ProjectModel project) {
		//权限判定
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		
		ProjectModel projectExist = projectService.queryByNo(project.getNo());
		//检测项目编号是否已经存在
		if(projectExist != null) {
			throw new HsysException("该项目信息存在,编号:%s", projectExist.getNo()); 
		}
		//检测信息长度
		if(project.getNo().length()>50) {
			throw new HsysException("该项目编号过长"); 
		}
		if(project.getName().length()>50) {
			throw new HsysException("该项目名过长"); 
		}
		
		try {
			Float.parseFloat(project.getFunds());
		}catch(Exception e) {
			throw new HsysException("经费格式不符合规范"); 
		}
		
		projectService.addProject(project);
		
	}

	@Transactional
	public void deleteProjects(ProjectJsonDeleteForm form) {
		//权限判定
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		int[] ids = form.getIds();
		//初始化已被利用的项目list
		List<String> errProjects = new ArrayList<>();
		for(int id : ids) {
			//排他检证
			ProjectModel project = projectService.queryById(id);
			if(project == null){
				throw new HsysException("该数据不存在");
			}
			
			ExpenseReceiptModel recepit = new ExpenseReceiptModel();
			List<Integer> checkIds = HsysList.New();
			checkIds.add(id);
			recepit.setCond(ExpenseReceiptModel.COND_PROJECT_IDS, checkIds);
			//项目对应id是否在报销单中被利用
			List<ExpenseReceiptModel> recepits = expenseReceiptService.queryList(recepit);
			//存在已被报销单使用的项目
			if(!HsysList.isEmpty(recepits))
			{
				ProjectModel errProject = projectService.queryById(id);
				//添加已被使用的项目编号
				errProjects.add(errProject.getNo());
			}else {
				projectService.deleteById(id);
			}
		}
		//存在已被报销单使用的项目
		if(!errProjects.isEmpty())
		{
			StringBuilder bld = new StringBuilder("无法删除！存在已在报销单中被使用的项目。编号: ");
			String str = StringUtils.join(errProjects, ',');
			throw new HsysException(bld.toString()+str);
		}
	}

	public ProjectModel getProject(ProjectJsonGetForm form) {
		//根据id取得项目信息
		ProjectModel project = projectService.queryById(form.getId());
		if(project == null) {
			throw new HsysException("该项目信息不存在。");
		}
		return project;
	}

	public void updateProject(ProjectJsonUpdateForm form) {
		//权限判定
		if(!HsysSecurityContextHolder.isLoginUserHasRole(ROLE.ADMIN)) {
			throw new HsysException("权限不足"); 
		}
		//项目信息是否存在
		ProjectModel project = projectService.queryById(form.getId());
		if(project == null) {
			throw new HsysException("该信息不存在。");
		}
		
		//判断项目编号是否更改
		if(!(project.getNo()==null?form.getNo()==null:project.getNo().equals(form.getNo()))) {
			//项目编号长度不得大于50个字
			if(form.getNo().length()<=50){
				ProjectModel projectExist = projectService.queryByNo(form.getNo());
				//检测项目编号是否已经存在
				if(projectExist != null) {
					throw new HsysException("该项目信息已存在"); 
				}
				project.setNo(form.getNo());
				project.setUpdate(ProjectModel.FIELD_NO);
			}else {
				throw new HsysException("请输入50字以内的项目编号");
			}
		}
		
		//项目名长度不得大于50个字
		if(!(project.getName()==null?form.getName()==null:project.getName().equals(form.getName()))){
			if(form.getName().length()<=50){
				project.setName(form.getName());
				project.setUpdate(ProjectModel.FIELD_NAME);
			}else {
				throw new HsysException("请输入50字以内的项目名");
			}
		}
		
		//项目经费必须是数字（可以带小数点，或负数）
		if(!(project.getFunds()==null?form.getFunds()==null:project.getFunds().equals(form.getFunds()))){
			try {
				Float.parseFloat(form.getFunds());
			}catch(Exception e) {
				throw new HsysException("经费格式不符合规范"); 
			}
			project.setFunds(form.getFunds());
			project.setUpdate(ProjectModel.FIELD_FUNDS);
		}
		
		if(project.hasUpdate())
		{
			projectService.update(project);
		}
	}

	public List<UserModel> getUnleaderList(ProjectJsonGetForm form) {
		List<UserModel> users = userService.queryList(new UserModel());
		ProjectModel project = projectService.queryById(form.getId());
		int userSize = users.size();
		if(project.getUsers()!=null) {
			for(int i = userSize-1; i>=0; i--){
				int leaderSize = project.getUsers().size();
				while(leaderSize>0) {
					UserModel leader = project.getUsers().get(leaderSize-1);
					if(leader.getId()==users.get(i).getId()) {
						project.getUsers().remove(leaderSize-1);
						users.remove(i);
						break;
					}
					leaderSize--;
				}
			}
		}
		return users;
	}
}
