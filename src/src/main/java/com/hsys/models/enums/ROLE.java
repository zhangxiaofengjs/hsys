package com.hsys.models.enums;

public interface ROLE {
	public final static String ADMIN = "admin";
	public final static String USER_EDIT = "user_edit";
	public final static String REST_APPROVE = "rest_approve";
	
	public final static String[] ALL = new String[] {
		ADMIN,
		USER_EDIT,
		REST_APPROVE };
}
