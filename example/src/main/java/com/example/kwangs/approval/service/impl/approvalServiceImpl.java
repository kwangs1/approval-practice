package com.example.kwangs.approval.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.Temp.UseFolderDat.saveDatTemp;
import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.approval.service.approvalVO;
import com.example.kwangs.approval.service.sendVO;
import com.example.kwangs.common.file.mapper.fileMapper;
import com.example.kwangs.common.file.service.AttachVO;
import com.example.kwangs.common.paging.SearchCriteria;
import com.example.kwangs.folder.mapper.folderMapper;
import com.example.kwangs.folder.service.fldrmbrVO;
import com.example.kwangs.folder.service.folderVO;
import com.example.kwangs.user.service.userVO;


@Service
public class approvalServiceImpl implements approvalService{
	private final Logger log = LoggerFactory.getLogger(approvalServiceImpl.class);
	@Autowired
	private approvalMapper mapper;
	@Autowired
	private folderMapper fMapper;
	@Autowired
	private fileMapper fileMapper;
	@Autowired
	private saveDatTemp saveDatTemp;
	
	//문서 작성
	@Override
	public void apprWrite(approvalVO approval) throws IOException {
		String abbr = approval.getDocregno();
		approval.setDocregno(abbr+"-@N");		
		
		if ("1".equals(approval.getDocattr())) {
		    log.info(approval.getDocattr());
		    approval.setPoststatus("1");
		    log.info(".. postStatus "+approval.getPoststatus());
		}
		mapper.apprWrite(approval);
		

		//기안자의 기안한문서 폴더에 관한 결재멤버테이블 등록을 위한 정보 가져오기 & 등록
		folderVO ApprFldrmbr_6021 = fMapper.ApprFldrmbr_6021(approval.getDrafterid());	
		fldrmbrVO fm_6021 = new fldrmbrVO();
		fm_6021.setFldrid(ApprFldrmbr_6021.getFldrid());
		fm_6021.setFldrmbrid(approval.getAppr_seq());
		fm_6021.setRegisterid(approval.getDrafterid());
		fMapper.ApprFldrmbrInsert(fm_6021);

		//기록물철 임시저장
		saveDatTemp.saveDataToDatFile(approval.getFolderid(), approval.getFoldername(), approval.getBizunitcd(),approval.getDrafterid());
		//첨부파일
		if(approval.getAttach() == null || approval.getAttach().size() <= 0) {
			log.info("file List: "+approval.getAttach());
			return;
		}
		approval.setAttachcnt(approval.getAttach().size());
		log.info("##### - "+approval.getAttachcnt());
		
		List<AttachVO> attach = approval.getAttach();
		if(attach != null && !attach.isEmpty()) {
			for(AttachVO attachVO : attach) {
				attachVO.setAppr_seq(approval.getAppr_seq());
				fileMapper.DocFileIn(attachVO);
			}
		}					
		
	}
	//결재대기
	@Override
	public List<approvalVO> apprWaitList(SearchCriteria scri) {	
		return mapper.apprWaitList(scri);
	}
	
	//결재진행
	@Override
	public List<approvalVO> SanctnProgrsList(SearchCriteria scri) {	
		return mapper.SanctnProgrsList(scri);
	}	
	//발송대기
	@Override
	public List<approvalVO>SndngWaitDocList(SearchCriteria scri){
		return mapper.SndngWaitDocList(scri);
	}
	//접수대기
	@Override
	public List<approvalVO>RceptWaitDocList(SearchCriteria scri){
		return mapper.RceptWaitDocList(scri);
	}
	//문서함
	@Override
	public List<approvalVO> docFrame(SearchCriteria scri){
		return mapper.docFrame(scri);
	}
	
	//상세보기
	@Override
	public approvalVO apprInfo(String appr_seq) {
		return mapper.apprInfo(appr_seq);
	}
	
	//유저에 대한 부서 약어
	@Override
	public userVO getUserDeptInfo(Map<String,Object>res) {
		return mapper.getUserDeptInfo(res);
	}
	
	//회수 시 문서 상태값 변경
	@Override
	public void RetireApprStatus(String appr_seq) {
		mapper.RetireApprStatus(appr_seq);
	}
	
	//재기안 시 문서 상태값 변경
	@Override
	public void Resubmission(approvalVO approval) {
		mapper.Resubmission(approval);
	}
	//문서함 총 갯수
	@Override
	public int totalDocCnt(SearchCriteria scri) {
		return mapper.totalDocCnt(scri);
	}
	//결재함[결재대기,진행] 문서 총 갯수
	@Override
	public int totalApprCnt(SearchCriteria scri) {
		return mapper.totalApprCnt(scri);
	}
	//결재함[발송대기] 문서 총 갯수
	@Override
	public int TotalSndngWaitCnt(SearchCriteria scri) {
		return mapper.TotalSndngWaitCnt(scri);
	}
	//결재함[접수대기] 문서 총 갯수
	@Override
	public int TotalRceptWaitCnt(SearchCriteria scri) {
		return mapper.TotalRceptWaitCnt(scri);
	}
	//결재진행, 재기안 시 첨부파일 등록 및 삭제 시 카운트 업데이트
	@Override
	public void UpdAttachCnt(Map<String,Object>res) {
		mapper.UpdAttachCnt(res);
	}
	/*
	 * 문서발송
	 * DocSndng - 발송부서의 send테이블 insert
	 * ReceiveDeptIn = 수신부서
	 * UpdDocPostStatus = 발송부서 문서 발송상태값 수정
	*/
	@Override
	public void DocSend(sendVO send) {
		mapper.DocSend(send);
	}
	@Override
	public void ReceiveDeptIn(sendVO send) {
		mapper.ReceiveDeptIn(send);
	}
	@Override
	public void UpdDocPostStatus(String appr_seq) {
		mapper.UpdDocPostStatus(appr_seq);
	}
	//상세보기에서의 접수를 해야할 문서인지 체크
	@Override
	public sendVO getSendInfo(Map<String,Object> send) {
		return mapper.getSendInfo(send);
	}
	//상세보기에서의 접수문서인지 체크
	@Override
	public sendVO getReceptInfo(String appr_seq) {
		return mapper.getReceptInfo(appr_seq);
	}
}
