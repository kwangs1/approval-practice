package com.example.kwangs.memo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.common.paging.SearchCriteria;
import com.example.kwangs.memo.mapper.memoMapper;
import com.example.kwangs.memo.service.memoService;
import com.example.kwangs.memo.service.memoVO;

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
	public int countList(SearchCriteria scri) {
		return mapper.countList(scri);
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
	
	@Override
	public Map<String, Object> searchStr(SearchCriteria scri) {
		Map<String, Object> result = new HashMap<>();
		List<memoVO> search = mapper.searchStr(scri);
		result.put("searchStr", search);
		return result;
	}
}
