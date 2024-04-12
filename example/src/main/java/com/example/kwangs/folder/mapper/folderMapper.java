package com.example.kwangs.folder.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.folder.service.folderVO;

@Repository
public class folderMapper {

	@Autowired
	private SqlSession session;
	
	//부서 별 폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	public void deptAllFolderAdd(folderVO fd) {
		session.insert("folder.deptAllFolderAdd",fd);
	}
	
	//부서 생성 시 공통 폴더 자동 생성
	public void CreateDeptCommonFolder(folderVO fd) {
		session.insert("folder.CreateDeptCommonFolder",fd);
	}
	//부서 별 폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	public folderVO b_fdInfo() {
		return session.selectOne("folder.b_fdInfo");
	}
	//문서함 사이드 메뉴 부서 폴더 가져올 거 
	public List<folderVO>docfldrSidebar(String ownerid){
		return session.selectList("folder.docfldrSidebar",ownerid);
	}
	//결재선 지정 시 폴더목록 불러올거
	public List<folderVO> DeptFolderList(String ownerid){
		return session.selectList("folder.DeptFolderList",ownerid);
	}
	//문서 목록
	public List<folderVO> list(){
		return session.selectList("folder.list");
	}
	//문서 상세보기
	public folderVO info(String fldrid) {
		return session.selectOne("folder.info",fldrid);
	}
	//기록물철 작성 시 폴더테이블 인서트
	public void apprfolderAdd(folderVO fd) {
		session.insert("folder.apprfolderAdd",fd);
	}
}
