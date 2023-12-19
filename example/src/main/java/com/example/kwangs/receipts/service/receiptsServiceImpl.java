package com.example.kwangs.receipts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.receipts.domain.receiptsVO;
import com.example.kwangs.receipts.mapper.receiptsMapper;

@Service
public class receiptsServiceImpl implements receiptsService{
	@Autowired
	private receiptsMapper mapper;
	
	@Override
	public void write(receiptsVO vo) {
		mapper.write(vo);
	}
}
