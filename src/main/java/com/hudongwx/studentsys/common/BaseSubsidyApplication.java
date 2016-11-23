package com.hudongwx.studentsys.common;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSubsidyApplication<M extends BaseSubsidyApplication<M>> extends Model<M> implements IBean {

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

	public void setApplicantId(java.lang.Integer applicantId) {
		set("applicantId", applicantId);
	}

	public java.lang.Integer getApplicantId() {
		return get("applicantId");
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

	public void setRegionId(java.lang.Integer regionId) {
		set("regionId", regionId);
	}

	public java.lang.Integer getRegionId() {
		return get("regionId");
	}

	public void setClassName(java.lang.String className) {
		set("className", className);
	}

	public java.lang.String getClassName() {
		return get("className");
	}

	public void setNumber(java.lang.Integer number) {
		set("number", number);
	}

	public java.lang.Integer getNumber() {
		return get("number");
	}

	public void setTotalSubsidy(java.lang.Integer totalSubsidy) {
		set("totalSubsidy", totalSubsidy);
	}

	public java.lang.Integer getTotalSubsidy() {
		return get("totalSubsidy");
	}

	public void setTotalBonus(java.lang.Integer totalBonus) {
		set("totalBonus", totalBonus);
	}

	public java.lang.Integer getTotalBonus() {
		return get("totalBonus");
	}

	public void setAggregateAmount(java.lang.Integer aggregateAmount) {
		set("aggregateAmount", aggregateAmount);
	}

	public java.lang.Integer getAggregateAmount() {
		return get("aggregateAmount");
	}

	public void setApproveStatus(java.lang.Integer approveStatus) {
		set("approveStatus", approveStatus);
	}

	public java.lang.Integer getApproveStatus() {
		return get("approveStatus");
	}

}
