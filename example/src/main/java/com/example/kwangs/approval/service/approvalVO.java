package com.example.kwangs.approval.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.kwangs.common.file.service.AttachVO;

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
	private int attachcnt;
	private String foldername;
	private String docattr;
	private String sendername;
	private String receivers;
	private String sendid;
	private String senddate;
	private String orgdraftdeptid;
	private String poststatus;
	private String draftsrctype;
	private String orgdeptfolderid;
	
	
	public String getOrgdeptfolderid() {
		return orgdeptfolderid;
	}
	public void setOrgdeptfolderid(String orgdeptfolderid) {
		this.orgdeptfolderid = orgdeptfolderid;
	}
	public String getDraftsrctype() {
		return draftsrctype;
	}
	public void setDraftsrctype(String draftsrctype) {
		this.draftsrctype = draftsrctype;
	}
	public String getPoststatus() {
		return poststatus;
	}
	public void setPoststatus(String poststatus) {
		this.poststatus = poststatus;
	}
	public String getOrgdraftdeptid() {
		return orgdraftdeptid;
	}
	public void setOrgdraftdeptid(String orgdraftdeptid) {
		this.orgdraftdeptid = orgdraftdeptid;
	}
	public String getDocattr() {
		return docattr;
	}
	public void setDocattr(String docattr) {
		this.docattr = docattr;
	}
	public String getSendername() {
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public String getReceivers() {
		return receivers;
	}
	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}
	public String getSendid() {
		return sendid;
	}
	public void setSendid(String sendid) {
		this.sendid = sendid;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	public String getFoldername() {
		return foldername;
	}
	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}
	public int getAttachcnt() {
		return attachcnt;
	}
	public void setAttachcnt(int attachcnt) {
		this.attachcnt = attachcnt;
	}
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
