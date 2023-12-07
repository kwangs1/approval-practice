package com.example.kwangs.memo.mapper;

import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.SearchCriteria;
import com.example.kwangs.memo.domain.memoVO;

@Repository
public class memoMapperImpl implements memoMapper{
	private static Logger log = Logger.getLogger(memoMapperImpl.class.getName());
	
	@Autowired
	private SqlSession session;
	
	@Override
	public memoVO read(int mno) {
		return session.selectOne("mapper.memo.read",mno);
	}
	
	@Override
	public List<memoVO> ajaxList(SearchCriteria scri){
		//log.info("memo Mapper list Success");
		return session.selectList("mapper.memo.ajaxList",scri);
	}
	
	@Override
	public int countList() {
		return session.selectOne("mapper.memo.countList");
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
	
	@Override
	public void update(memoVO memo) {
		session.update("mapper.memo.update",memo);
	}
}
