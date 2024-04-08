package com.example.kwangs.approval.service;

import java.util.List;
import java.util.Map;

import com.example.kwangs.user.service.userVO;

public interface approvalService {
	//결재 작성
	void apprWrite(approvalVO approval);
	//결재대기
	List<approvalVO> apprWaitList(String id);
	//결재진행
	List<approvalVO> SanctnProgrsList(String id);
	//문서함
	List<approvalVO>docFrame(String drafterdeptid);
	//결재 상세보기
	approvalVO apprInfo(String appr_seq);
	//유저에 대한 부서 약어
	userVO getUserDeptInfo(Map<String, Object> res);
	//회수 시 문서 상태값 변경
	void RetireApprStatus(String appr_seq);
	//재기안 시 문서 상태값 변경
	void Resubmission(approvalVO approval);
}
