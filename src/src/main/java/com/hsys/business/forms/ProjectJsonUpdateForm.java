package com.hsys.business.forms;

/**
 * @author: lingxiaming
 * @version: 2019/06/04
 */
public class ProjectJsonUpdateForm {
	private int id;
	private String no;
	private String name;
	private String funds;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFunds() {
		return funds;
	}

	public void setFunds(String funds) {
		this.funds = funds;
	}
}
