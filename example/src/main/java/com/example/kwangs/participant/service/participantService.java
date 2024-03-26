package com.example.kwangs.participant.service;

import java.util.List;
import java.util.Map;

public interface participantService {
	//문서 기안 시 결재선 지정
	void ParticipantWrite(List<participantVO> participant,String id);

	//일괄 결재
	void BulkAppr(List<participantVO> participant);
	
	//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	List<participantVO> ApprWaitFLowInfo(String appr_seq);
	
	//회수 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	List<participantVO> ApprProgrsFLowInfo(String appr_seq);
	
	//결재 상신 시 결재선 테이블 관련 approvalType, approvalStatus 컬럼 값 셋팅 메서드
	void approvalTypeAndStatus(List<participantVO> participant);
	
	//결재 이후 순번 재지정
	void updateNextApprovalType(String appr_seq);
	
	//결재
	void FlowAppr(participantVO participant);

	//일반 결재 시 상세보기에서의 결재선 정보 
	participantVO pInfo(Map<String, Object> res);

	//문서번호 체결
	void ConCludeDocRegNo(String apprid);
	
	//회수
	void RetireAppr(Map<String,Object> res);
}
