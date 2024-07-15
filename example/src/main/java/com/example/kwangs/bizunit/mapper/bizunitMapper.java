package com.example.kwangs.bizunit.mapper;

import java.util.List;
import java.util.Map;
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
	
	//csv upload
	public void uploadCSV(bizunitVO biz) {
		session.insert("mapper.bizunit.uploadCSV",biz);
	}
	//csv export file  시  가져올 목록
	public List<bizunitVO> getbizList() {
		return session.selectList("mapper.bizunit.getbizList");
	}
	//단위과제 목록
	public List<bizunitVO> list(){
		return session.selectList("mapper.bizunit.list");
	}
	//단위과제 작성
	public void write(bizunitVO biz) {
		session.insert("mapper.bizunit.write",biz);
	}
	//기록물철 작성 시 단위과제 정보 가져오기
	public bizunitVO bInfo(Map<String,Object>res) {
		return session.selectOne("mapper.bizunit.bInfo",res);
	}
}
