package com.example.kwangs.folder.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.bizunit.mapper.bizunitMapper;
import com.example.kwangs.bizunit.service.bizunitVO;
import com.example.kwangs.folder.mapper.folderMapper;
import com.example.kwangs.folder.service.apprfolderVO;
import com.example.kwangs.folder.service.folderService;
import com.example.kwangs.folder.service.folderVO;
import com.example.kwangs.user.mapper.userMapper;
import com.example.kwangs.user.service.userVO;

@Service
public class folderServiceimpl implements folderService{

	@Autowired
	private folderMapper mapper;
	@Autowired
	private userMapper userMapper;
	@Autowired
	private bizunitMapper bizMapper;
	
	//부서 별 폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	@Override
	public void deptAllFolderAdd(folderVO fd) {
		/*
		List<deptVO> departments = deptMapper.findAll();
		for(deptVO dept : departments) {
			String ownerid = dept.getDeptid();		
			fd.setOwnerid(ownerid);
		}*/
		mapper.deptAllFolderAdd(fd);
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
			
			List<apprfolderVO> fds = mapper.DeptApprFolderList(ownerid);
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
	public void folderAddAndApprF(folderVO fd,String userid) throws Exception {
		fd.setApplid(7020);
		mapper.folderAddAndApprF(fd);
		
		userVO folderUseInfo = userMapper.folderUseInfo(userid);
		bizunitVO biz = bizMapper.bInfo(fd.getFldrname());
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
		af.setFldrmanagerid(folderUseInfo.getId());
		af.setFldrmanagername(folderUseInfo.getName());
		
		mapper.apprFolderAdd(af);
	}
	
	//결재함 사이드메뉴
	@Override
	public List<folderVO>ApprfldrSidebar(String ownerid){
		return mapper.ApprfldrSidebar(ownerid);
	}
}
