package com.example.kwangs.memo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.memo.domain.memoVO;
import com.example.kwangs.memo.mapper.memoMapper;

import lombok.Setter;

@Service
public class memoServiceImpl implements memoService{
	@Autowired
	private memoMapper mapper;
	
	@Override
	public void write(memoVO memo) {
		mapper.writeSelectKey(memo);
	}
}
