package com.example.kwangs.memo.service;

import java.util.List;

import com.example.kwangs.SearchCriteria;
import com.example.kwangs.memo.domain.memoVO;

public interface memoService {

	void write(memoVO memo);

	void TitleUpdate(memoVO memo);

	memoVO read(int mno);

	List<memoVO> ajaxList(SearchCriteria scri);

	void update(memoVO memo);

	int countList();

}
