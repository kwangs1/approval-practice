package com.example.kwangs.approval.service;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class sendVO {

	private String sendid;
	private String appr_seq;
	private String parsendid;
	private String sendtype;
	private String receiverid;
	private String receivername;
	private String senderid;
	private String sendername;
	private String senddeptid;
	private String senddate;
	private String approvaldate;
	private String receiptappr_seq;
	private String registdate;
	private String registerid;
	private String recdocstatus;
	private Date rejectdate;
	private String rejecterid;
	private String rejectername;
	private String returncommentflag;
	private String assignedflag;
	private String assigncommentflag;
	private String assignuserid;
	private String assignusername;
	private String assigndeptid;
	private Date docrecvdate;
	private String broadno;
	private Date broaddate;
	private String broaduserid;
	private String broadusername;
	private String broaddeptid;
	private String broaddeptname;
	private String undertakerid;
	private String undertakerdeptid;
	private String undertakername;
	//=====//
	private int count;
	private String action_code; //쿼리 조인 결과값에서 log_sanc의 action_code값을 가져오기 위해
	
	
	public String getRecdocstatus() {
		return recdocstatus;
	}
	public void setRecdocstatus(String recdocstatus) {
		this.recdocstatus = recdocstatus;
	}
	public String getSendid() {
		return sendid;
	}
	public void setSendid(String sendid) {
		this.sendid = sendid;
	}
	public String getAppr_seq() {
		return appr_seq;
	}
	public void setAppr_seq(String appr_seq) {
		this.appr_seq = appr_seq;
	}
	public String getParsendid() {
		return parsendid;
	}
	public void setParsendid(String parsendid) {
		this.parsendid = parsendid;
	}
	public String getSendtype() {
		return sendtype;
	}
	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}
	public String getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}
	public String getReceivername() {
		return receivername;
	}
	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}
	public String getSenderid() {
		return senderid;
	}
	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}
	public String getSendername() {
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public String getSenddeptid() {
		return senddeptid;
	}
	public void setSenddeptid(String senddeptid) {
		this.senddeptid = senddeptid;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	public String getApprovaldate() {
		return approvaldate;
	}
	public void setApprovaldate(String approvaldate) {
		this.approvaldate = approvaldate;
	}
	public String getReceiptappr_seq() {
		return receiptappr_seq;
	}
	public void setReceiptappr_seq(String receiptappr_seq) {
		this.receiptappr_seq = receiptappr_seq;
	}
	public String getRegistdate() {
		return registdate;
	}
	public void setRegistdate(String registdate) {
		this.registdate = registdate;
	}
	public String getRegisterid() {
		return registerid;
	}
	public void setRegisterid(String registerid) {
		this.registerid = registerid;
	}
	public Date getRejectdate() {
		return rejectdate;
	}
	public void setRejectdate(Date rejectdate) {
		this.rejectdate = rejectdate;
	}
	public String getRejecterid() {
		return rejecterid;
	}
	public void setRejecterid(String rejecterid) {
		this.rejecterid = rejecterid;
	}
	public String getRejectername() {
		return rejectername;
	}
	public void setRejectername(String rejectername) {
		this.rejectername = rejectername;
	}
	public String getReturncommentflag() {
		return returncommentflag;
	}
	public void setReturncommentflag(String returncommentflag) {
		this.returncommentflag = returncommentflag;
	}
	public String getAssignedflag() {
		return assignedflag;
	}
	public void setAssignedflag(String assignedflag) {
		this.assignedflag = assignedflag;
	}
	public String getAssigncommentflag() {
		return assigncommentflag;
	}
	public void setAssigncommentflag(String assigncommentflag) {
		this.assigncommentflag = assigncommentflag;
	}
	public String getAssignuserid() {
		return assignuserid;
	}
	public void setAssignuserid(String assignuserid) {
		this.assignuserid = assignuserid;
	}
	public String getAssignusername() {
		return assignusername;
	}
	public void setAssignusername(String assignusername) {
		this.assignusername = assignusername;
	}
	public String getAssigndeptid() {
		return assigndeptid;
	}
	public void setAssigndeptid(String assigndeptid) {
		this.assigndeptid = assigndeptid;
	}
	public Date getDocrecvdate() {
		return docrecvdate;
	}
	public void setDocrecvdate(Date docrecvdate) {
		this.docrecvdate = docrecvdate;
	}
	public String getBroadno() {
		return broadno;
	}
	public void setBroadno(String broadno) {
		this.broadno = broadno;
	}
	public Date getBroaddate() {
		return broaddate;
	}
	public void setBroaddate(Date broaddate) {
		this.broaddate = broaddate;
	}
	public String getBroaduserid() {
		return broaduserid;
	}
	public void setBroaduserid(String broaduserid) {
		this.broaduserid = broaduserid;
	}
	public String getBroadusername() {
		return broadusername;
	}
	public void setBroadusername(String broadusername) {
		this.broadusername = broadusername;
	}
	public String getBroaddeptid() {
		return broaddeptid;
	}
	public void setBroaddeptid(String broaddeptid) {
		this.broaddeptid = broaddeptid;
	}
	public String getBroaddeptname() {
		return broaddeptname;
	}
	public void setBroaddeptname(String broaddeptname) {
		this.broaddeptname = broaddeptname;
	}
	public String getUndertakerid() {
		return undertakerid;
	}
	public void setUndertakerid(String undertakerid) {
		this.undertakerid = undertakerid;
	}
	public String getUndertakerdeptid() {
		return undertakerdeptid;
	}
	public void setUndertakerdeptid(String undertakerdeptid) {
		this.undertakerdeptid = undertakerdeptid;
	}
	public String getUndertakername() {
		return undertakername;
	}
	public void setUndertakername(String undertakername) {
		this.undertakername = undertakername;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	
	
}
