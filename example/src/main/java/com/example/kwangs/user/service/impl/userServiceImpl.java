package com.example.kwangs.user.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.user.mapper.userMapper;
import com.example.kwangs.user.service.userService;
import com.example.kwangs.user.service.userVO;

@Service
public class userServiceImpl implements userService{
	private static Logger log = LoggerFactory.getLogger(userService.class);
	@Autowired
	private userMapper mapper;
	
	@Override
	public void write(userVO user) {
		mapper.write(user);
	}
	
	@Override
	public int idcheck(String id) {
		return mapper.idcheck(id);
	}
	
	@Override
	public userVO login(userVO user) {
		log.debug("login success={}",user);
		return mapper.login(user);
	}
	
	@Override
	public List<userVO> list() {
		return mapper.list();
	}
}
