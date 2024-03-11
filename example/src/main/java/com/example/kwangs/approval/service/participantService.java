package com.example.kwangs.approval.service;

import java.util.List;
import java.util.Map;

import com.example.kwangs.approval.domain.participantVO;

public interface participantService {
	//문서 기안 시 결재선 지정
	void ParticipantWrite(List<participantVO> participant);

	//일괄 결재
	void BulkAppr(List<participantVO> participant);
	
	//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	List<participantVO> getParticipantInfo(String appr_seq);
	
	//결재
	void FlowAppr(participantVO participant);

	participantVO pInfo(Map<String, Object> res);
}
