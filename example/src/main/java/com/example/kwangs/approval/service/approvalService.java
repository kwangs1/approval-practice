package com.example.kwangs.approval.service;

import java.util.List;
import java.util.Map;

import com.example.kwangs.user.service.userVO;

public interface approvalService {
	//결재 작성
	void apprWrite(approvalVO approval);
	//결재대기
	List<approvalVO> apprWaitList(String id);
	//결재 상세보기
	approvalVO apprInfo(String appr_seq);
	//유저에 대한 부서 약어
	userVO getUserDeptInfo(Map<String, Object> res);
}
