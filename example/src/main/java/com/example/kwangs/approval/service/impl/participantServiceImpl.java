package com.example.kwangs.approval.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//일괄 결재 시 결재선 업데이트 
	@Override
	public void participantCheck(List<participantVO> participant) {
		log.info("participant check service...in");
		for(participantVO pp : participant) {
			//put을 통해 key,value를 받아 전달된 인자는 hashMap에 key-value 관계로 저장
			Map<String, Object> params = new HashMap<>();
			params.put("approvaltype", pp.getApprovaltype());
			params.put("approvalstatus", pp.getApprovalstatus());
			params.put("id", pp.getId());
	        params.put("participant_seq", pp.getParticipant_seq());
			params.put("appr_seq", pp.getAppr_seq());
						
			log.info("service {} :"+params);
			mapper.participantCheck(params);
			//결재선 차례 업데이트
			updateNextApprovalType(pp.getAppr_seq());
		}
	}
	//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	@Override
	public List<participantVO>  getParticipantInfo(String appr_seq) {
		return mapper.getParticipantInfo(appr_seq);
	}
	
	//결재 이후 결재선 순번 재지정
	public void updateNextApprovalType(String appr_seq) {
	    log.info("Updating next approval type...");
	    
	    List<participantVO> approvalLines = mapper.getApprovalApprseq(appr_seq);
	    for (int i = 0; i< approvalLines.size(); i++) {
	        log.info("check point..");
	        participantVO currentParticipant = approvalLines.get(i);
	        int line_seq = currentParticipant.getLine_seq();
	        
	        log.info("loop.... ing..");
	        log.info("participant user line_seq: {}", line_seq);
	        
	        // 첫 번째 결재자는 pass
	        if (line_seq == 1) {
	            continue;
	        }   
	        
	        log.info("checked appr_seq.."+appr_seq);
            log.info("checked participant_seq.."+currentParticipant.getParticipant_seq());
	        
	        // 현재 결재자의 approvaltype이 결재를 완료하여 2가 되고 다음 결재자(중간결재자)의 approvalType를 4로 변경
            if (currentParticipant.getApprovaltype() == 2 && i + 1 < approvalLines.size()){
	            participantVO nextParticipant = approvalLines.get(i + 1);
	             
	            // 모든 결재자의 participant_seq 값도 확인하여 업데이트
	            for (participantVO participant : approvalLines) {
	            		String participant_seq = participant.getParticipant_seq();
	            	
	            		log.info("for loop check.."+participant_seq);
	                
	                // 다음 결재자의 participant_seq 값과 현재 participant_seq 값이 일치하는 경우
	                if (nextParticipant.getParticipant_seq() != null && nextParticipant.getParticipant_seq().equals(participant_seq)) {
	                	nextParticipant.setApprovaltype(4);
	                    log.info("Updated next approval type: {}", nextParticipant.getApprovaltype());
	                    
	                    // Map으로 매개변수 전달
	                    Map<String, Object> params = new HashMap<>();
	                    params.put("appr_seq", nextParticipant.getAppr_seq());
	                    params.put("participant_seq", participant_seq);
	                    params.put("approvaltype", nextParticipant.getApprovaltype());
	                    
	                    // mapper를 통해 DB 업데이트 수행
	                    mapper.updateNextApprovalType(params);
	                    
	                    log.info("=======================================");
	                    break; // 현재 결재자의 업데이트가 완료되면 루프 종료
	                }//end if..2
	            }//end for..2
	        }//end if..1
	    }//end for..1
	}
	
}
