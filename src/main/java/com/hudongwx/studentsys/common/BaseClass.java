package com.hudongwx.studentsys.common;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseClass<M extends BaseClass<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setClassName(java.lang.String className) {
		set("className", className);
	}

	public java.lang.String getClassName() {
		return get("className");
	}

	public void setHeadTeacher(java.lang.String headTeacher) {
		set("headTeacher", headTeacher);
	}

	public java.lang.String getHeadTeacher() {
		return get("headTeacher");
	}

	public void setAssistant(java.lang.String Assistant) {
		set("Assistant", Assistant);
	}

	public java.lang.String getAssistant() {
		return get("Assistant");
	}

	public void setTutorId(java.lang.Integer tutorId) {
		set("tutorId", tutorId);
	}

	public java.lang.Integer getTutorId() {
		return get("tutorId");
	}

	public void setTutor(java.lang.String tutor) {
		set("tutor", tutor);
	}

	public java.lang.String getTutor() {
		return get("tutor");
	}

	public void setStudentCnt(java.lang.Integer studentCnt) {
		set("studentCnt", studentCnt);
	}

	public java.lang.Integer getStudentCnt() {
		return get("studentCnt");
	}

	public void setClassOpeningTime(java.lang.Long classOpeningTime) {
		set("classOpeningTime", classOpeningTime);
	}

	public java.lang.Long getClassOpeningTime() {
		return get("classOpeningTime");
	}

	public void setClassCreateTime(java.lang.Long classCreateTime) {
		set("classCreateTime", classCreateTime);
	}

	public java.lang.Long getClassCreateTime() {
		return get("classCreateTime");
	}

	public void setClassUpdateTime(java.lang.Long classUpdateTime) {
		set("classUpdateTime", classUpdateTime);
	}

	public java.lang.Long getClassUpdateTime() {
		return get("classUpdateTime");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

	public void setRegionId(java.lang.Integer regionId) {
		set("regionId", regionId);
	}

	public java.lang.Integer getRegionId() {
		return get("regionId");
	}

	public void setClassOperaterId(java.lang.Integer classOperaterId) {
		set("classOperaterId", classOperaterId);
	}

	public java.lang.Integer getClassOperaterId() {
		return get("classOperaterId");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

}
