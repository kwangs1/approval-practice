package com.example.kwangs.approval.service;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class opinionVO {
	
	private String opinionid; 
	private String opiniontype; 
	private String opinioncontent; 
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date credate; 
	private String registerid; 
	private Date updatedate; 
	private String updateid; 
	//participant
	private String signername;
	private String participant_seq;
	//approval
	private String poststatus;
	private String appr_seq;
	//send
	private String sendid;
	public String getOpinionid() {
		return opinionid;
	}
	public void setOpinionid(String opinionid) {
		this.opinionid = opinionid;
	}
	public String getOpiniontype() {
		return opiniontype;
	}
	public void setOpiniontype(String opiniontype) {
		this.opiniontype = opiniontype;
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
	public String getUpdateid() {
		return updateid;
	}
	public void setUpdateid(String updateid) {
		this.updateid = updateid;
	}
	public String getSignername() {
		return signername;
	}
	public void setSignername(String signername) {
		this.signername = signername;
	}
	public String getPoststatus() {
		return poststatus;
	}
	public void setPoststatus(String poststatus) {
		this.poststatus = poststatus;
	}
	public String getSendid() {
		return sendid;
	}
	public void setSendid(String sendid) {
		this.sendid = sendid;
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
	
	
}
