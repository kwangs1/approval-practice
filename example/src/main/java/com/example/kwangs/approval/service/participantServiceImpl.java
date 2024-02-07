package com.example.kwangs.approval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.approval.domain.participantVO;
import com.example.kwangs.approval.mapper.participantMapper;

@Service
public class participantServiceImpl implements participantService{

	@Autowired
	private participantMapper mapper;
	
	@Override
	public void participantCheck(participantVO participant) {
		mapper.participantCheck(participant);
	}
}
