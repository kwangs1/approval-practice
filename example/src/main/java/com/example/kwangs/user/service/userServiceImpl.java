package com.example.kwangs.user.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.user.domain.userVO;
import com.example.kwangs.user.mapper.userMapper;

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
}
