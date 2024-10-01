package com.example.kwangs.participant.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class participantVO {
	private String participant_seq;
	private String appr_seq;
	private String status;
	private int line_seq;
	private String signername;
	private String signerid;
	private String pos;
	private String regdate;
	private String upddate;
	private int approvalstatus;
	private int approvaltype;
	private String deptid;
	private String deptname;
	private String flag;
	private String statusname;
	private List<participantVO> participants;
	//opinion
	private String opinioncontent;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date credate;
	
	
	
	public List<participantVO> getParticipants() {
		return participants;
	}
	public void setParticipants(List<participantVO> participants) {
		this.participants = participants;
	}
	public String getOpinioncontent() {
		return opinioncontent;
	}
	public void setOpinioncontent(String opinioncontent) {
		this.opinioncontent = opinioncontent;
	}

	public Date getCredate() {
		return credate;
	}
	public void setCredate(Date credate) {
		this.credate = credate;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getLine_seq() {
		return line_seq;
	}
	public void setLine_seq(int line_seq) {
		this.line_seq = line_seq;
	}
	public String getSignername() {
		return signername;
	}
	public void setSignername(String signername) {
		this.signername = signername;
	}
	public String getSignerid() {
		return signerid;
	}
	public void setSignerid(String signerid) {
		this.signerid = signerid;
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
