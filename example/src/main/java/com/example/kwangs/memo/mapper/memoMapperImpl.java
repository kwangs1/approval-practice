package com.example.kwangs.memo.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.memo.domain.memoVO;

@Repository
public class memoMapperImpl implements memoMapper{
	
	@Autowired
	private SqlSession session;
	
	@Override
	public void write(memoVO memo) {
		session.insert("mapper.memo.write",memo);
	}
	
	@Override
	public void writeSelectKey(memoVO memo) {
		session.insert("mapper.memo.writeSelectKey",memo);
	}
}
