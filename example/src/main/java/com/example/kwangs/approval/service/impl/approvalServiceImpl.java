package com.example.kwangs.approval.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.approval.service.approvalVO;
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
	
	//문서 작성
	@Override
	public void apprWrite(approvalVO approval) {
		String abbr = approval.getDocregno();
		approval.setDocregno(abbr+"-@N");
		mapper.apprWrite(approval);
		
		//기안자의 기안한문서 폴더에 관한 결재멤버테이블 등록을 위한 정보 가져오기 & 등록
		folderVO ApprFldrmbr_6021 = fMapper.ApprFldrmbr_6021(approval.getDrafterid());	
		fldrmbrVO fm_6021 = new fldrmbrVO();
		fm_6021.setFldrid(ApprFldrmbr_6021.getFldrid());
		fm_6021.setFldrmbrid(approval.getAppr_seq());
		fm_6021.setRegisterid(approval.getDrafterid());
		fMapper.ApprFldrmbrInsert(fm_6021);
		
		if(approval.getAttach() == null || approval.getAttach().size() <= 0) {
			log.info("file List: "+approval.getAttach());
			return;
		}
		List<AttachVO> attach = approval.getAttach();
		for(int i=0; i < attach.size(); i++) {
			AttachVO attachVO = attach.get(i);
			attachVO.setAppr_seq(approval.getAppr_seq());
			fileMapper.DocFileIn(attachVO);
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
	//결재대기 문서 총 갯수
	@Override
	public int totalApprCnt(SearchCriteria scri) {
		return mapper.totalApprCnt(scri);
	}

}
