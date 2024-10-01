package com.example.kwangs.approval.service;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class action_log_sanc {

	private String userid;
	private String action_code;
	private Date log_date;
	private String data1;
	private String data2;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	public Date getLog_date() {
		return log_date;
	}
	public void setLog_date(Date log_date) {
		this.log_date = log_date;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	
}
