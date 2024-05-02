package com.example.kwangs.folder.service;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class fldrmbrVO {
	private String fldrid;
	private String fldrmbrid;
	private Date indexdate;
	private Date registdate;
	private String registerid;
	private Date updatedate;
	private String updaterid;
	
	public String getFldrid() {
		return fldrid;
	}
	public void setFldrid(String fldrid) {
		this.fldrid = fldrid;
	}
	public String getFldrmbrid() {
		return fldrmbrid;
	}
	public void setFldrmbrid(String fldrmbrid) {
		this.fldrmbrid = fldrmbrid;
	}
	public Date getIndexdate() {
		return indexdate;
	}
	public void setIndexdate(Date indexdate) {
		this.indexdate = indexdate;
	}
	public Date getRegistdate() {
		return registdate;
	}
	public void setRegistdate(Date registdate) {
		this.registdate = registdate;
	}
	public String getRegisterid() {
		return registerid;
	}
	public void setRegisterid(String registerid) {
		this.registerid = registerid;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getUpdaterid() {
		return updaterid;
	}
	public void setUpdaterid(String updaterid) {
		this.updaterid = updaterid;
	}
	
	
}
