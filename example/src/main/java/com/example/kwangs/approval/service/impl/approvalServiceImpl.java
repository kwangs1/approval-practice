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
import com.example.kwangs.common.SearchCriteria;
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
	
	//문서 작성
	@Override
	public void apprWrite(approvalVO approval) {
		String abbr = approval.getDocregno();
		approval.setDocregno(abbr+"-@N");
		mapper.apprWrite(approval);
		
		//기안자의 결재진행&기안한문서 폴더에 관한 결재멤버테이블 등록을 위한 정보 가져오기
		folderVO ApprFldrmbr_2020_D = fMapper.ApprFldrmbr_2020(approval.getDrafterid());
		folderVO ApprFldrmbr_6021_D = fMapper.ApprFldrmbr_6021(approval.getDrafterid());
		
		fldrmbrVO fm_2020_D = new fldrmbrVO();
		fm_2020_D.setFldrid(ApprFldrmbr_2020_D.getFldrid());
		fm_2020_D.setFldrmbrid(approval.getAppr_seq());
		fm_2020_D.setRegisterid(approval.getDrafterid());
		fMapper.ApprFldrmbrInsert(fm_2020_D);
		
		fldrmbrVO fm_6021_D = new fldrmbrVO();
		fm_6021_D.setFldrid(ApprFldrmbr_6021_D.getFldrid());
		fm_6021_D.setFldrmbrid(approval.getAppr_seq());
		fm_6021_D.setRegisterid(approval.getDrafterid());
		fMapper.ApprFldrmbrInsert(fm_6021_D);

	}
	//결재대기
	@Override
	public List<approvalVO> apprWaitList(String id) {	
		return mapper.apprWaitList(id);
	}
	
	//결재진행
	@Override
	public List<approvalVO> SanctnProgrsList(String id) {	
		return mapper.SanctnProgrsList(id);
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

}
