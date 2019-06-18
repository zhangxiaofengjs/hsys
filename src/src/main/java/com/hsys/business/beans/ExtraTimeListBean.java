package com.hsys.business.beans;

import java.util.List;

import com.hsys.common.HsysList;
import com.hsys.models.ExtraTimeModel;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/04/15
 */
public class ExtraTimeListBean {
	private List<ExtraTimeModel> list;
	private float sumNormal;
	private float sumWeekend;
	private float sumHoliday;
	
	public ExtraTimeListBean() {
		this.setList(HsysList.New());
	}
	
	public List<ExtraTimeModel> getList() {
		return list;
	}
	public void setList(List<ExtraTimeModel> list) {
		this.list = list;
	}
	public float getSumNormal() {
		return sumNormal;
	}
	public void setSumNormal(float sumNormal) {
		this.sumNormal = sumNormal;
	}
	public float getSumWeekend() {
		return sumWeekend;
	}
	public void setSumWeekend(float sumWeekend) {
		this.sumWeekend = sumWeekend;
	}
	public float getSumHoliday() {
		return sumHoliday;
	}
	public void setSumHoliday(float sumHoliday) {
		this.sumHoliday = sumHoliday;
	}

	public void addSumNormal(float length) {
		this.sumNormal += length;
	}

	public void addSumHoliday(float length) {
		this.sumHoliday += length;
	}

	public void addSumWeekend(float length) {
		this.sumWeekend += length;
	}
}
