package com.example.kwangs.folder.service;

import org.springframework.stereotype.Component;

@Component
public class folderVO {

	private String fldrid;
	private String fldrname;
	private String parfldrid;
	private String parfldrname;
	private int fldrdepth;
	private String ownertype;
	private String ownerid;
	private String appltype;
	private int applid;
	private String regdate;
	private String updatedate;
	private String updaterid;
	private String year;
	private String endyear;
	
	public String getFldrid() {
		return fldrid;
	}
	public void setFldrid(String fldrid) {
		this.fldrid = fldrid;
	}
	public String getFldrname() {
		return fldrname;
	}
	public void setFldrname(String fldrname) {
		this.fldrname = fldrname;
	}
	public String getParfldrid() {
		return parfldrid;
	}
	public void setParfldrid(String parfldrid) {
		this.parfldrid = parfldrid;
	}
	public String getParfldrname() {
		return parfldrname;
	}
	public void setParfldrname(String parfldrname) {
		this.parfldrname = parfldrname;
	}
	public int getFldrdepth() {
		return fldrdepth;
	}
	public void setFldrdepth(int fldrdepth) {
		this.fldrdepth = fldrdepth;
	}
	public String getOwnertype() {
		return ownertype;
	}
	public void setOwnertype(String ownertype) {
		this.ownertype = ownertype;
	}
	public String getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
	public String getAppltype() {
		return appltype;
	}
	public void setAppltype(String appltype) {
		this.appltype = appltype;
	}
	public int getApplid() {
		return applid;
	}
	public void setApplid(int applid) {
		this.applid = applid;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getEndyear() {
		return endyear;
	}
	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}
	
	
}
