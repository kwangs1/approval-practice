package com.example.kwangs.memo.mapper;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.common.SearchCriteria;
import com.example.kwangs.memo.service.memoVO;

@Repository
public class memoMapper{
	private static Logger log = Logger.getLogger(memoMapper.class.getName());
	

	private SqlSession session;
	

	public memoVO read(int mno) {
		return session.selectOne("mapper.memo.read",mno);
	}
	

	public List<memoVO> ajaxList(SearchCriteria scri){
		//log.info("memo Mapper list Success");
		return session.selectList("mapper.memo.ajaxList",scri);
	}
	

	public int countList(SearchCriteria scri) {
		return session.selectOne("mapper.memo.countList",scri);
	}
	

	public void write(memoVO memo) {
		session.insert("mapper.memo.write",memo);
	}
	

	public void writeSelectKey(memoVO memo) {
		session.insert("mapper.memo.writeSelectKey",memo);
	}
	

	public void TitleUpdate(memoVO memo) {
		session.update("mapper.memo.TitleUpdate",memo);
	}
	

	public void update(memoVO memo) {
		session.update("mapper.memo.update",memo);
	}
	

	public List<memoVO> searchStr(SearchCriteria scri) {
		return session.selectList("mapper.memo.searchStr",scri);
	}
}
