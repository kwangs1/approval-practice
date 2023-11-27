package com.example.kwangs.memo.mapper;

import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.memo.domain.memoVO;
import com.example.kwangs.memo.service.memoServiceImpl;

@Repository
public class memoMapperImpl implements memoMapper{
	private static Logger log = Logger.getLogger(memoMapperImpl.class.getName());
	
	@Autowired
	private SqlSession session;
	
	@Override
	public List<memoVO> read(int mno) {
		return session.selectOne("mapper.memo.read",mno);
	}
	
	@Override
	public List<memoVO> list(){
		log.info("memo Mapper list Success");
		return session.selectList("mapper.memo.list");
	}
	
	@Override
	public void write(memoVO memo) {
		session.insert("mapper.memo.write",memo);
	}
	
	@Override
	public void writeSelectKey(memoVO memo) {
		session.insert("mapper.memo.writeSelectKey",memo);
	}
	
	@Override
	public void TitleUpdate(memoVO memo) {
		session.update("mapper.memo.TitleUpdate",memo);
	}
}
