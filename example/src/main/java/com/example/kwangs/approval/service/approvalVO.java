package com.example.kwangs.approval.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.kwangs.common.file.AttachVO;

@Component
public class approvalVO{
	private String appr_seq;
	private String draftername;
	private String drafterid;
	private String regdate;
	private String title;
	private String content;
	private Date startdate;
	private Date enddate;
	private int status;
	private String docregno;
	private String drafterdeptid;
	private String drafterdeptname;
	private String regno;
	private String finalapprover;
	private String approvaldate;
	private String folderid;
	private String bizunitcd;
	private List<AttachVO> attach;

	
	
	public List<AttachVO> getAttach() {
		return attach;
	}
	public void setAttach(List<AttachVO> attach) {
		this.attach = attach;
	}
	public String getFolderid() {
		return folderid;
	}
	public void setFolderid(String folderid) {
		this.folderid = folderid;
	}
	public String getBizunitcd() {
		return bizunitcd;
	}
	public void setBizunitcd(String bizunitcd) {
		this.bizunitcd = bizunitcd;
	}
	public String getApprovaldate() {
		return approvaldate;
	}
	public void setApprovaldate(String approvaldate) {
		this.approvaldate = approvaldate;
	}
	public String getFinalapprover() {
		return finalapprover;
	}
	public void setFinalapprover(String finalapprover) {
		this.finalapprover = finalapprover;
	}
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public String getDocregno() {
		return docregno;
	}
	public void setDocregno(String docregno) {
		this.docregno = docregno;
	}

	public String getDrafterdeptid() {
		return drafterdeptid;
	}
	public void setDrafterdeptid(String drafterdeptid) {
		this.drafterdeptid = drafterdeptid;
	}

	public String getDrafterdeptname() {
		return drafterdeptname;
	}
	public void setDrafterdeptname(String drafterdeptname) {
		this.drafterdeptname = drafterdeptname;
	}
	public String getAppr_seq() {
		return appr_seq;
	}
	public void setAppr_seq(String appr_seq) {
		this.appr_seq = appr_seq;
	}
	public String getDraftername() {
		return draftername;
	}
	public void setDraftername(String draftername) {
		this.draftername = draftername;
	}
	public String getDrafterid() {
		return drafterid;
	}
	public void setDrafterid(String drafterid) {
		this.drafterid = drafterid;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
