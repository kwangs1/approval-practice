package com.example.kwangs.folder.service;

import org.springframework.stereotype.Component;

@Component
public class fldrmbr2VO {
	private String fldrid;
	private String fldrmbrid;
	private String indexdate;
	private String registdate;
	private String registerid;
	private String updatedate;
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
	public String getIndexdate() {
		return indexdate;
	}
	public void setIndexdate(String indexdate) {
		this.indexdate = indexdate;
	}
	public String getRegistdate() {
		return registdate;
	}
	public void setRegistdate(String registdate) {
		this.registdate = registdate;
	}
	public String getRegisterid() {
		return registerid;
	}
	public void setRegisterid(String registerid) {
		this.registerid = registerid;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getUpdaterid() {
		return updaterid;
	}
	public void setUpdaterid(String updaterid) {
		this.updaterid = updaterid;
	}
	
	
}
