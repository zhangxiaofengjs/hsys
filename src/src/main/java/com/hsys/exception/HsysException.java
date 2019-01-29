package com.hsys.exception;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/12/26
 */
public class HsysException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int code = 500;

	public HsysException(String msg, String... params) {
		super(String.format(msg, params));
	}

	public HsysException(int code) {
		this.code = code;
	}
	
	public HsysException(String msg, Throwable e) {
		super(msg, e);
	}

	public HsysException(String msg, int code) {
		super(msg);
		this.code = code;
	}

	public HsysException(String msg, int code, Throwable e) {
		super(msg, e);
		this.code = code;
	}

	public HsysException(Exception e) {
		super("hsys error:", e);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
