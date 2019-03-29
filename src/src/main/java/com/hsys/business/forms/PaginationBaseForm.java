package com.hsys.business.forms;

import com.hsys.business.beans.HsysPageInfo;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/03/13
 */
public class PaginationBaseForm {
	private int pageStart = 1;
	private int pageEnd = 10;
	private int pageNum = 1;
	private int pages = 0;
	private int pageSize = 15;
	private int pageCount = 10;
	
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
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return this.pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	
	public void setPageInfo(HsysPageInfo<?> pageInfo) {
		this.setPages(pageInfo.getPages());
		
		if(this.pageNum == 0) {
			this.pageNum = 1;
		}
		if(this.pageNum > this.pages) {
			this.pageNum = this.pages;
			this.pageEnd = this.pages;
		}
		
		if(this.pageEnd < this.pageStart) {
			this.pageEnd = this.pageStart + this.pageCount - 1;
		}
		if(this.pageEnd > this.pages) {
			this.pageEnd = this.pages;
			this.pageStart = this.pages - this.pageCount;
			if(this.pageStart < 1) {
				this.pageStart = 1;
			}
		}
		
		if(this.pageStart < 1) {
			this.pageStart = 1;
		}
	}
}
