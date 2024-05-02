package com.example.kwangs.common.file.service;

import java.util.List;
import java.util.Map;

public interface fileService {

	//첨부파일 리스트[문서에 대해]
	List<AttachVO> getAttachList(String appr_seq);
	//첨부파일 수정 폼
	List<AttachVO> AttachModifyForm(String appr_seq);
	//첨부파일 삭제
	void ApprDocDeleteFiles(Map<String,Object>res);
	//삭제 이후 첨부파일 카운트 결재테이블에 업데이트
	void UpdateDocAttachCnt(String appr_seq,int attachcnt);
	//첨부파일 수정 폼에서의 등록[추가]
	void ApprDocInsertFiles(AttachVO attach);
}
