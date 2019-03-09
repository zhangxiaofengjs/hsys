package com.hsys.models.enums;

public interface ROLE {
	public final static String ADMIN = "admin";
	public final static String USER_EDIT = "user_edit";
	public final static String REST_APPROVE = "rest_approve";
	public final static String REST_LIST = "rest_list";
	public final static String REST_LIST_ALL = "rest_list_all";
	public final static String ATTENDANCE_LIST = "attendance_list";
	public final static String ATTENDANCE_UPLOAD = "attendance_upload";
	public final static String ATTENDANCE_LIST_ALL = "attendance_list_all";
	public static final String EXTRATIME_APPROVE = "extratime_approve";
	public final static String EXTRATIME_LIST = "extratime_list";
	public final static String EXTRATIME_LIST_ALL = "extratime_list_all";
	public final static String EXPENSE_LIST = "expense_list";
	public final static String EXPENSE_LIST_ALL = "expense_list_all";
	public final static String DEVICE_EDIT = "device_edit";
	
	public final static String[] ALL = new String[] {
		ADMIN,
		USER_EDIT,
		EXTRATIME_APPROVE,
		EXTRATIME_LIST,
		EXTRATIME_LIST_ALL,
		REST_APPROVE,
		REST_LIST,
		REST_LIST_ALL,
		ATTENDANCE_UPLOAD,
		ATTENDANCE_LIST,
		ATTENDANCE_LIST_ALL,
		EXPENSE_LIST,
		EXPENSE_LIST_ALL,
		DEVICE_EDIT,
	};
}
