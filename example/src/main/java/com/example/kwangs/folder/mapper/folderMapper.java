package com.example.kwangs.folder.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.folder.service.apprfolderVO;
import com.example.kwangs.folder.service.fldrmbr2VO;
import com.example.kwangs.folder.service.fldrmbrVO;
import com.example.kwangs.folder.service.folderVO;

@Repository
public class folderMapper {

	@Autowired
	private SqlSession session;
	
	//부서 별 폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	public void deptAllFolderAdd(folderVO fd) {
		session.insert("folder.deptAllFolderAdd",fd);
	}
	//하위 폴더 생성
	public void subFolderAdd(folderVO fd) {
		session.insert("folder.subFolderAdd",fd);
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
	public void folderAddAndApprF(folderVO fd) {
		session.insert("folder.folderAddAndApprF",fd);
	}
	//기록물철 작성
	public void apprFolderAdd(apprfolderVO af) {
		session.insert("folder.apprFolderAdd",af);
	}
	//기안 시 기록물철 가져와서 집어넣기
	public List<apprfolderVO> DeptApprFolderList(String procdeptid){
		return session.selectList("folder.DeptApprFolderList",procdeptid);
	}
	//최종 결재 이후 문서폴더 멤버 테이블 insert
	public void DocFldrmbr2Add(fldrmbr2VO fm2) {
		session.insert("folder.DocFldrmbr2Add",fm2);
	}
	//문서함 기록물 등록대장 fldrid 가져오기
	public folderVO DocFloder(String ownerid) {
		return session.selectOne("folder.DocFloder",ownerid);
	}
	//결재함 사이드메뉴
	public List<folderVO>ApprfldrSidebar(String ownerid){
		return session.selectList("folder.ApprfldrSidebar",ownerid);
	}
	//기안 시 기안자의 결재진행&기안한문서 폴더에 관한 결재멤버테이블 등록을 위한 정보 가져오기
	public folderVO ApprFldrmbr_2020(String ownerid) {
		return session.selectOne("folder.ApprFldrmbr_2020",ownerid);
	}
	public folderVO ApprFldrmbr_6021(String ownerid) {
		return session.selectOne("folder.ApprFldrmbr_6021",ownerid);
	}
	//기안 시 기안자의 결재진행&기안한문서 폴더에 관한 결재멤버테이블 등록
	public void ApprFldrmbrInsert(fldrmbrVO fm) {
		session.insert("folder.ApprFldrmbrInsert",fm);
	}
}
