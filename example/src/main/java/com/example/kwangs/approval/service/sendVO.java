package com.example.kwangs.approval.service;

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
	
	
}
