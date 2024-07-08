package com.example.kwangs.folder.service;

import java.util.List;
import java.util.Map;

import com.example.kwangs.bizunit.service.bizunitVO;

public interface folderService {
	//폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	void FolderAdd(folderVO fd);
	//하위 폴더 생성
	void subFolderAdd(folderVO fd);
	//문서함 사이드 메뉴 부서 폴더 가져올 거 
	List<folderVO> docfldrSidebar(String ownerid);
	//결재선 지정 시 폴더목록 불러올거
	List<folderVO> DeptFolderList(String ownerid);
	//문서 목록
	List<folderVO> list();
	//문서 상세보기
	folderVO info(String fldrid);
	//기록물철 작성
	void folderAddAndApprF(folderVO fd, String fldrmanagerid, String fldrmanagername, bizunitVO biz) throws Exception;
	//결재함 사이드메뉴
	List<folderVO>ApprfldrSidebar(String ownerid);
	//기안자 발송대기 폴더 정보가져오기
	folderVO ApprFldrmbr_4030(String ownerid);
	//발송대기 폴더 삭제
	void deleteApprFldrmbr_4030(Map<String,Object> sendData_4030);
	//결재함 폴더 테이블 등록
	void ApprFldrmbrInsert(fldrmbrVO fm);
	//접수대기 폴더정보가져오기
	folderVO ApprFldrmbr_5010(String ownerid);
	//접수대기폴더 삭제
	void deleteApprFldrmbr_5010(Map<String,Object> sendData_5010);
	//접수한 폴더정보가져오기
	folderVO ApprFldrmbr_6050(String ownerid);
	//문서 카운트[결재함]
	Map<String,Object> getFolderCounts(Map<String,Object>res);
	//문서 삭제 시 결재함테이블 에서 삭제
	void deleteDocFldrmbr(String fldrmbrid);
}
