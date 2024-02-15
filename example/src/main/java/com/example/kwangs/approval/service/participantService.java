package com.example.kwangs.approval.service;

import java.util.List;

import com.example.kwangs.approval.domain.participantVO;

public interface participantService {

	void participantCheck(List<participantVO> participant);

	List<participantVO> getParticipantInfo(String appr_seq);
}
