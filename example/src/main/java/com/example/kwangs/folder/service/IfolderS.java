package com.example.kwangs.folder.service;

import java.util.List;

public interface IfolderS {
	//부서 별 폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	void deptAllFolderAdd(folderVO fd);
	//문서함 사이드 메뉴 부서 폴더 가져올 거 
	List<folderVO> docfldrSidebar(String ownerid);
	//결재선 지정 시 폴더목록 불러올거
	List<folderVO> DeptFolderList(String ownerid);
	//문서 목록
	List<folderVO> list();
	//문서 상세보기
	folderVO info(String fldrid);
	//기록물철 작성
	void apprfolderAdd(folderVO fd, String userid) throws Exception;
}
