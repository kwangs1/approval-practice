package com.example.kwangs.approval.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.example.kwangs.common.paging.SearchCriteria;
import com.example.kwangs.user.service.userVO;

public interface approvalService {
	//결재 작성
	void apprWrite(approvalVO approval)throws IOException;
	//결재대기
	List<approvalVO> apprWaitList(SearchCriteria scri);
	//결재진행
	List<approvalVO> SanctnProgrsList(SearchCriteria scri);
	//문서함
	List<approvalVO>docFrame(SearchCriteria scri);
	//결재 상세보기
	approvalVO apprInfo(String appr_seq);
	//유저에 대한 부서 약어
	userVO getUserDeptInfo(Map<String, Object> res);
	//회수 시 문서 상태값 변경
	void RetireApprStatus(String appr_seq);
	//재기안 시 문서 상태값 변경
	void Resubmission(approvalVO approval);
	//문서함 문서 총갯수
	int totalDocCnt(SearchCriteria scri);
	//결재대기 문서 총 갯수
	int totalApprCnt(SearchCriteria scri);
	//결재진행, 재기안 시 첨부파일 등록 및 삭제 시 카운트 업데이트
	void UpdAttachCnt(Map<String,Object>res);
}
