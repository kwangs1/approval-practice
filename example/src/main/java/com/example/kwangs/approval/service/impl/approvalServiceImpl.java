package com.example.kwangs.approval.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kwangs.approval.domain.approvalVO;
import com.example.kwangs.approval.domain.participantVO;
import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.approval.mapper.participantMapper;
import com.example.kwangs.approval.service.approvalService;


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
	
	//기안 시 결재선 관련
	@Override
	public void write(List<participantVO> participant){
		log.info("write method 진입");
		int line_seq = 1;
		int approvalstatus = 4097;
		
		String seqCurrval = mapper.getLatestReceiptsSeq();
		log.debug("write seqValue..{}" + seqCurrval);
		
		for (participantVO pVO : participant) {
			pVO.setAppr_seq(seqCurrval);
			log.debug("new_seq getSeqValue...{}" + pVO.getAppr_seq());
			pVO.setLine_seq(line_seq);// 기본값 1
			
			
			approvalType(participant);
			
			if(pVO.getLine_seq() == 1) {
				pVO.setApprovalstatus(approvalstatus);
			}else if(pVO.getLine_seq() > 1 && pVO.getStatus() == 2000 || pVO.getStatus() == 4000){
				pVO.setApprovalstatus(4098);
			}else if(pVO.getLine_seq() > 1 && pVO.getStatus() == 3000){
				pVO.setApprovalstatus(4120);
			}
			
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
	
	//결재 상신 시 결재선 테이블 관련 approvalType 컬럼 값 셋팅 메서드
	public void approvalType(List<participantVO> participant) {
		int totalParticipant = participant.size();
		
		for(int i =0; i < totalParticipant; i++) {
			participantVO pVO = participant.get(i);
			
			if(pVO.getLine_seq() == 1) {
				pVO.setApprovaltype(2);
			}else if(pVO.getLine_seq() == 2) {
				pVO.setApprovaltype(4);
			}else {
				pVO.setApprovaltype(8);
			}
			
		}
	
	}

	@Override
	public List<approvalVO> apprWaitList(String id) {	
		return mapper.apprWaitList(id);
	}
	
	@Override
	public approvalVO apprInfo(String appr_seq) {
		return mapper.apprInfo(appr_seq);
	}
	

}
