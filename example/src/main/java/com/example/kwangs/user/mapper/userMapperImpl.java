package com.example.kwangs.user.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.user.domain.userVO;

@Repository
public class userMapperImpl implements userMapper{
	@Autowired
	private SqlSession session;
	
	@Override
	public void write(userVO user) {
		session.insert("mapper.user.write",user);
	}
	
	@Override
	public int idcheck(String id) {
		return session.selectOne("mapper.user.idcheck",id);
	}
	
	@Override
	public userVO login(userVO user) {
		return session.selectOne("mapper.user.login",user);
	}
}
