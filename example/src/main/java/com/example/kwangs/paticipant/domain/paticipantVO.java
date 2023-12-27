package com.example.kwangs.paticipant.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class paticipantVO {
	private String paticipant_seq;
	private String receipts_seq;
	private int status;
	private int line_seq;
	private String name;
	private String id;
	private String pos;
	private Date regdate;
	private Date upddate;
	
	public String getPaticipant_seq() {
		return paticipant_seq;
	}
	public void setPaticipant_seq(String paticipant_seq) {
		this.paticipant_seq = paticipant_seq;
	}
	public String getReceipts_seq() {
		return receipts_seq;
	}
	public void setReceipts_seq(String receipts_seq) {
		this.receipts_seq = receipts_seq;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getLine_seq() {
		return line_seq;
	}
	public void setLine_seq(int line_seq) {
		this.line_seq = line_seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	
}
