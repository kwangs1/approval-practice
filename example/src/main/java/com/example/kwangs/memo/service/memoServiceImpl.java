package com.example.kwangs.memo.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.memo.domain.memoVO;
import com.example.kwangs.memo.mapper.memoMapper;

@Service
public class memoServiceImpl implements memoService{
	private static Logger log = Logger.getLogger(memoServiceImpl.class.getName());
	@Autowired
	private memoMapper mapper;
	
	@Override
	public List<memoVO> read(int mno) {
		return mapper.read(mno);
	}

	@Override
	public List<memoVO>list(){
		log.info("memo list Service success");
		return mapper.list();
	}
	
	@Override
	public void write(memoVO memo) {
		mapper.writeSelectKey(memo);
	}
	
	@Override
	public void TitleUpdate(memoVO memo) {
		mapper.TitleUpdate(memo);
	}
}
