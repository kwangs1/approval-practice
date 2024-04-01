package com.example.kwangs.bizunit.mapper;

import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.bizunit.service.bizunitVO;

@Repository
public class bizunitMapper {
	private static Logger log = Logger.getLogger(bizunitMapper.class.getName());
	
	@Autowired
	private SqlSession session;
	
	
	public void uploadCSV(bizunitVO biz) {
		session.insert("mapper.bizunit.uploadCSV",biz);
	}
	
	public List<bizunitVO> getbizList() {
		return session.selectList("mapper.bizunit.getbizList");
	}
	
	public List<bizunitVO> list(){
		return session.selectList("mapper.bizunit.list");
	}
	
	public void write(bizunitVO biz) {
		session.insert("mapper.bizunit.write",biz);
	}
}
