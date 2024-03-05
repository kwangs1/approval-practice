package com.example.kwangs.approval.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	//문서 작성
	@Override
	public void apprWrite(approvalVO approval) {
		mapper.apprWrite(approval);
	}
	
	//문서 기안 시 결재선 지정
	@Override
	public void ParticipantWrite(List<participantVO> participant){
		log.info("write method 진입");
		int line_seq = 1;
		
		String seqCurrval = mapper.getLatestReceiptsSeq(); //결재 시퀀스 가져오기
		log.debug("Origin Seq..{}" + seqCurrval);
		
		for (participantVO pVO : participant) {
			pVO.setAppr_seq(seqCurrval);
			log.debug("approval getSeq...{}" + pVO.getAppr_seq());
			pVO.setLine_seq(line_seq);// 기본값 1

			approvalTypeAndStatus(participant);
			// 이후 insert 된 receipts_seq 값 가져올 것.
			participantMapper.ParticipantWrite(pVO);
			line_seq++;// receitps_seq 별 사용자 번호 순차 증가
		}
	}
	
	
	//결재 상신 시 결재선 테이블 관련 approvalType, approvalStatus 컬럼 값 셋팅 메서드
	public void approvalTypeAndStatus(List<participantVO> participant) {
	    int approvalstatus = 4097;
	    boolean isFirst = true;

        // 기안자와 최종 결재자가 같은 경우
	    if(participant.size() == 1) {
	        participantVO pVO = participant.get(0);//리스트의 첫 번쨰 요소
	        pVO.setApprovaltype(2);
	        pVO.setApprovalstatus(approvalstatus);
            mapper.ApprovalUpdateStatus(pVO.getAppr_seq());
	    }
	    //그 외
	    else {
		    for(int i = 0; i < participant.size(); i++) {
		        participantVO pVO = participant.get(i);  
		        
		        // 기안자인 경우 2(결재완료)
		        if(i == 0) {
		            pVO.setApprovaltype(2);
		            pVO.setApprovalstatus(approvalstatus);
		        }            
		        // 중간 결재자이면서 마지막 결재자인 경우 4(결재진행) , 4098 미결재
	            else if(i + 1 == participant.size() && isFirst) {
	                pVO.setApprovaltype(4);
	                pVO.setApprovalstatus(4098);
	            }
		        // 중간 결재자인 경우 4(결재진행) , 4098 미결재
		        else if(i + 1 < participant.size()) {
		            // 가장 앞 번호의 중간 결재자는 4, 나머지는 8
		            if(isFirst) {
		                pVO.setApprovaltype(4);
		                pVO.setApprovalstatus(4098);
		                isFirst = false;
		            } else {
		                pVO.setApprovaltype(8);
		                pVO.setApprovalstatus(4098);
		            }
		        }
		        // 마지막 결재자인 경우 8 (결재대기) ,4098 미결재
		        else if(i == participant.size() - 1) {
			        pVO.setApprovaltype(8);
			        pVO.setApprovalstatus(4098);	
		        }
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
