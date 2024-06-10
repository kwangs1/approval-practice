package com.example.kwangs.common.paging;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Criteria {
	private int page;
	private int perPageNum;
	private int rowStart;
	private int rowEnd;
	private String drafterdeptid;
	private String fldrid;
	private String fldrname;
	private String ownerid;
	private int applid;
	private String bizunitcd;
	private String id;
	private String signerid;
	private String receiverid;
	private String appr_seq;
	
	//기본값으로 한 페이지당 보이는 게시물 개수 10개
	//이후 사용자가 원하는 게시물 개수 선택하여 리스트에 보이게 하기 위해 calculateRowStartAndEnd()함수 만듬.
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
        calculateRowStartAndEnd(); 
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
		}else {
			this.page = page;
		}
	}

	public int getPerPageNum() {
		return perPageNum;
	}
	
	//한 페이지당 게시물 개수 설정. calculateRowStartAndEnd 함수를 호출하여 rowStart,rowEnd 갱신.
	public void setPerPageNum(int perPageNum) {
        this.perPageNum = perPageNum;
        calculateRowStartAndEnd();
	}

	public int getRowStart() {
		return rowStart;
	}


	public int getRowEnd() {
		return rowEnd;
	}
	
	//rowStart, rowEnd의 set 값은 사용자가 원하는 개수에 따라 변하게 하기 위해서.
    private void calculateRowStartAndEnd() {
        this.rowStart = ((page - 1) * perPageNum) + 1;
        this.rowEnd = rowStart + perPageNum - 1;
    }

	public String getDrafterdeptid() {
		return drafterdeptid;
	}

	public void setDrafterdeptid(String drafterdeptid) {
		this.drafterdeptid = drafterdeptid;
	}

	public String getFldrid() {
		return fldrid;
	}

	public void setFldrid(String fldrid) {
		this.fldrid = fldrid;
	}

	public String getFldrname() {
		return fldrname;
	}

	public void setFldrname(String fldrname) {
		this.fldrname = fldrname;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public int getApplid() {
		return applid;
	}

	public void setApplid(int applid) {
		this.applid = applid;
	}

	public String getBizunitcd() {
		return bizunitcd;
	}

	public void setBizunitcd(String bizunitcd) {
		this.bizunitcd = bizunitcd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSignerid() {
		return signerid;
	}

	public void setSignerid(String signerid) {
		this.signerid = signerid;
	}
	
	public String getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	public String getAppr_seq() {
		return appr_seq;
	}

	public void setAppr_seq(String appr_seq) {
		this.appr_seq = appr_seq;
	}

	public void cookieVal(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("perPageNum")) {
					int perPageNum = Integer.parseInt(cookie.getValue());
					this.perPageNum = perPageNum;
					calculateRowStartAndEnd();
					break;
				}
			}
		}
	}
	
}
