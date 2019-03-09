package com.hsys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.common.HsysList;
import com.hsys.mappers.ProjectMapper;
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

	public boolean isLeader(int id, int userId) {
		ProjectModel project = new ProjectModel();
		project.setId(id);
		project.setCond(ProjectModel.COND_ID, id);
		project.setCond(ProjectModel.COND_LEADER_ID, userId);
		List<UserModel> leaders = queryProjectLeaders(project);
		return !HsysList.isEmpty(leaders);
	}
}
