package com.example.kwangs.approval.mapper;

import java.util.List;
import java.util.Map;

import com.example.kwangs.approval.domain.participantVO;

public interface participantMapper {

	void ParticipantWrite(participantVO vo);

	void participantCheck(Map<String, Object> params);

	List<participantVO> getParticipantInfo(String appr_seq);

}
