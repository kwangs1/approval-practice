package com.example.kwangs.approval.mapper;

import java.util.List;

import com.example.kwangs.approval.domain.participantVO;

public interface participantMapper {

	void ParticipantWrite(participantVO vo);

	void participantCheck(List<participantVO>  participant);

	List<participantVO> getParticipantInfo(String appr_seq);

}
