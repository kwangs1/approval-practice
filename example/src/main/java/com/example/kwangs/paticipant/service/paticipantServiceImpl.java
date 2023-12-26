package com.example.kwangs.paticipant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.paticipant.domain.paticipantVO;
import com.example.kwangs.paticipant.mapper.paticipantMapper;

@Service
public class paticipantServiceImpl implements paticipantService{

	@Autowired
	private paticipantMapper mapper;
	/*
	@Override
	public void write(paticipantVO vo) {
		mapper.write(vo);
	}*/
}
