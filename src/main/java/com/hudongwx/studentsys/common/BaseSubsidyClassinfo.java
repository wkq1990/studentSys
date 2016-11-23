package com.hudongwx.studentsys.common;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSubsidyClassinfo<M extends BaseSubsidyClassinfo<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setClassId(java.lang.Integer classId) {
		set("classId", classId);
	}

	public java.lang.Integer getClassId() {
		return get("classId");
	}

	public void setClassName(java.lang.String className) {
		set("className", className);
	}

	public java.lang.String getClassName() {
		return get("className");
	}

	public void setRegionId(java.lang.Integer regionId) {
		set("regionId", regionId);
	}

	public java.lang.Integer getRegionId() {
		return get("regionId");
	}

	public void setStudentId(java.lang.Integer studentId) {
		set("studentId", studentId);
	}

	public java.lang.Integer getStudentId() {
		return get("studentId");
	}

	public void setStudentName(java.lang.String studentName) {
		set("studentName", studentName);
	}

	public java.lang.String getStudentName() {
		return get("studentName");
	}

	public void setSubsidyAmount(java.lang.Integer subsidyAmount) {
		set("subsidyAmount", subsidyAmount);
	}

	public java.lang.Integer getSubsidyAmount() {
		return get("subsidyAmount");
	}

	public void setBonus(java.lang.Integer bonus) {
		set("bonus", bonus);
	}

	public java.lang.Integer getBonus() {
		return get("bonus");
	}

	public void setResidualFrequency(java.lang.Integer residualFrequency) {
		set("residualFrequency", residualFrequency);
	}

	public java.lang.Integer getResidualFrequency() {
		return get("residualFrequency");
	}

	public void setState(java.lang.String state) {
		set("state", state);
	}

	public java.lang.String getState() {
		return get("state");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

}
