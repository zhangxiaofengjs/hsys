package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.business.forms.ProjectLeaderAddForm;
import com.hsys.business.forms.ProjectLeaderDeleteForm;
import com.hsys.common.HsysList;
import com.hsys.mappers.ProjectMapper;
import com.hsys.models.ProjectLeaderModel;
import com.hsys.models.ProjectModel;
import com.hsys.models.UserModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/06
 */
@Service
public class ProjectService {
	@Autowired
    private ProjectMapper projectMapper;
	
	public List<ProjectModel> queryList(ProjectModel p) {
		return projectMapper.queryList(p);
	}

	public List<UserModel> queryProjectLeaders(ProjectModel project) {
		return projectMapper.queryLeaderList(project);
	}
	
	//通过名称检索
	public ProjectModel queryByNo(String no) {
		ProjectModel project = new ProjectModel();
		project.setNo(no);
		project.setCond(ProjectModel.COND_NO, true);
		List<ProjectModel> projectlist = queryList(project);
		if(projectlist.size() == 1) {
			return projectlist.get(0);
		}
		return null;
	}

	public boolean isLeader(int id, int userId) {
		ProjectModel project = new ProjectModel();
		project.setId(id);
		project.setCond(ProjectModel.COND_ID, id);
		project.setCond(ProjectModel.COND_LEADER_ID, userId);
		List<UserModel> leaders = queryProjectLeaders(project);
		return !HsysList.isEmpty(leaders);
	}

	public void addLeader(ProjectLeaderAddForm form) {
		ProjectLeaderModel leader = new ProjectLeaderModel();
		leader.setLeaderId(form.getUserId());
		leader.setProjectId(form.getProjectId());
		projectMapper.addLeader(leader);
	}

	public void deleteLeader(ProjectLeaderDeleteForm form) {
		ProjectLeaderModel leader = new ProjectLeaderModel();
		leader.setLeaderId(form.getUserId());
		leader.setProjectId(form.getProjectId());
		projectMapper.deleteLeader(leader);
	}

	public void addProject(ProjectModel project) {
		projectMapper.addProject(project);
		
	}

	public ProjectModel queryById(int id) {
		ProjectModel project = new ProjectModel();
		project.setId(id);
		project.setCond(ProjectModel.COND_ID, true);
		List<ProjectModel> list = queryList(project);
		if(list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public void deleteById(int id) {
		ProjectModel project = new ProjectModel();
		project.setId(id);
		projectMapper.deleteProjects(project);
		
	}

	public void update(ProjectModel project) {
		projectMapper.update(project);
	}
}
