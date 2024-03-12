package com.example.kwangs.common;

public class Criteria {
	private int page;
	private int perPageNum;
	private int rowStart;
	private int rowEnd;
	
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
	
}
