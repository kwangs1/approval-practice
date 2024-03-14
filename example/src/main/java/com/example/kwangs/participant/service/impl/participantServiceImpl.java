package com.example.kwangs.participant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.approval.service.approvalVO;
import com.example.kwangs.participant.mapper.participantMapper;
import com.example.kwangs.participant.service.participantService;
import com.example.kwangs.participant.service.participantVO;
import com.example.kwangs.user.service.userVO;

@Service
public class participantServiceImpl implements participantService{
	private Logger log = LoggerFactory.getLogger(participantServiceImpl.class.getName());
	
	@Autowired
	private participantMapper mapper;
	@Autowired
	private approvalMapper approvalMapper;
	@Autowired
	private HttpServletRequest request;
	

	//문서 기안 시 결재선 지정
	@Override
	public void ParticipantWrite(List<participantVO> participant){
		log.info("write method 진입");
		int line_seq = 1;
		
		String seqCurrval = approvalMapper.getLatestReceiptsSeq(); //결재 시퀀스 가져오기
		log.debug("Origin Seq..{}" + seqCurrval);
		
		for (participantVO pVO : participant) {
			pVO.setAppr_seq(seqCurrval);
			log.debug("approval getSeq...{}" + pVO.getAppr_seq());
			pVO.setLine_seq(line_seq);// 기본값 1

			approvalTypeAndStatus(participant);
			// 이후 insert 된 receipts_seq 값 가져올 것.
			mapper.ParticipantWrite(pVO);
			line_seq++;// receitps_seq 별 사용자 번호 순차 증가
		}
	}
	
	//일괄 결재
	@Override
	public void BulkAppr(List<participantVO> participant) {
		for(participantVO pp : participant) {
			//put을 통해 key,value를 받아 전달된 인자는 hashMap에 key-value 관계로 저장
			Map<String, Object> params = new HashMap<>();
			params.put("approvaltype", pp.getApprovaltype());
			params.put("approvalstatus", pp.getApprovalstatus());
			params.put("id", pp.getId());
	        params.put("participant_seq", pp.getParticipant_seq());
			params.put("appr_seq", pp.getAppr_seq());
						
			log.info("service {} :"+params);
			mapper.BulkAppr(params);
			//결재선 차례 업데이트
			updateNextApprovalType(pp.getAppr_seq());
		}
	}
	
	//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	@Override
	public List<participantVO>  getParticipantInfo(String appr_seq) {
		return mapper.getParticipantInfo(appr_seq);
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
	        //결재문서 상태값 변경
	        approvalMapper.ApprovalUpdateStatus(pVO.getAppr_seq());
	        ConCludeDocRegNo(pVO.getAppr_seq());
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
	        if (line_seq == approvalLines.get(0).getLine_seq()) {
	            continue;
	        }   
	        
	        log.info("checked appr_seq.."+appr_seq);
	        log.info("checked participant_seq.."+currentParticipant.getParticipant_seq());
	        log.info("checked currParticipant type.."+currentParticipant.getApprovaltype());
	        log.info("check size length.."+approvalLines.size());
	        
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
	                int nextLine_seq = nextParticipant.getLine_seq();
	                
	                log.info("Next Line_seq.."+nextLine_seq);
	                log.info("NextIndex.."+nextIndex);
	                log.info("for loop check.."+participant_seq);
	                //결재가 되고 가져온 그다음 결재자의 값이 8이면 4로 업데이트를 침
	                if (nextParticipant.getApprovaltype() == 8 && nextLine_seq == currentParticipant.getLine_seq() +1) {
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
	                if(nextIndex == approvalLines.size() && nextParticipant.getApprovaltype() == 2 ) {
	                	approvalMapper.ApprovalUpdateStatus(appr_seq);
	                	log.info("final participant and approval status update");
	                	ConCludeDocRegNo(appr_seq);
	                }
	            }//end while
	            /*
	             * 기안자,최종결재자 둘만 있을 시 while문을 타지 않기에 while문 밖에서 기안자,최종결재자만 있는 결재문서일 경우
	             * 문서 상태값 완료로 변경            	
	            */
	            if(currentParticipant.getApprovaltype() == 2 && nextIndex == approvalLines.size()) {
                	approvalMapper.ApprovalUpdateStatus(appr_seq);
                	log.info("final participant and approval status update");
                	ConCludeDocRegNo(appr_seq);
	            }
	        }//end if (currentParticipant.getApprovaltype() == 2)
	    }//end for
	}
	
	//결재
	@Override
	public void FlowAppr(participantVO participant) {
		Map<String,Object> res = new HashMap<>();
		res.put("appr_seq", participant.getAppr_seq());
		res.put("participant_seq", participant.getParticipant_seq());
		res.put("approvaltype", participant.getApprovaltype());
		res.put("approvalstatus", participant.getApprovalstatus());
		res.put("id", participant.getId());
		mapper.FlowAppr(res);
		
		log.info("service FlowAppr RecData {}"+res);
		updateNextApprovalType(participant.getAppr_seq());
		
	}

	//일반 결재 시 상세보기에서의 결재선 정보 
	@Override
	public participantVO pInfo(Map<String,Object> res) {
		return mapper.pInfo(res);
	}
	
	//문서번호 체번
	public void ConCludeDocRegNo(String appr_seq) {
		log.info("=================== DOCNO UPDATE LINE ===================");
		//결재가 완료된 문서찾기
		List<approvalVO> approval = approvalMapper.getApprStatus(appr_seq);
		
		//기안자의 부서 가져오기
		String id = (String)request.getSession().getAttribute("userId");
		userVO checkDocDept = approvalMapper.getDocDept(id);
		
		String deptid = checkDocDept.getDeptid();
		String abbreviation = checkDocDept.getAbbreviation();
		log.info("Use Info"+ id +"/"+ deptid +"/"+ abbreviation);

		for(approvalVO ap : approval) {
			if(ap.getStatus() == 256 && id.equals(ap.getId()) && deptid.equals(ap.getDrafterdeptid())) {
				
				log.info("regno value{}"+ap.getRegno());
				//현재의 번호를 가져오며 , 만약 값이 없다면 1부터 , 있다면 제일 큰수 +1
				List<Integer> currSeq = approvalMapper.getCurrSeq(ap.getDrafterdeptid());
				int seq;
				
				if(currSeq.isEmpty()) {
					seq = 1;
				}else {
					seq = currSeq.get(0)+1;
				}
				log.info(ap.getAppr_seq()+" -> CurrSeq value{} "+seq);	
				
				ap.setDocregno(abbreviation+"-"+seq);

				//문서번호 업데이트
				approvalMapper.ConCludeDocRegNo(ap);

				//다음 문서번호 가져올 번호 업데이트
				approvalMapper.getNextSeq(ap.getDrafterdeptid());
				log.info("NextSeq value{}"+seq);
			}
		}
		log.info("=================== DOCNO UPDATE LINE ===================");
	}
}
