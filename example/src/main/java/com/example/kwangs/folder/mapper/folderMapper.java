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
	public List<folderVO> list(String ownerid){
		return session.selectList("folder.list",ownerid);
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
	//문서 카운트[문서함]
	public Map<String,Object> getDocFolderCnt(Map<String,Object>res){
		return session.selectOne("folder.getDocFolderCnt",res);
	}
	//문서 삭제 시 결재함 테이블 삭제
	public void deleteDocFldrmbr(String fldrmbrid) {
		session.delete("folder.deleteDocFldrmbr",fldrmbrid);
	}	
	//문서함[함관리] 에서 기록물철 정보
	public apprfolderVO ApprFldrInfo(Map<String, Object> res) {
		return session.selectOne("folder.ApprFldrInfo",res);
	}
	//수정[기록물철-정보]
	public void edit(apprfolderVO af) {
		session.update("folder.edit",af);
	}
	//수정[기록물철-이관년도]
	public void transferYear(apprfolderVO af) {
		session.update("folder.transferYear",af);
	}
	//수정[기록물철-편철상태확인취소]
	public void CancelFldrStatus(apprfolderVO af) {
		session.update("folder.CancelFldrStatus",af);
	}
	public void edit_FInfo(Map<String,Object>res) {
		session.update("folder.edit_FInfo",res);
	}
	//기록물철 추가 시 해당 단위과제 하위 기록물철의 마지막 깊이값 가져오기
	public folderVO getListFldrDepth(String fldrid) {
		return session.selectOne("folder.getListFldrDepth",fldrid);
	}	
	//정리한 기록물철 하위 철 이동 시 정보 업데이트(폴더테이블)
	public void MoveApprFldr(folderVO fd) {
		session.update("folder.MoveApprFldr",fd);
	}
	//정리한 기록물철 하위 철 이동 시 정보 업데이트(기록물철 테이블)
	public void UpdateApFldrInfo(apprfolderVO af) {
		session.update("folder.UpdateApFldrInfo",af);
	}
	//기록물철, 단위과제에 대한 부서 폴더 리스트(깊이 정렬)
	public List<folderVO> DeptFldrList(String ownerid){
		return session.selectList("folder.DeptFldrList",ownerid);
	}
	//문서 이동 시 문서 깊이 중복 체크 후 값 조정 메서드
	public void updateFldrdepth(folderVO fd) {
		session.update("folder.updateFldrdepth",fd);
	}
	/*
	 * 이관 
	 */
	//인계부서 기록물철 정보 업데이트
	public void OriginApprFldrUpd(Map<String,Object>res) {
		session.update("folder.OriginApprFldrUpd",res);
	}	
	//인수부서 정리할 기록물철 정보가져오기.
	public folderVO OrganizeFldrInfo(String ownerid) {
		return session.selectOne("folder.OrganizeFldrInfo",ownerid);
	}
	//기록물철 복사
	public void CopyApprFldr(apprfolderVO af) {
		session.insert("folder.CopyApprFldr",af);
	}
	//폴더 복사
	public void CopyFldr(folderVO fd) {
		session.insert("folder.CopyFldr",fd);
	}
	//인수부서에 인게부서 폴더 복사하는 부분
	public folderVO TakeOverFldr(String fldrid) {
		return session.selectOne("folder.TakeOverFldr",fldrid);
	}
	//이관 시 인계부서에서 fldrmbr2테이블에 해당기록물철 문서 저장된 데이터 읽어오기 
	public List<fldrmbr2VO>getMoveFldrMngList(Map<String,Object>res){
		return session.selectList("folder.getMoveFldrMngList",res);
	}
	//인수부서 기록물등록대장 정보
	public folderVO getOfficialRegister(String ownerid) {
		return session.selectOne("folder.getOfficialRegister",ownerid);
	}
}
