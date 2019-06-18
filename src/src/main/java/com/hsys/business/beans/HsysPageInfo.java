package com.hsys.business.beans;

import java.util.ArrayList;

import com.github.pagehelper.Page;
import com.hsys.business.forms.PaginationBaseForm;

public class HsysPageInfo<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;
	private E param;
	//页面显示的页数开始
	private int pageStart;
	//页面显示的页数结束
	private int pageEnd;
	//页面显示的页数
	private int pageCount;
	//当前页码
	private int pageNum;
	//总页数
	private int pages;
	//每页条数
	private int pageSize;
	//是否page计数
	private boolean isCount;
		
	public HsysPageInfo() {
		super();
		isCount = true;
	}

	public E getParam() {
		return param;
	}

	public void setParam(E param) {
		this.param = param;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 设定查询的Form上的分页信息
	 * @param form
	 */
	public void setPageInfo(PaginationBaseForm form) {
		this.setPageNum(form.getPageNum());
		this.setPageSize(form.getPageSize());
		this.setPages(form.getPages());
		this.setPageStart(form.getPageStart());
		this.setPageEnd(form.getPageEnd());
		this.setPageCount(form.getPageCount());
	}

	/**
	 * 设定分页结果信息
	 * @param page
	 */
	public void setPageInfo(Page<E> page) {
		this.addAll(page);
		this.setPageNum(page.getPageNum());//第几页
		this.setPages(page.getPages());
		this.setPageSize(page.getPageSize());//总页数
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isCount() {
		return isCount;
	}

	public void setCount(boolean isCount) {
		this.isCount = isCount;
	}
}
