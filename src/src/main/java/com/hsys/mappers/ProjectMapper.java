package com.hsys.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsys.models.ProjectLeaderModel;
import com.hsys.models.ProjectModel;
import com.hsys.models.UserModel;


/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/07
 */
@Mapper
public interface ProjectMapper {
	List<ProjectModel> queryList(ProjectModel p);

	List<UserModel> queryLeaderList(ProjectModel project);
	
	void addLeader(ProjectLeaderModel pl);

	void deleteLeader(ProjectLeaderModel pl);

	void addProject(ProjectModel project);

	void deleteProjects(ProjectModel project);

	void update(ProjectModel project);
}
