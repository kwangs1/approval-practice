package com.example.kwangs.bizunit.service;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class bizunitVO {

	private String bizunitcd;
	private String procdeptid;
	private String procdeptname;
	private Date bizopendate;
	private String bizunitname;
	private String bizunitdesc;
	private String bizunitchargeid;
	private String keepperiod;
	private String ablitionflag;
	
	
	public String getBizunitcd() {
		return bizunitcd;
	}
	public void setBizunitcd(String bizunitcd) {
		this.bizunitcd = bizunitcd;
	}
	public String getProcdeptid() {
		return procdeptid;
	}
	public void setProcdeptid(String procdeptid) {
		this.procdeptid = procdeptid;
	}
	public String getProcdeptname() {
		return procdeptname;
	}
	public void setProcdeptname(String procdeptname) {
		this.procdeptname = procdeptname;
	}
	public Date getBizopendate() {
		return bizopendate;
	}
	public void setBizopendate(Date bizopendate) {
		this.bizopendate = bizopendate;
	}
	public String getBizunitname() {
		return bizunitname;
	}
	public void setBizunitname(String bizunitname) {
		this.bizunitname = bizunitname;
	}
	public String getBizunitdesc() {
		return bizunitdesc;
	}
	public void setBizunitdesc(String bizunitdesc) {
		this.bizunitdesc = bizunitdesc;
	}
	public String getBizunitchargeid() {
		return bizunitchargeid;
	}
	public void setBizunitchargeid(String bizunitchargeid) {
		this.bizunitchargeid = bizunitchargeid;
	}
	public String getKeepperiod() {
		return keepperiod;
	}
	public void setKeepperiod(String keepperiod) {
		this.keepperiod = keepperiod;
	}
	public String getAblitionflag() {
		return ablitionflag;
	}
	public void setAblitionflag(String ablitionflag) {
		this.ablitionflag = ablitionflag;
	}
	
	
}
