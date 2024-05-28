package com.example.kwangs.participant.service;

import java.util.List;
import java.util.Map;

public interface participantService {
	/*
	 * participant 내 기능 메서드
	 */
	//기안 시 기안자 다음 결재자의 결재대기,결재진행 폴더 생성
	public void IntermediateApprFldrmbr(String appr_seq);
	//결재 상신 시 결재선 테이블 관련 approvalType, approvalStatus 컬럼 값 셋팅 메서드
	void approvalTypeAndStatus(List<participantVO> participant);	
	//결재 이후 순번 재지정
	void updateNextApprovalType(String appr_seq);
	//문서번호 체결
	void ConCludeDocRegNo(String apprid);
	//최종 결재 이후 문서폴더멤버 테이블 같이 insert
	public void DocFldrmbr2Add(String appr_seq);
	//participant 내 기능 메서드 끝.
	
	//문서 기안 시 결재선 지정
	void ParticipantWrite(List<participantVO> participant,String id);
	//일괄 결재
	void BulkAppr(List<participantVO> participant);	
	//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	List<participantVO> ApprWaitFLowInfo(String appr_seq);	
	//회수 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	List<participantVO> ApprProgrsFLowInfo(String appr_seq);
	//발송대기 리스트에서 결재선 정보 가져오기
	List<participantVO> SndngWaitflowInfo(String appr_seq);
	//결재
	void FlowAppr(participantVO participant);
	//일반 결재 시 상세보기에서의 결재선 정보 
	participantVO pInfo(Map<String, Object> res);
	//회수
	void RetireAppr(Map<String,Object> res);		
	//재기안 시 해당 문서에 대한 결재자 정보 가져오기
	List<participantVO> getRe_pInfo(String appr_seq);
	//접수문서 시 기안부서 결재자 정보
	List<participantVO> getRcept_pInfo(String appr_seq);	
	//재기안 시 결재자 상태값 업데이트
	void ResubmissionFlowStatusUpd(List<participantVO> participant);
	//재기안 시 결재선 새로 추가
	 void ResubmissionParticipantWrite(List<participantVO> participant,String id);
}
