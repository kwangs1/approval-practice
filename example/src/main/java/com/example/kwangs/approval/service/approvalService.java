package com.example.kwangs.approval.service;

import java.util.List;

import com.example.kwangs.approval.domain.approvalVO;
import com.example.kwangs.approval.domain.participantVO;

public interface approvalService {
	//결재 작성
	void apprWrite(approvalVO approval);
	//결재대기
	List<approvalVO> apprWaitList(String id);
	//결재 상세보기
	approvalVO apprInfo(String appr_seq);
}
