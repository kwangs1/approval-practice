package com.example.kwangs.participant.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class participantVO {
	private String participant_seq;
	private String appr_seq;
	private int status;
	private int line_seq;
	private String name;
	private String id;
	private String pos;
	private Date regdate;
	private Date upddate;
	private int approvalstatus;
	
	public String getParticipant_seq() {
		return participant_seq;
	}
	public void setParticipant_seq(String participant_seq) {
		this.participant_seq = participant_seq;
	}
	
	public String getAppr_seq() {
		return appr_seq;
	}
	public void setAppr_seq(String appr_seq) {
		this.appr_seq = appr_seq;
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
	public int getApprovalstatus() {
		return approvalstatus;
	}
	public void setApprovalstatus(int approvalstatus) {
		this.approvalstatus = approvalstatus;
	}
	
}
