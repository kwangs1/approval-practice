package com.example.kwangs.approval.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.approval.domain.participantVO;
import com.example.kwangs.approval.mapper.participantMapper;
import com.example.kwangs.approval.service.participantService;

@Service
public class participantServiceImpl implements participantService{
	private Logger log = LoggerFactory.getLogger(participantServiceImpl.class.getName());
	
	@Autowired
	private participantMapper mapper;
	
	@Override
	public void participantCheck(participantVO participant) {
		log.info("participantCheck service...in");
		log.info("apprseq{}.."+participant.getAppr_seq());
		log.info("participantseq{}.."+participant.getParticipant_seq());
		log.info("name{}.."+participant.getName());
		log.info("status{}.."+participant.getApprovalstatus());
		log.info("type{}.."+participant.getApprovaltype());
		mapper.participantCheck(participant);
	}
	
	@Override
	public participantVO getParticipantInfo(String appr_seq) {
		return mapper.getParticipantInfo(appr_seq);
	}
}
