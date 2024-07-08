package com.example.kwangs.folder.service.impl;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.bizunit.service.bizunitVO;
import com.example.kwangs.folder.mapper.folderMapper;
import com.example.kwangs.folder.service.apprfolderVO;
import com.example.kwangs.folder.service.fldrmbrVO;
import com.example.kwangs.folder.service.folderService;
import com.example.kwangs.folder.service.folderVO;

@Service
public class folderServiceimpl implements folderService{

	private final Logger log = LoggerFactory.getLogger(folderServiceimpl.class);

	@Autowired
	private folderMapper mapper;
	/*
	@Autowired
	private userMapper userMapper;
	@Autowired
	private bizunitMapper bizMapper;*/
	
	//기록물철 생성 시 연번 채번
	public String ApprFldrBizunitYearSeq(String procdeptid) {
		apprfolderVO BizunitYearSeq = mapper.findByBizunitSeq(procdeptid);
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String currentYearStr = String.valueOf(currentYear);
		log.info(currentYearStr);
		
		if(BizunitYearSeq == null || !BizunitYearSeq.getFldrinfoyear().equals(currentYearStr)) {
			BizunitYearSeq = new apprfolderVO();
			BizunitYearSeq.setBizunityearseq("000001");
			log.info("해당부서에는 기록물철이 만들어진 이력이 없기에 초기번호로 셋팅이 됩니다.");
		}else {
			int seq = Integer.parseInt(BizunitYearSeq.getBizunityearseq());
			seq += 1;
			BizunitYearSeq.setBizunityearseq(String.format("%06d", seq));
			log.info("해당부서에는 이미 연번이 존재하므로 그다음 번호를 드림 "+BizunitYearSeq.getBizunityearseq());
		}
		return BizunitYearSeq.getBizunityearseq();
	}
	//폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	@Override
	public void FolderAdd(folderVO fd) {
		mapper.FolderAdd(fd);
	}
	
	//하위 폴더 생성
	@Override
	public void subFolderAdd(folderVO fd) {
		mapper.subFolderAdd(fd);
	}
	
	//문서함 사이드 메뉴 부서 폴더 가져올 거 
	@Override
	public List<folderVO>docfldrSidebar(String ownerid){
		return mapper.docfldrSidebar(ownerid);
	}
	//결재선 지정 시 폴더목록 불러올거
	@Override
	public List<folderVO> DeptFolderList(String ownerid){
		List<folderVO> DeptApprFolderList = mapper.DeptFolderList(ownerid);
		for(folderVO fd : DeptApprFolderList) {
			Map<String,Object> res = new HashMap<>();
			res.put("ownerid", ownerid);
			res.put("fldrid", fd.getFldrid());
			
			List<apprfolderVO> fds = mapper.DeptApprFolderList(res);
			fd.setApprfolders(fds);
		}
		return DeptApprFolderList;
	}
	//문서 목록
	@Override
	public List<folderVO> list(){
		return mapper.list();
	}
	//문서 상세보기
	@Override
	public folderVO info(String fldrid) {
		return mapper.info(fldrid);
	}
	//기록물철 등록
	@Override
	public void folderAddAndApprF(folderVO fd, String fldrmanagerid, String fldrmanagername, bizunitVO biz) throws Exception {
		fd.setApplid(7020);
		mapper.folderAddAndApprF(fd);
		
		//userVO folderUseInfo = userMapper.folderUseInfo(userid);
		//bizunitVO biz = bizMapper.bInfo(fd.getOwnerid());
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		String strYear = Integer.toString(year);
		
		//단위과제 하위 폴더 등록 시 기록물철에 같이 insert
		apprfolderVO af = new apprfolderVO();
		af.setFldrid(fd.getFldrid());
		af.setFldrinfoyear(strYear);
		af.setBizunitcd(biz.getBizunitcd());
		af.setProcdeptid(biz.getProcdeptid());
		af.setKeepingperiod(biz.getKeepperiod());
		af.setProdyear(fd.getYear());
		af.setEndyear(fd.getEndyear());
		af.setFldrmanagerid(fldrmanagerid);
		af.setFldrmanagername(fldrmanagername);
		af.setBizunityearseq(ApprFldrBizunitYearSeq(biz.getProcdeptid()));
		
		mapper.apprFolderAdd(af);
	}
	
	//결재함 사이드메뉴
	@Override
	public List<folderVO>ApprfldrSidebar(String ownerid){
		return mapper.ApprfldrSidebar(ownerid);
	}
	
	//기안자 발송대기 폴더 정보가져오기
	@Override
	public folderVO ApprFldrmbr_4030(String ownerid) {
		return mapper.ApprFldrmbr_4030(ownerid);
	}
	//발송대기 폴더 삭제
	@Override
	public void deleteApprFldrmbr_4030(Map<String,Object> sendData_4030) {
		mapper.deleteApprFldrmbr_4030(sendData_4030);
	}
	
	//결재함 폴더 테이블 등록
	@Override
	public void ApprFldrmbrInsert(fldrmbrVO fm) {
		mapper.ApprFldrmbrInsert(fm);
	}
	//접수대기 폴더정보가져오기
	@Override
	public folderVO ApprFldrmbr_5010(String ownerid) {
		return mapper.ApprFldrmbr_5010(ownerid);
	}
	//접수대기폴더 삭제
	@Override
	public void deleteApprFldrmbr_5010(Map<String,Object> sendData_5010) {
		mapper.deleteApprFldrmbr_5010(sendData_5010);
	}
	//접수한 폴더정보가져오기
	@Override
	public folderVO ApprFldrmbr_6050(String ownerid) {
		return mapper.ApprFldrmbr_6050(ownerid);
	}
	//문서 카운트[결재함]
	@Override
	public Map<String,Object> getFolderCounts(Map<String,Object>res){
		mapper.getFolderCounts(res);
		return res;
	}
	//문서 삭제 시 결재함 테이블 삭제
	@Override
	public void deleteDocFldrmbr(String fldrmbrid) {
		mapper.deleteDocFldrmbr(fldrmbrid);
	}
}
