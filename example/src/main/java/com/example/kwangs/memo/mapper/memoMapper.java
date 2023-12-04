package com.example.kwangs.memo.mapper;

import java.util.List;

import com.example.kwangs.memo.domain.memoVO;

public interface memoMapper {
	
	void writeSelectKey(memoVO memo);
	
	void write(memoVO memo);

	void TitleUpdate(memoVO memo);

	memoVO read(int mno);
	
	List<memoVO> ajaxList();

	void update(memoVO memo);
}
