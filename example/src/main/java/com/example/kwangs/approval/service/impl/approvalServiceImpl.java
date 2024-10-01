package com.example.kwangs.approval.service.impl;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.Temp.UseFolderDat.saveDatTemp;
import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.approval.service.action_log_sanc;
import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.approval.service.approvalVO;
import com.example.kwangs.approval.service.opinionVO;
import com.example.kwangs.approval.service.sendVO;
import com.example.kwangs.common.file.mapper.fileMapper;
import com.example.kwangs.common.file.service.AttachVO;
import com.example.kwangs.common.paging.SearchCriteria;
import com.example.kwangs.folder.mapper.folderMapper;
import com.example.kwangs.folder.service.fldrmbrVO;
import com.example.kwangs.folder.service.folderVO;
import com.example.kwangs.user.mapper.userMapper;
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
	@Autowired
	private userMapper uMapper;
	
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

		action_log_sanc sanc = new action_log_sanc();
		sanc.setUserid(approval.getDrafterid());
		sanc.setAction_code("S0");
		sanc.setData2(approval.getAppr_seq());
		sanc.setData1(approval.getTitle());
		log.info(sanc.getUserid()+" / "+sanc.getAction_code()+" / "+sanc.getData1()+" / "+sanc.getData2());
		mapper.ActionLogSancAdd(sanc);
		log.info("approval service appr_seq? "+approval.getAppr_seq());

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
	//발송현황
	@Override
	public List<approvalVO>SndngSttusDocList(SearchCriteria scri){
		return mapper.SndngSttusDocList(scri);
	}
	//접수대기
	@Override
	public List<approvalVO>RceptWaitDocList(SearchCriteria scri){
		return mapper.RceptWaitDocList(scri);
	}
	//수신반송
	@Override
	public List<approvalVO>ReceptReturnDocList(SearchCriteria scri){
		return mapper.ReceptReturnDocList(scri);
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
		//mapper.RetireApprStatus(appr_seq);
		approvalVO Info = mapper.apprInfo(appr_seq);
		log.info("Doc ApprId? "+Info.getAppr_seq());
		log.info("draftsrctype Vlaue? "+Info.getDraftsrctype());
		String type = Info.getDraftsrctype();
		if(type == null) {
			log.info("일반문서 회수");
			mapper.RetireApprStatus(appr_seq);
		}else if(type.equals("1")){
			log.info("접수문서 회수");
			log.info("해당 접수문서의 SendId? "+Info.getSendid());
			mapper.RetireApprStatus(appr_seq); //문서상태
			sendVO send = mapper.SndngSttusApprInfo(Info.getSendid());
			String setRecdoc = send.getRecdocstatus();
			setRecdoc = "20480";
			Map<String,Object> res = new HashMap<>();
			res.put("sendid", send.getSendid());
			res.put("recdocstatus", setRecdoc);
			mapper.SendDocRecdocStatus(res);//접수테이블에서의 상태
			log.info("접수문서 회수 후 상태값? "+send.getRecdocstatus());
		}else {
			log.info("??????");
			return;
		}
	}
	
	//재기안 시 문서 상태값 변경
	@Override
	public void Resubmission(approvalVO approval) throws IOException {
		//기록물철 임시저장
		saveDatTemp.saveDataToDatFile(approval.getFolderid(), approval.getFoldername(), approval.getBizunitcd(),approval.getDrafterid());
		if(!approval.getDraftsrctype().equals("1")) {
			if(approval.getDocattr().equals("1")) {
				log.info("@>@>@>>> 1"+approval.getAppr_seq()+" --SUCCESS");
				approval.setPoststatus("1");	
			}else 
				if(approval.getDocattr().equals("2")) {
					log.info("@>@>@>>> 2"+approval.getAppr_seq()+" ---SUCCESS");
					approval.setOrgdraftdeptid("");
					approval.setSendername("");
					approval.setPoststatus("");
			}	
		}else {
			log.info("재기안... 접수문서ok");
			log.info("draftsrctype? "+approval.getDraftsrctype());
			sendVO send = mapper.SndngSttusApprInfo(approval.getSendid());
			String setRecdoc = send.getRecdocstatus();
			setRecdoc = "4";
			Map<String,Object> res = new HashMap<>();
			res.put("sendid", send.getSendid());
			res.put("recdocstatus", setRecdoc);
			mapper.SendDocRecdocStatus(res);
		}	
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
	//결재함[수신반송] 문서 총 갯수
	@Override
	public int TotalRceptReturnDocCnt(SearchCriteria scri) {
		return mapper.TotalRceptReturnDocCnt(scri);
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
	public void UpdDocPostStatus(Map<String ,Object> drafterRes) {
		mapper.UpdDocPostStatus(drafterRes);
	}
	//상세보기에서의 접수를 해야할 문서인지 체크
	@Override
	public sendVO getSendInfo(Map<String,Object> send) {
		return mapper.getSendInfo(send);
	}
	//상세보기에서의 접수문서인지 체크
	@Override
	public sendVO getReceptInfo(Map<String,Object> send) {
		return mapper.getReceptInfo(send);
	}
	//접수대기 -> 접수 시 기존 apprid 가져오는 부분
	@Override
	public sendVO getSendOrgApprId(String appr_seq) {
		return mapper.getSendOrgApprId(appr_seq);
	}
	//발송 시 fldrmbr테이블에 fldrmbrid는 각 부서에 체결된 sendid로 기입
	@Override
	public sendVO getSendId(Map<String,Object> res) {
		return mapper.getSendId(res);
	}
	//접수대기 문서 접수하기
	public void RceptDocSang(approvalVO ap) throws IOException {
		String abbr = ap.getDocregno();
		sendVO OrgRegisterDate = mapper.SndngSttusApprInfo(ap.getSendid());
		ap.setDocregno(abbr+"-@N");
		ap.setDraftsrctype("1");
		ap.setRegdate(OrgRegisterDate.getRegistdate());
		log.info(".....>>> "+ap.getDraftsrctype());
		mapper.RceptDocSang(ap);
		
		String sendid = ap.getSendid();
		log.info("SERVICE-- SEND TABLE SENDID >>>>> "+sendid);
		mapper.updSendData(sendid);
		
		folderVO ApprFldrId_6050 = fMapper.ApprFldrmbr_6050(ap.getDrafterid()); //접수한 문서
		//6050[접수한 문서]
		fldrmbrVO fm = new fldrmbrVO();
		fm.setFldrid(ApprFldrId_6050.getFldrid());
		fm.setFldrmbrid(ap.getAppr_seq());
		fm.setRegisterid(ap.getDrafterid());
		fMapper.ApprFldrmbrInsert(fm);
		
		saveDatTemp.saveDataToDatFile(ap.getFolderid(), ap.getFoldername(), ap.getBizunitcd(),ap.getDrafterid());
	}
	/*
	 * 문서삭제
	 */
	
	public boolean DeleteDoc(String appr_seq) {
		return mapper.DeleteDoc(appr_seq) == 1;
	}
	@Override
	public sendVO SndngSttusApprInfo(String sendid) {
		return mapper.SndngSttusApprInfo(sendid);
	}
	@Override
	public void SendDocRecdocStatus(Map<String,Object> res) {
		mapper.SendDocRecdocStatus(res);
	}
	//반송
	@Override
	public void RecptDocReturn(String appr_seq,String deptid, String userid, String opinioncontent, Date regdate, String name) throws ParseException {
		Map<String,Object> res= new HashMap<>();
		res.put("appr_seq", appr_seq);
		res.put("receiverid", deptid);
		log.info("반송 시키는 부서 ID? "+deptid);
		sendVO sd = mapper.getSendInfo(res);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		java.util.Date sdfDate = sdf.parse("1970/01/01 09:00:00");
		java.sql.Date f_date = new java.sql.Date(sdfDate.getTime());
		
		sendVO sndng = new sendVO();
		sndng.setAppr_seq(appr_seq);
		sndng.setSendtype(sd.getSendtype());
		sndng.setReceiverid(sd.getReceiverid());
		sndng.setReceivername(sd.getReceivername());
		sndng.setSenderid(sd.getSenderid());
		sndng.setSendername(sd.getSendername());
		sndng.setSenddeptid(sd.getSenddeptid());
		sndng.setReceiptappr_seq(sd.getReceiptappr_seq());
		sndng.setRegisterid(sd.getRegisterid());
		sndng.setParsendid(sd.getParsendid());
		sndng.setRecdocstatus("16");
		sndng.setRejectdate(regdate);//
		sndng.setRejecterid(userid);
		sndng.setRejectername(name);
		sndng.setApprovaldate("1970/01/01 090000");
		sndng.setRegistdate("1970/01/01 090000");
		sndng.setDocrecvdate(f_date);
		if(opinioncontent != null) {
			sndng.setReturncommentflag("Y");
		}else {
			sndng.setReturncommentflag("N");
		}
		boolean result = mapper.RecptDocReturn(sndng);
		log.info("SERVICE: 반송데이터 INSERT SUCCESS");
		
		opinionVO op = new opinionVO();
		op.setOpinionid(sndng.getSendid());
		op.setOpiniontype("S1");
		op.setOpinioncontent(opinioncontent);
		op.setRegisterid(userid);
		op.setCredate(regdate);
		mapper.DocOpinionAdd(op);
		log.info("Opinion Value id? "+op.getOpinionid());
		log.info("Opinion Value content? "+op.getOpinioncontent());
		log.info("Opinion Value registerid? "+op.getRegisterid());
		log.info("Opinion Value date? "+op.getCredate());
			Map<String, Object>sendData_5010 = new HashMap<>();
		if(result) {
			List<userVO> DeptUseInfo = uMapper.DeptUseInfo(deptid);
			List<userVO> DeptUseInfo2 = uMapper.DeptUseInfo(sd.getSenddeptid());
			
			for(userVO use : DeptUseInfo) {
				folderVO fldrmbr5010 = fMapper.ApprFldrmbr_5010(use.getId());
				log.info("수신 부서에 FldrId: "+fldrmbr5010.getFldrid());
				sendData_5010.put("fldrmbrid", sd.getSendid());
				sendData_5010.put("registerid", use.getId());
				sendData_5010.put("fldrid", fldrmbr5010.getFldrid());
				fMapper.deleteApprFldrmbr_5010(sendData_5010);
			}	
			log.info("수신부서 접수대기 데이터 삭제");
			for(userVO use2 : DeptUseInfo2) {
				log.info("반송처리 되어, 기안부서 소속인원들의 수신반송 폴더테이블에 등록");
				folderVO fldrmbr5020 = fMapper.ApprFldrmbr_5020(use2.getId());
				log.info("발송부서? "+use2.getDeptid());
				log.info("발송부서 소속인원? "+use2.getId());
				fldrmbrVO fm = new fldrmbrVO();
				fm.setFldrid(fldrmbr5020.getFldrid());
				fm.setFldrmbrid(sndng.getSendid());
				fm.setRegisterid(use2.getId());
				fMapper.ApprFldrmbrInsert(fm);
			}
		}
		approvalVO Info = mapper.apprInfo(appr_seq);
		//반송 시 이력테이블에 발송값 추가.
		action_log_sanc sanc = new action_log_sanc();
		sanc.setUserid(userid);
		sanc.setAction_code("S1");
		sanc.setData2(appr_seq);
		sanc.setData1(Info.getTitle());
		log.info("문서 반송 후 action_log_sanc 테이블 INSERT");
		log.info(sanc.getUserid()+" / "+sanc.getAction_code()+" / "+sanc.getData1()+" / "+sanc.getData2());
		mapper.ActionLogSancAdd(sanc);	
	}
	//의견목록
	@Override
	public List<opinionVO>DocOpinionList(String appr_seq){
		return mapper.DocOpinionList(appr_seq);
	}
	//의견추가
	@Override
	public void DocOpinionAdd(opinionVO op) {
		mapper.DocOpinionAdd(op);
	}
	//의견삭제
	@Override
	public void DocOpinionDel(Map<String,Object> res) {
		log.info("Service Delete Data? "+res);
		mapper.DocOpinionDel(res);
	}
}
