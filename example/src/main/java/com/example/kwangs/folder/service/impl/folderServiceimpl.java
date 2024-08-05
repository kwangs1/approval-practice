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

import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.approval.service.approvalVO;
import com.example.kwangs.bizunit.service.bizunitVO;
import com.example.kwangs.folder.mapper.folderMapper;
import com.example.kwangs.folder.service.apprfolderVO;
import com.example.kwangs.folder.service.fldrmbr2VO;
import com.example.kwangs.folder.service.fldrmbrVO;
import com.example.kwangs.folder.service.folderService;
import com.example.kwangs.folder.service.folderVO;

@Service
public class folderServiceimpl implements folderService{

	private final Logger log = LoggerFactory.getLogger(folderServiceimpl.class);

	@Autowired
	private folderMapper mapper;
	@Autowired
	private approvalMapper approvalMapper;
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
	public List<folderVO> list(String ownerid){
		return mapper.list(ownerid);
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
	//문서 카운트[문서함]
	@Override
	public Map<String,Object> getDocFolderCnt(Map<String,Object>res){
		mapper.getDocFolderCnt(res);
		return res;
	}
	//문서 삭제 시 결재함 테이블 삭제
	@Override
	public void deleteDocFldrmbr(String fldrmbrid) {
		mapper.deleteDocFldrmbr(fldrmbrid);
	}
	//문서함[함관리] 에서 기록물철 정보
	@Override
	public apprfolderVO ApprFldrInfo(Map<String, Object> res) {
		return mapper.ApprFldrInfo(res);
	}
	//수정[기록물철-정보]
	@Override
	public void edit(apprfolderVO af,String userid) {
		log.info("Origin fldrname? "+af.getFldrname());
		mapper.edit(af);
		log.info("update gogo");
		Map<String,Object>res = new HashMap<>();
		res.put("fldrid", af.getFldrid());
		res.put("fldrname", af.getFldrname());
		res.put("updaterid", userid);
		res.put("endyear", af.getEndyear());
		log.info("change fldrname? "+af.getFldrname());
		mapper.edit_FInfo(res);
	}
	//수정[기록물철- 예상이관년도]
	@Override
	public void transferYear(apprfolderVO af) {
		mapper.transferYear(af);
	}
	//수정[기록물철-편철상태확인취소]
	@Override
	public void CancelFldrStatus(apprfolderVO af) {
		mapper.CancelFldrStatus(af);
	}
	//기록물철 추가 시 해당 단위과제 하위 기록물철의 마지막 깊이값 가져오기
	@Override
	public folderVO getListFldrDepth(String fldrid) {
		return mapper.getListFldrDepth(fldrid);
	}
	//정리할 기록물철 -> 단위과제 하위 이동
	@Override
	public void MoveApprFldr(folderVO fd,String userid, String name) {
		int originDepth = fd.getCurrentFldrDepth(); //기록물철 이동 전 이동폴더에 대한 기존 깊이값
		mapper.MoveApprFldr(fd);
		List<approvalVO>apprList = approvalMapper.getMoveApprList(fd.getFldrid());//이관 기록물철 인수부서 기록물철 하위 폴더 이동 시 folderid 업데이트
		log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡfolder(폴더 DB)");
		log.info("get Folder Bizunitcd? "+fd.getBizunitcd());
		log.info("get Folder fldrdepth? "+fd.getFldrdepth());
		log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡfldrdepth 체크 로직 start");
		checkFldrDepth(fd,originDepth);
		log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡfldrdepth 체크 로직 end");
		
		log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapprfolder(기록물철 DB) update Strart");
		apprfolderVO af = new apprfolderVO(); //정리할 기록물철 -> 단위과제 아래 이동시 업데이트되는 부분
		af.setBizunitcd(fd.getBizunitcd());
		//af.setBizunityearseq(ApprFldrBizunitYearSeq(fd.getOwnerid()));			
		af.setFldrmanagerid(userid);
		af.setFldrmanagername(name);
		af.setFldrid(fd.getFldrid());
		log.info("apprfolder fldrid? "+fd.getFldrid());
		log.info("apprfolder bizunitcd? "+fd.getBizunitcd());
		log.info("apprfolder procdeptid? "+fd.getOwnerid());
		mapper.UpdateApFldrInfo(af);
		log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapprfolder(기록물철 DB) update End");
		log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapproval(문서 DB) update Start");
		for(approvalVO Info : apprList) { //해당 기록물철에 문서가 있다면 해당 문서에 대한 단위과제,폴더아이디 값 변경
			log.info("getApprList Apprid? "+Info.getAppr_seq());
			approvalVO ap = new approvalVO();
			ap.setBizunitcd(fd.getBizunitcd());
			ap.setOrgdraftdeptid(Info.getDrafterdeptid());
			ap.setFolderid(fd.getFldrid());
			
			log.info("Update ApprInfo Bizunitcd? "+fd.getBizunitcd());
			log.info("Update ApprInfo Orgdraftdeptid? "+ap.getOrgdraftdeptid());
			
			approvalMapper.UpdateApprInfo(ap);
			log.info("Update ApprInfo SUCCESS");
		}
		log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡapproval(문서 DB) update End");
	}
	
	//문서 이동 시 문서 깊이 중복 체크 후 값 조정 메서드
	public void checkFldrDepth(folderVO fd, int originDepth) {
		List<folderVO> list = mapper.DeptFldrList(fd.getOwnerid()); //기록물철, 단위과제에 대한 부서 폴더 리스트
		int NewDepth = fd.getFldrdepth(); //이동 시 선택한 기록물철+ 셀렉트박스에 의한 깊이값 가져옴
		
		if(NewDepth > originDepth) {//현재 깊이에서 새로운 깊이까지의 폴더깊이 1씩 감소
			for(folderVO f : list) {
				log.info("==============================");
				log.info("-- start --");
				log.info("OLD[Minus] -- name_ "+f.getFldrname()+", depth_ "+f.getFldrdepth());
				log.info("중복번호: "+fd.getFldrdepth()+" 번 중복 확인 후 폴더깊이값 수정 시작");
				
				if(f.getFldrdepth() <= NewDepth && f.getFldrdepth() > originDepth) {
					f.setFldrdepth(f.getFldrdepth()-1);
					log.info("NEW[Minus] -- name_ "+f.getFldrname()+", depth_ "+f.getFldrdepth());
					mapper.updateFldrdepth(f);
				}
			}
		}else if(NewDepth < originDepth) { //새로운 깊이에서 현재 깊이까지의 폴더 깊이를 1씩 증가
			for(folderVO f : list) {
				log.info("==============================");
				log.info("-- start --");
				log.info("OLD[Plus] -- name_ "+f.getFldrname()+", depth_ "+f.getFldrdepth());
				log.info("중복번호: "+fd.getFldrdepth()+" 번 중복 확인 후 폴더깊이값 수정 시작");
				
				if(f.getFldrdepth() >= NewDepth && f.getFldrdepth() < originDepth) {
					f.setFldrdepth(f.getFldrdepth()+1);
					log.info("NEW[Plus] -- name_ "+f.getFldrname()+", depth_ "+f.getFldrdepth());
					mapper.updateFldrdepth(f);
				}
			}
		}
		log.info("-- end --");
		log.info("==============================");
		fd.setFldrdepth(NewDepth);
		mapper.updateFldrdepth(fd);
	}

	//이관 시 인계부서 기록물철 -> 인수부서에 같은기록물철로 생성
	public void MoveFldrMng(Map<String,Object>res) {
		mapper.OriginApprFldrUpd(res);//해당 기록물철 업데이트
		apprfolderVO Info = mapper.ApprFldrInfo(res);//인계부서 해당 기록물철 정보
		folderVO OrganizeFldrInfo = mapper.OrganizeFldrInfo(Info.getTodeptid()); //인수부서에 만들어진 기록물철 정보
		
		//기록물철 테이블에서의 복사 [첫 이관시에는.. 기록물철 데이터 인수인계구분값 제외 동일하게 넘김]		
		log.info("apprfolder table insert start.");
		apprfolderVO af = new apprfolderVO();
		af.setFldrinfoyear(Info.getFldrinfoyear()); //함정보년도
		af.setBizunitcd(Info.getBizunitcd()); //단위과제코드
		af.setFilingflag("1"); //폴더 유형
		af.setKeepingperiod(Info.getKeepingperiod()); //보존기간
		af.setProcstatus("0"); //문서처리상태
		af.setFldrmanagerid(Info.getFldrmanagerid());
		af.setFldrmanagername(Info.getFldrmanagername());
		af.setFromdeptid(Info.getFromdeptid());
		af.setTodeptid(Info.getTodeptid());
		af.setProdyear(Info.getProdyear()); //생산년도
		af.setEndyear(Info.getEndyear()); //종료년도
		af.setBizunityearseq(ApprFldrBizunitYearSeq(Info.getTodeptid()));
		af.setOriginfldrid(Info.getFldrid()); //기존 폴더ID
		//인수인계구분
		if(Info.getBiztranstype().equals("1")) {
			af.setBiztranstype("2");
		}else {
			af.setBiztranstype("4");
		}
		af.setProcdeptid(af.getTodeptid());
		mapper.CopyApprFldr(af);// apprfolder insert
		log.info("apprfolder table insert Success.");
		
		//폴더 테이블에서의 복사
		folderVO fd = new folderVO();
		log.info("인수부서..? "+Info.getTodeptid());
		log.info("=== 기록물철명 === "+OrganizeFldrInfo.getFldrname()+" .. folder table start");
		fd.setFldrid(af.getFldrid()); //fldrid는 apprfolder 테이블에 생성된 값과 동일한 값으로 처리
		fd.setFldrname(Info.getFldrname());
		fd.setParfldrid(OrganizeFldrInfo.getFldrid());
		fd.setParfldrname(OrganizeFldrInfo.getFldrname());
		fd.setFldrdepth(OrganizeFldrInfo.getFldrdepth()+1);
		fd.setOwnertype(OrganizeFldrInfo.getOwnertype());
		fd.setOwnerid(OrganizeFldrInfo.getOwnerid());
		fd.setAppltype(OrganizeFldrInfo.getAppltype());
		fd.setApplid(7040);
		fd.setYear(Info.getProdyear());
		fd.setEndyear(Info.getEndyear());
		mapper.CopyFldr(fd);// folder insert
		log.info("folder table insert Success..");
		
		folderVO TakeOverFldr = mapper.TakeOverFldr(Info.getFldrid()); //인수부서에 생성된 폴더 값 정보 가져오기
		log.info("인수 ID "+TakeOverFldr.getFldrid());
		log.info("인수 NAME "+TakeOverFldr.getFldrname());
		//fldrmbr2 테이블 복사 및 approval테이블에서 해당 문서 값 folderid, orgdeptfolderid 값 업데이트.
		List<fldrmbr2VO> getMoveFldrMngList = mapper.getMoveFldrMngList(res);
		List<approvalVO> getMoveApprList = approvalMapper.getMoveApprList(Info.getFldrid());
		//기록물 등록대장 ID값 가져오기
		folderVO OfficialRegister = mapper.getOfficialRegister(Info.getTodeptid());
		for(fldrmbr2VO fm2 : getMoveFldrMngList) {
			fldrmbr2VO af_fm2 =  new fldrmbr2VO();
			af_fm2.setFldrid(af.getFldrid());
			af_fm2.setFldrmbrid(fm2.getFldrmbrid());
			af_fm2.setIndexdate(fm2.getIndexdate());
			af_fm2.setRegistdate(fm2.getRegistdate());
			af_fm2.setRegisterid(fm2.getRegisterid());
			mapper.DocFldrmbr2Add(af_fm2); //fldrmbr apprfolder data isnert
			
			log.info("인수부서 기록물 등록대장 ID "+ OfficialRegister.getFldrid());
			fldrmbr2VO fd_fm2 = new fldrmbr2VO();
			fd_fm2.setFldrid(OfficialRegister.getFldrid());
			fd_fm2.setFldrmbrid(fm2.getFldrmbrid());
			fd_fm2.setIndexdate(fm2.getIndexdate());
			fd_fm2.setRegistdate(fm2.getRegistdate());
			fd_fm2.setRegisterid(fm2.getRegisterid());
			mapper.DocFldrmbr2Add(fd_fm2); //fldrmbr folder 기록물 등록대장 data insert
			
			log.info("fldrmbr2 테이블에 해당 문서 데이터 af, fd 복사 성공이요~");
			for(approvalVO ap:getMoveApprList) {
				log.info("복사한 문서 데이터 값 폴더아이디 업데이트");
				ap.setOrgdeptfolderid(ap.getFolderid());
				ap.setFolderid(af.getFldrid());

				log.info("기존 폴더값 "+ap.getOrgdeptfolderid());
				log.info("인수부서 폴더 값 "+ap.getFolderid());
				log.info("문서 ID "+ap.getAppr_seq());
				
				approvalMapper.UpdMoveApprMng(ap);// approval update
			}
		}
	}
}
