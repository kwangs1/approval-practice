package com.example.kwangs.approval.mapper;

import java.util.List;
import java.util.Map;

import com.example.kwangs.approval.domain.participantVO;

public interface participantMapper {

	//결재 상신 전 결재선 지정
	void ParticipantWrite(participantVO vo);

	//일괄 결재
	void BulkAppr(Map<String, Object> params);

	//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	List<participantVO> getParticipantInfo(String appr_seq);

	//일괄 결재 이후 결재선 타입 변경
	void updateNextApprovalType(Map<String, Object> params);
	
	//일괄 결재 시 기준이 되는 결재 시퀀스
	List<participantVO> getApprovalApprseq(String appr_seq);
	
	//결재
	int FlowAppr(Map<String,Object> res);

	participantVO pInfo(Map<String, Object> res);

}
