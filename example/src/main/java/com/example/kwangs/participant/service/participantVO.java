package com.example.kwangs.participant.service;

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
	private String regdate;
	private String upddate;
	private int approvalstatus;
	private int approvaltype;
	
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
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public int getApprovalstatus() {
		return approvalstatus;
	}
	public void setApprovalstatus(int approvalstatus) {
		this.approvalstatus = approvalstatus;
	}
	public int getApprovaltype() {
		return approvaltype;
	}
	public void setApprovaltype(int approvaltype) {
		this.approvaltype = approvaltype;
	}
	
}
