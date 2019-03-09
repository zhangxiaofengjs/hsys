package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.business.forms.ProjectJsonListForm;
import com.hsys.models.ProjectModel;
import com.hsys.services.ProjectService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/07
 */
@Component
public class ProjectBusiness {
	@Autowired
	private ProjectService projectService;
	
	public List<ProjectModel> getProjects(ProjectJsonListForm form) {
		return projectService.queryList(new ProjectModel());
	}
}
