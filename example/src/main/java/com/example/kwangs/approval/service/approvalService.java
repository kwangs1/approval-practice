package com.example.kwangs.approval.service;

import java.util.List;

import com.example.kwangs.approval.domain.approvalVO;
import com.example.kwangs.participant.domain.participantVO;

public interface approvalService {
	//결재화면
	void apprView(approvalVO approval);
	//결재선
	void write(List<participantVO> participant);
	
	void ApprovlTransanctional(approvalVO approval, List<participantVO> participant);

}
