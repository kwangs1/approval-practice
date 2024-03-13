package com.example.kwangs.dept.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.kwangs.user.service.userVO;

@Component
public class deptVO {

	private String deptid;
	private String deptname;
	private String parid;
	private String abbreviation;
	private String sendername;
	private Date credate;
	private String org_deptname;
	private List<userVO> users; //부서& 유저 조직트리를 위해 userVO 추가
	
	
	
	public List<userVO> getUsers() {
		return users;
	}
	public void setUsers(List<userVO> users) {
		this.users = users;
	}
	public String getOrg_deptname() {
		return org_deptname;
	}
	public void setOrg_deptname(String org_deptname) {
		this.org_deptname = org_deptname;
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
	public String getParid() {
		return parid;
	}
	public void setParid(String parid) {
		this.parid = parid;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getSendername() {
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public Date getCredate() {
		return credate;
	}
	public void setCredate(Date credate) {
		this.credate = credate;
	}
	
	
}
