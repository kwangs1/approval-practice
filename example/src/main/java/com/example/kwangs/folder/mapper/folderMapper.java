package com.example.kwangs.folder.mapper;

import java.util.List;
import java.util.Map;

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
	
	//기록물철 연번 채번
	public apprfolderVO findByBizunitSeq(String procdeptid) {
		return session.selectOne("folder.findByBizunitSeq",procdeptid);
	}
	//폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	public void FolderAdd(folderVO fd) {
		session.insert("folder.FolderAdd",fd);
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
	public folderVO b_fdInfo(String ownerid) {
		return session.selectOne("folder.b_fdInfo",ownerid);
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
	public List<apprfolderVO> DeptApprFolderList(Map<String,Object> res){
		return session.selectList("folder.DeptApprFolderList",res);
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
	//중간 결재자 및 최종결재자의 결재 차례시 결재대기 폴더 정보 가져오기
	public folderVO ApprFldrmbr_2010(String ownerid) {
		return session.selectOne("folder.ApprFldrmbr_2010",ownerid);
	}
	//중간 결재자 및 최종결재자의 결재 차례시 결재한 문서 폴더 정보 가져오기
	public folderVO ApprFldrmbr_6022(String ownerid) {
		return session.selectOne("folder.ApprFldrmbr_6022",ownerid);
	}
	//기안자의 발송대기 폴더 정보 가져오기
	public folderVO ApprFldrmbr_4030(String ownerid) {
		return session.selectOne("folder.ApprFldrmbr_4030",ownerid);
	}
	// 접수대기 폴더 정보 가져오기
	public folderVO ApprFldrmbr_5010(String ownerid) {
		return session.selectOne("folder.ApprFldrmbr_5010",ownerid);
	}
	//접수한 폴더정보가져오기
	public folderVO ApprFldrmbr_6050(String ownerid) {
		return session.selectOne("folder.ApprFldrmbr_6050",ownerid);
	}
	//기안 시 기안자의 결재진행&기안한문서 폴더에 관한 결재멤버테이블 등록
	public void ApprFldrmbrInsert(fldrmbrVO fm) {
		session.insert("folder.ApprFldrmbrInsert",fm);
	}
	//결재멤버폴더 테이블에서의 해당 문서 결재대기&결재진행 삭제
	public void deleteApprFldrmbr_2010(Map<String,Object> sendData_2010) {
		session.delete("folder.deleteApprFldrmbr_2010",sendData_2010);
	}
	public void deleteApprFldrmbr_2020(Map<String,Object> sendData_2020) {
		session.delete("folder.deleteApprFldrmbr_2020",sendData_2020);
	}
	public void deleteApprFldrmbr_4030(Map<String,Object> sendData_4030) {
		session.delete("folder.deleteApprFldrmbr_4030",sendData_4030);
	}
	public void deleteApprFldrmbr_5010(Map<String,Object> sendData_5010) {
		session.delete("folder.deleteApprFldrmbr_5010",sendData_5010);
	}
	//해당 문서의 결재자들에 대한 결재대기,결재진행 폴더 중복 체크
	public int checkFldrmbr_2010(Map<String,Object> check2010) {
		return session.selectOne("folder.checkFldrmbr_2010",check2010);
	}
	public int checkFldrmbr_2020(Map<String,Object> check2020) {
		return session.selectOne("folder.checkFldrmbr_2020",check2020);
	}	
	//기안한 문서 && 결재한 문서 폴더 중복 체크
	public int checkFldrmbr_6021(Map<String,Object>check6021) {
		return session.selectOne("folder.checkFldrmbr_6021",check6021);
	}
	public int checkFldrmbr_6022(Map<String,Object>check6022) {
		return session.selectOne("folder.checkFldrmbr_6022",check6022);
	}

	//문서 카운트[결재함]
	public Map<String,Object> getFolderCounts(Map<String,Object>res){
		return session.selectOne("folder.getFolderCounts",res);
	}
	//문서 삭제 시 결재함 테이블 삭제
	public void deleteDocFldrmbr(String fldrmbrid) {
		session.delete("folder.deleteDocFldrmbr",fldrmbrid);
	}
}
