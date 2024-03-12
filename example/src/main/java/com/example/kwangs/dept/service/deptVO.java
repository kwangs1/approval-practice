package com.example.kwangs.dept.service;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class deptVO {

	private String deptid;
	private String deptname;
	private String parid;
	private String abbreviation;
	private String sendername;
	private Date credate;
	
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getParid() {
		return parid;
	}
	public void setParid(String parid) {
		this.parid = parid;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getSendername() {
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public Date getCredate() {
		return credate;
	}
	public void setCredate(Date credate) {
		this.credate = credate;
	}
	
	
}
