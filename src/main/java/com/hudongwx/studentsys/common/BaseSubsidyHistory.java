package com.hudongwx.studentsys.common;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSubsidyHistory<M extends BaseSubsidyHistory<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setApplicant(java.lang.String applicant) {
		set("applicant", applicant);
	}

	public java.lang.String getApplicant() {
		return get("applicant");
	}

	public void setApplicationDate(java.lang.Long applicationDate) {
		set("applicationDate", applicationDate);
	}

	public java.lang.Long getApplicationDate() {
		return get("applicationDate");
	}

	public void setState(java.lang.Integer state) {
		set("state", state);
	}

	public java.lang.Integer getState() {
		return get("state");
	}

}
