package com.example.kwangs.approval.service;

import org.springframework.stereotype.Component;

@Component
public class Document {

	private String deptid;
	private int deptdocno;
	private int year;
	
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public int getDeptdocno() {
		return deptdocno;
	}
	public void setDeptdocno(int deptdocno) {
		this.deptdocno = deptdocno;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
}
