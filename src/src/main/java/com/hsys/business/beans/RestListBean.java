package com.hsys.business.beans;

import java.util.List;

import com.hsys.common.HsysList;
import com.hsys.models.RestModel;

public class RestListBean {
	private List<RestModel> list;
	private float sumVacation;
	private float sumSick;
	private float sumLeave;
	private float sumMarriage;
	private float sumFuneral;
	private float sumPublic;
	
	public RestListBean() {
		this.setList(HsysList.New());
	}
	
	public List<RestModel> getList() {
		return list;
	}
	public void setList(List<RestModel> list) {
		this.list = list;
	}
	public float getSumVacation() {
		return sumVacation;
	}
	public void setSumVacation(float sumVacation) {
		this.sumVacation = sumVacation;
	}
	public float getSumSick() {
		return sumSick;
	}
	public void setSumSick(float sumSick) {
		this.sumSick = sumSick;
	}
	public float getSumLeave() {
		return sumLeave;
	}
	public void setSumLeave(float sumLeave) {
		this.sumLeave = sumLeave;
	}
	public float getSumMarriage() {
		return sumMarriage;
	}
	public void setSumMarriage(float sumMarriage) {
		this.sumMarriage = sumMarriage;
	}
	public float getSumFuneral() {
		return sumFuneral;
	}
	public void setSumFuneral(float sumFuneral) {
		this.sumFuneral = sumFuneral;
	}
	public float getSumPublic() {
		return sumPublic;
	}
	public void setSumPublic(float sumPublic) {
		this.sumPublic = sumPublic;
	}
	
	public void addSumVacation(float length) {
		this.sumVacation += length;
	}

	public void addSumSick(float length) {
		this.sumSick += length;
	}

	public void addSumLeave(float length) {
		this.sumLeave += length;
	}
	public void addSumMarriage(float length) {
		this.sumMarriage += length;
	}
	public void addSumFuneral(float length) {
		this.sumFuneral += length;
	}
	public void addSumPublic(float length) {
		this.sumPublic += length;
	}

}
