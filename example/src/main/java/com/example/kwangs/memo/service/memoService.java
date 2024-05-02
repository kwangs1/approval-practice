package com.example.kwangs.memo.service;

import java.util.List;
import java.util.Map;

import com.example.kwangs.common.paging.SearchCriteria;

public interface memoService {

	void write(memoVO memo);

	void TitleUpdate(memoVO memo);

	memoVO read(int mno);

	List<memoVO> ajaxList(SearchCriteria scri);

	void update(memoVO memo);

	int countList(SearchCriteria scri);

	Map<String, Object> searchStr(SearchCriteria scri);

}
