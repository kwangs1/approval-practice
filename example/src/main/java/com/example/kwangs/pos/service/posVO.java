package com.example.kwangs.pos.service;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class posVO {

	private int posid;
	private String posname;
	private int seq;
	private Date credate;
	
	public int getPosid() {
		return posid;
	}
	public void setPosid(int posid) {
		this.posid = posid;
	}
	public String getPosname() {
		return posname;
	}
	public void setPosname(String posname) {
		this.posname = posname;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public Date getCredate() {
		return credate;
	}
	public void setCredate(Date credate) {
		this.credate = credate;
	}
	
	
}
