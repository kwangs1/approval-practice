package com.example.kwangs.participant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.participant.mapper.participantMapper;

@Service
public class participantServiceImpl implements participantService{

	@Autowired
	private participantMapper mapper;
}
