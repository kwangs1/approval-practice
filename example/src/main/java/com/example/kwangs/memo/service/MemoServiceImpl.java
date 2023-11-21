package com.example.kwangs.memo.service;

import org.springframework.stereotype.Service;

import com.example.kwangs.memo.domain.memoVO;
import com.example.kwangs.memo.mapper.MemoMapper;

@Service
public class MemoServiceImpl implements MemoService{

	private MemoMapper mapper;
	
	@Override
	public void write(memoVO memo) {
		mapper.writeSelectKey(memo);
	}
}
