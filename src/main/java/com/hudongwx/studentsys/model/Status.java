package com.hudongwx.studentsys.model;

import com.hudongwx.studentsys.common.BaseStatus;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Status extends BaseStatus<Status> {
	public static final String STATE_OF_ATTENDING = "status";
	public static final Status dao = new Status();
	public static final String SEARCH_FROM_STATUS = "select * from stumanager_status ";
}
