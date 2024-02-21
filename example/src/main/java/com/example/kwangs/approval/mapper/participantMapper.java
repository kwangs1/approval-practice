package com.example.kwangs.approval.mapper;

import java.util.List;
import java.util.Map;

import com.example.kwangs.approval.domain.participantVO;

public interface participantMapper {

	//결재 상신 전 결재선 지정
	void ParticipantWrite(participantVO vo);

	//일괄 결재 시 결재선 업데이트 
	void participantCheck(Map<String, Object> params);

	//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	List<participantVO> getParticipantInfo(String appr_seq);

}
