package com.example.kwangs.approval.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.approval.service.Document;
import com.example.kwangs.approval.service.approvalVO;
import com.example.kwangs.approval.service.sendVO;
import com.example.kwangs.common.paging.SearchCriteria;
import com.example.kwangs.dept.service.deptVO;
import com.example.kwangs.user.service.userVO;

@Repository
public class approvalMapper{
	private final Logger log = LoggerFactory.getLogger(approvalMapper.class);
	@Autowired
	private SqlSession session;
	
	//결재선 지정 후 결재 상신 시 결재 테이블에 결재 시퀀스 값 가져오기 위한 시퀀스 셀렉트
	public String getLatestReceiptsSeq() {
		return session.selectOne("mapper.approval.getLatestReceiptsSeq");
	}
	
	//결재 작성
	public void apprWrite(approvalVO approval) {
		session.insert("mapper.approval.apprWrite",approval);
	}
	
	//결재 대기
	public List<approvalVO> apprWaitList(SearchCriteria scri) {
		return session.selectList("mapper.approval.apprWaitList",scri);
	}
	
	//결재 진행
	public List<approvalVO> SanctnProgrsList(SearchCriteria scri) {
		return session.selectList("mapper.approval.SanctnProgrsList",scri);
	}
	
	//발송대기
	public List<approvalVO>SndngWaitDocList(SearchCriteria scri){
		return session.selectList("mapper.approval.SndngWaitDocList",scri);
	}
	//접수대기
	public List<approvalVO>RceptWaitDocList(SearchCriteria scri){
		return session.selectList("mapper.approval.RceptWaitDocList",scri);
	}
	//문서함
	public List<approvalVO> docFrame(SearchCriteria scri){
		return session.selectList("mapper.approval.docFrame",scri);
	}
	
	//결재 상세보기
	public approvalVO apprInfo(String appr_seq) {
		return session.selectOne("mapper.approval.apprInfo",appr_seq);
	}
	
	//최종 결재자 결재 이후 문서 상태값 변경
	public int ApprovalUpdateStatus(String appr_seq) {
		return session.update("mapper.approval.ApprovalUpdateStatus",appr_seq);
	}
	
	//유저에 대한 부서 약어
	public userVO getUserDeptInfo(Map<String,Object>res) {
		return session.selectOne("mapper.approval.getUserDeptInfo",res);
	}
	
	//문서 번호 체결에 대한 Mapper start
	//결재 완료 문서 찾기
	public List<approvalVO> getApprStatus(String appr_seq){
		return session.selectList("mapper.approval.getApprStatus",appr_seq);
	}
	//기안자 부서 가져오기
	public approvalVO ApprDocDeptInfo(String appr_seq) {
		return session.selectOne("mapper.approval.ApprDocDeptInfo",appr_seq);
	}
	public deptVO DocDeptInfo(String deptid) {
		return session.selectOne("mapper.approval.DocDeptInfo",deptid);
	}
	//문서번호 업데이트
	public void ConCludeDocRegNo(approvalVO ap) {
		session.update("mapper.approval.ConCludeDocRegNo",ap);
	}
	//문서번호 가져오기
	public Document findByDeptDocNo(String deptid) {
		return session.selectOne("mapper.approval.findByDeptDocNo",deptid);
	}
	//문서번호 저장
	public int save(Document document) {
		return session.insert("mapper.approval.save",document);
	}
	//end
	
	//문서 회수 시 문서 상태값 변경
	public void RetireApprStatus(String appr_seq) {
		session.update("mapper.approval.RetireApprStatus",appr_seq);
	}
	//재기안 시 문서 상태값 변경
	public void Resubmission(approvalVO approval) {
		session.update("mapper.approval.Resubmission",approval);
	}
	//문서함 총 갯수
	public int totalDocCnt(SearchCriteria scri) {
		return session.selectOne("mapper.approval.totalDocCnt",scri);
	}
	//결재함[결재대기,진행] 문서 총갯수
	public int totalApprCnt(SearchCriteria scri) {
		return session.selectOne("mapper.approval.totalApprCnt",scri);
	}
	//결재함[발송대기] 문서 총 갯수
	public int TotalSndngWaitCnt(SearchCriteria scri) {
		return session.selectOne("mapper.approval.TotalSndngWaitCnt",scri);
	}
	//결재함[접수대기] 문서 총 갯수
	public int TotalRceptWaitCnt(SearchCriteria scri) {
		return session.selectOne("mapper.approval.TotalRceptWaitCnt",scri);
	}
	//결재진행, 재기안 시 첨부파일 등록 및 삭제 시 카운트 업데이트
	public void UpdAttachCnt(Map<String,Object>res) {
		session.update("mapper.approval.UpdAttachCnt",res);
	}
	/*
	 * 문서발송
	 * DocSndng - 발송부서의 send테이블 insert
	 * ReceiveDeptIn = 수신부서
	 * UpdDocPostStatus = 발송부서 문서 발송상태값 수정
	*/
	public void DocSend(sendVO send) {
		session.insert("mapper.approval.DocSend",send);
	}
	public void ReceiveDeptIn(sendVO send) {
		session.insert("mapper.approval.ReceiveDeptIn",send);
	}
	public void UpdDocPostStatus(String appr_seq) {
		session.update("mapper.approval.UpdDocPostStatus",appr_seq);
	}
	//상세보기에서의 접수를 해야할 문서인지 체크
	public sendVO getSendInfo(Map<String,Object> send) {
		return session.selectOne("mapper.approval.getSendInfo",send);
	}
	//상세보기에서의 접수문서인지 체크
	public sendVO getReceptInfo(Map<String,Object> send) {
		return session.selectOne("mapper.approval.getReceptInfo",send);
	}
	//접수대기 -> 접수 시 기존 apprid 가져오는 부분
	public sendVO getSendOrgApprId(String appr_seq) {
		return session.selectOne("mapper.approval.getSendOrgApprId",appr_seq);
	}
	//발송 시 fldrmbr테이블에 fldrmbrid는 각 부서에 체결된 sendid로 기입
	public sendVO getSendId(Map<String,Object> res) {
		return session.selectOne("mapper.approval.getSendId",res);
	}
	//접수대기 문서 접수하기	
	public void RceptDocSang(approvalVO ap) {
		session.insert("mapper.approval.RceptDocSang",ap);
	}
	//접수 이후 해당문서에대한 send테이블에서의 값 업데이트
	public void updSendData(String sendid) {
		session.update("mapper.approval.updSendData",sendid);
	}
	//문서 삭제
	public int DeleteDoc(String appr_seq) {
		return session.delete("mapper.approval.DeleteDoc",appr_seq);
	}
	//이관 시 해당 기록물철에 등록된 문서리스트
	public List<approvalVO> getMoveApprList(String folderid){
		return session.selectList("mapper.approval.getMoveApprList",folderid);
	}
	//이관 이후 문서 폴더값 업뎃
	public void UpdMoveApprMng(approvalVO ap) {
		session.update("mapper.approval.UpdMoveApprMng",ap);
	}
	//
	public void UpdateApprInfo(approvalVO ap) {
		session.update("mapper.approval.UpdateApprInfo",ap);
	}
}
