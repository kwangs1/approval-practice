package com.example.kwangs.common.file.service;

import java.util.List;
import java.util.Map;

public interface fileService {

	//첨부파일 리스트[문서에 대해]
	List<AttachVO> getAttachList(String appr_seq);
	//첨부파일 리스트[접수문서에 대해]
	List<AttachVO> getRceptAttachList(String appr_seq);
	//첨부파일 수정 폼
	List<AttachVO> AttachModifyForm(String appr_seq);
	//첨부파일 삭제
	void ApprDocDeleteFiles(Map<String,Object>res);
	//첨부파일 수정 폼에서의 등록[추가]
	void ApprDocInsertFiles(AttachVO attach);
	//해당 문서의 첨부파일 갯수 카운트
	int getAttachCnt(String appr_seq);
	//문서 삭제 시 첨부파일 삭제
	void deleteDocAttach(String appr_seq);
}
