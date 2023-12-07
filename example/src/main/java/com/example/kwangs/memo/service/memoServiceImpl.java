package com.example.kwangs.memo.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.SearchCriteria;
import com.example.kwangs.memo.domain.memoVO;
import com.example.kwangs.memo.mapper.memoMapper;

@Service
public class memoServiceImpl implements memoService{
	private static Logger log = Logger.getLogger(memoServiceImpl.class.getName());
	@Autowired
	private memoMapper mapper;
	
	@Override
	public memoVO read(int mno) {
		return mapper.read(mno);
	}
	
	@Override
	public List<memoVO> ajaxList(SearchCriteria scri){
		//log.info("memo list Service success");		
		return mapper.ajaxList(scri);
	}
	
	@Override
	public int countList() {
		return mapper.countList();
	}
	
	@Override
	public void write(memoVO memo) {
		mapper.writeSelectKey(memo);
	}
	
	@Override
	public void TitleUpdate(memoVO memo) {
		mapper.TitleUpdate(memo);
	}
	
	@Override
	public void update(memoVO memo) {
		mapper.update(memo);
	}
}
