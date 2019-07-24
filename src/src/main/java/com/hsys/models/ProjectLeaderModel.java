package com.hsys.models;

import org.apache.ibatis.type.Alias;

/**
 * @author: lingxiaming
 * @version: 2019/06/04
 */
@Alias("projectLeaderModel")
public class ProjectLeaderModel extends BaseModel {
	private int id;
	private int projectId;
	private int leaderId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}
}
