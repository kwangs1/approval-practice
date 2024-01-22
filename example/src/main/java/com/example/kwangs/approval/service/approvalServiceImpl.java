package com.example.kwangs.approval.service;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kwangs.approval.domain.approvalVO;
import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.participant.domain.participantVO;
import com.example.kwangs.participant.mapper.participantMapper;


@Service
public class approvalServiceImpl implements approvalService{
	private final Logger log = LoggerFactory.getLogger(approvalServiceImpl.class);

	@Autowired
	private approvalMapper mapper;
	@Autowired
	private participantMapper participantMapper;
	
	@Override
	public void apprView(approvalVO approval) {
		mapper.apprView(approval);
	}
	
	@Override
	public void write(List<participantVO> participant){
		log.info("write method 진입");
		int line_seq = 1;
		
		String seqCurrval = mapper.getLatestReceiptsSeq();
		log.debug("write seqValue..{}" + seqCurrval);
		
		for (participantVO pVO : participant) {
			pVO.setAppr_seq(seqCurrval);
			log.debug("new_seq getSeqValue...{}" + pVO.getAppr_seq());
			pVO.setLine_seq(line_seq);// 기본값 1

			// 이후 insert 된 receipts_seq 값 가져올 것.
			participantMapper.ParticipantWrite(pVO);
			line_seq++;// receitps_seq 별 사용자 번호 순차 증가
		}
	}
	
	@Override
	@Transactional
	public void ApprovlTransanctional(approvalVO approval, List<participantVO> participant) {
	    apprView(approval);
	    write(participant);
	}


}
