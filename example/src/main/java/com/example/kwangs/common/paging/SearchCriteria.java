package com.example.kwangs.common.paging;

public class SearchCriteria extends Criteria{
	private String searchType = "";
	private String keyword = "";
	private int mno; //게시글 내용 문자 검색 시 mno기준으로 검색하기 위해 추가
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	
	
}
