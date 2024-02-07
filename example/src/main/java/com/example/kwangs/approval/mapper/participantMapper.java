package com.example.kwangs.approval.mapper;

import com.example.kwangs.approval.domain.participantVO;

public interface participantMapper {

	void ParticipantWrite(participantVO vo);

	void participantCheck(participantVO participant);

}
