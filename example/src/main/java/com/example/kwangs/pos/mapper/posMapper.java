package com.example.kwangs.pos.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.pos.service.posVO;

@Repository
public class posMapper {

	@Autowired
	private SqlSession session;
	
	public void write(posVO pos) {
		session.insert("mapper.pos.write",pos);
	}
	
	public List<posVO> JoinposList() {
		return session.selectList("mapper.pos.JoinposList");
	}
	
	public List<posVO> list() {
		return session.selectList("mapper.pos.list");
	}
}
