package com.example.kwangs.approval.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.approval.domain.participantVO;
import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.approval.mapper.participantMapper;
import com.example.kwangs.approval.service.participantService;

@Service
public class participantServiceImpl implements participantService{
	private Logger log = LoggerFactory.getLogger(participantServiceImpl.class.getName());
	
	@Autowired
	private participantMapper mapper;
	@Autowired
	private approvalMapper approvalMapper;
	
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
	    //appr_seq를 통해 participantVO의 정보를 반복문을 통해 가져옴
	    List<participantVO> approvalLines = mapper.getApprovalApprseq(appr_seq);
	    for (int i = 0; i < approvalLines.size(); i++) {
	        log.info("check point..");
	        //0~ 마지막 까지 
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
	        
	        // 현재 결재자의 approvaltype이 결재를 완료하여 2가 되고,
	        // 다음 결재자(중간결재자 또는 마지막 결재자)의 approvaltype이 8인 경우 approvalType를 4로 변경
	        if (currentParticipant.getApprovaltype() == 2) {
	            // 다음 결재자의 index (첫 결재자 이후 2번쨰 부터 결재를 한 후 while문이 종료되면  1씩 증가 시켜서 다음 결재자 시퀀스를 가져오기 위해
	            int nextIndex = i + 1;
	            
	            // 모든 결재자의 participant_seq 값도 확인하여 업데이트
	            // nextIndex의 값보다 결재선의 길이가 더크다면
	            while (nextIndex < approvalLines.size()) {
	            	//그다음 결재자의 시퀀스를 가져오기 위해 nextIndex를 가져오고 그게에 대해 시퀀스를 가져온다.
	                participantVO nextParticipant = approvalLines.get(nextIndex);
	                String participant_seq = nextParticipant.getParticipant_seq();
	                
	                log.info("for loop check.."+participant_seq);
	                //결재가 되고 가져온 그다음 결재자의 값이 8이면 4로 업데이트를 침
	                if (nextParticipant.getApprovaltype() == 8) {
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
	                    break; // 업데이트 후 루프 종료
	                }
	                
	                nextIndex++;
	                //마지막 결재자 이며 , 마지막 결재자가 결재를 했다면 문서 상태값 완료[256] 변경 
	                if(nextIndex == approvalLines.size() && nextParticipant.getApprovaltype() == 2) {
	                	approvalMapper.ApprovalUpdateStatus(appr_seq);
	                	log.info("final participant and approval status update");
	                }
	            }//end while
	        }//end if (currentParticipant.getApprovaltype() == 2)
	    }//end for
	}

	
}
