package com.example.kwangs.memo.mapper;

import com.example.kwangs.memo.domain.memoVO;

public interface MemoMapper {
	
	public void writeSelectKey(memoVO memo);
	
	public void write(memoVO memo);
}
