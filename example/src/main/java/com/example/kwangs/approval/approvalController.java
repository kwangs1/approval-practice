package com.example.kwangs.approval;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.approval.service.approvalVO;
import com.example.kwangs.approval.service.opinionVO;
import com.example.kwangs.approval.service.sendVO;
import com.example.kwangs.common.file.fileController;
import com.example.kwangs.common.file.service.AttachVO;
import com.example.kwangs.common.file.service.fileService;
import com.example.kwangs.common.paging.PageMaker;
import com.example.kwangs.common.paging.SearchCriteria;
import com.example.kwangs.dept.service.deptService;
import com.example.kwangs.dept.service.deptVO;
import com.example.kwangs.folder.service.apprfolderVO;
import com.example.kwangs.folder.service.fldrmbrVO;
import com.example.kwangs.folder.service.folderService;
import com.example.kwangs.folder.service.folderVO;
import com.example.kwangs.participant.service.participantService;
import com.example.kwangs.participant.service.participantVO;
import com.example.kwangs.user.service.userService;
import com.example.kwangs.user.service.userVO;

@Controller
@RequestMapping("/approval")
public class approvalController {
	private Logger log = LoggerFactory.getLogger(approvalController.class.getName());
	@Autowired
	private approvalService service;
	@Autowired
	private participantService serviceP;
	@Autowired
	private folderService folderService;
	@Autowired
	private fileService fileService;
	@Autowired
	private deptService deptService;
	@Autowired
	private userService userService;
	
	
	//첨부파일 삭제
	private void deleteFiles(List<AttachVO> attach, String appr_seq) {
		if(attach == null || attach.size() == 0) {
			log.info("Not Files..");
			return;
		}
		log.info("delete files ? "+attach);
		for(int i=0; i < attach.size(); i++) {
			AttachVO attachVO = attach.get(i);
			String id = appr_seq.substring(16);
			try {
				File folder = new File("/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+attachVO.getUploadPath()+"/"+id);
				FileUtils.cleanDirectory(folder);
				folder.delete();
				log.info("delete success");
			}catch(Exception e) {
				log.info("delete file error: "+e.getMessage());
			}
		}
	}
	
	//프로시저 카운트 처리[결재함]
	public void FolderCounts(HttpServletRequest request, folderVO fd,Model model) {
		String sabun = (String)request.getSession().getAttribute("sabun");
		
		Map<String,Object> res = new HashMap<>();
		res.put("sabun", sabun);
		res.put("applid", fd.getApplid());
		Map<String,Object> result = folderService.getFolderCounts(res);
		model.addAttribute("FolderCnt",result);
	}
	
	//프로시저 카운트 처리[문서함]
	public void DocFolderCnt(HttpServletRequest request, folderVO fd,Model model) {
		String sabun = (String)request.getSession().getAttribute("sabun");
		
		Map<String,Object> res = new HashMap<>();
		res.put("sabun", sabun);
		res.put("applid", fd.getApplid());
		res.put("fldrid", fd.getFldrid());
		Map<String,Object> result = folderService.getDocFolderCnt(res);
		model.addAttribute("DocFolderCnt",result);
	}
	//문서작성
	@GetMapping("/apprWrite")
	public void apprWrite(userVO userVO,HttpServletRequest req, Model model,approvalVO approval) {
		//부서 약어에 대한 값 가져오기 위함
		String userId = (String) req.getSession().getAttribute("userId");
		String abbreviation = userVO.getAbbreviation();
		
		Map<String,Object> res = new HashMap<>();
		res.put("id", userId);
		res.put("abbreviation", abbreviation);
		
		userVO uInfo = service.getUserDeptInfo(res);
		model.addAttribute("uInfo",uInfo);
	}
	
	@ResponseBody
	@PostMapping("/apprWrite")
	public void apprWrite(approvalVO approval,HttpServletRequest request,Model model)throws IOException {
		service.apprWrite(approval);
		List<AttachVO> attach = approval.getAttach();
		if(attach != null && !attach.isEmpty()) {		
			String appr_seq = approval.getAppr_seq().substring(16);
			String uploadFolder = fileController.getFolder();
			String newFolderPath = "/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+uploadFolder+"/"+appr_seq;
			File newFolder = new File(newFolderPath);
			
			if(!newFolder.exists()) {
				newFolder.mkdirs();
			}

			String id = (String) request.getSession().getAttribute("userId");
			String tempFolderPath = "/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+uploadFolder+"/temp/"+id;
			File tempFolder = new File(tempFolderPath);
			File[] files = tempFolder.listFiles();
			if(files != null) {
				for(File file : files) {
					file.renameTo(new File(newFolderPath + "/" + file.getName()));
				}
			}
			tempFolder.delete();
		}
	}

	//결재함
	@GetMapping("/apprFrame")
	public void apprFrame(Model model, HttpServletRequest request) {
		String id = (String) request.getSession().getAttribute("userId");
		request.getParameter(id);
		//결재함 사이드 메뉴
		List<folderVO> ApprfldrSidebar = folderService.ApprfldrSidebar(id);
		model.addAttribute("ApprfldrSidebar",ApprfldrSidebar);	
	}
	
	//결재대기
	@GetMapping("/apprWaitList")
	public String apprWaitList(Model model,HttpServletRequest request, SearchCriteria scri, folderVO fd) {	
		if(request.getSession(false).getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		String id =(String)request.getSession().getAttribute("userId");
		String drafterdeptid = (String)request.getSession().getAttribute("deptId");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		scri.setDrafterdeptid(drafterdeptid);
		scri.setId(id);
		scri.setOwnerid(fd.getOwnerid());
		scri.setFldrid(fd.getFldrid());
		scri.setFldrname(fd.getFldrname());
		scri.setApplid(fd.getApplid());
		
		scri.cookieVal(request);// 페이징 화면에 표기할 값 쿠키에 저장
		
		List<approvalVO> wait = service.apprWaitList(scri);
		model.addAttribute("list",wait);
		
		pageMaker.setTotalCount(service.totalApprCnt(scri));
		model.addAttribute("pageMaker",pageMaker);
		
		//결재함 사이드 메뉴
		List<folderVO> ApprfldrSidebar = folderService.ApprfldrSidebar(id);
		model.addAttribute("ApprfldrSidebar",ApprfldrSidebar);	
		
		for(approvalVO ap : wait) {	
			scri.setAppr_seq(ap.getAppr_seq());
			List<participantVO> participantInfo = serviceP.ApprWaitFLowInfo(scri);
			model.addAttribute("participantInfo",participantInfo);	
		}
		FolderCounts(request,fd,model);
		
		return "/approval/apprWaitList";
	}
	
	//결재진행
	@GetMapping("/SanctnProgrsList")
	public String SanctnProgrsList(Model model,HttpServletRequest request, SearchCriteria scri, folderVO fd) {
		
		if(request.getSession(false).getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		String id =(String)request.getSession().getAttribute("userId");
		String drafterdeptid = (String)request.getSession().getAttribute("deptId");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		scri.setDrafterdeptid(drafterdeptid);
		scri.setId(id);
		scri.setOwnerid(fd.getOwnerid());
		scri.setFldrid(fd.getFldrid());
		scri.setFldrname(fd.getFldrname());
		scri.setApplid(fd.getApplid());
		scri.setSignerid(id);
		
		scri.cookieVal(request);// 페이징 화면에 표기할 값 쿠키에 저장
		List<approvalVO> progrs = service.SanctnProgrsList(scri);
		model.addAttribute("list",progrs);
		
		//결재함 사이드 메뉴
		List<folderVO> ApprfldrSidebar = folderService.ApprfldrSidebar(id);
		model.addAttribute("ApprfldrSidebar",ApprfldrSidebar);	

		pageMaker.setTotalCount(service.totalApprCnt(scri));
		model.addAttribute("pageMaker",pageMaker);
		
		for(approvalVO ap : progrs) {	
			scri.setAppr_seq(ap.getAppr_seq());
			List<participantVO> participantInfo = serviceP.ApprProgrsFLowInfo(scri);
			model.addAttribute("participantInfo",participantInfo);	
		}
		FolderCounts(request,fd,model);
		
		return "/approval/SanctnProgrsList";		
	}
	//발송대기
	@GetMapping("/SndngWaitDocList")
	public String SndngWaitDocList(Model model, HttpServletRequest request,SearchCriteria scri,folderVO fd) {
		if(request.getSession(false).getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		String id =(String)request.getSession().getAttribute("userId");
		String drafterdeptid = (String)request.getSession().getAttribute("deptId");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		scri.setDrafterdeptid(drafterdeptid);
		scri.setId(id);
		scri.setOwnerid(fd.getOwnerid());
		scri.setFldrid(fd.getFldrid());
		scri.setFldrname(fd.getFldrname());
		scri.setApplid(fd.getApplid());
		scri.setSignerid(id);
			
		scri.cookieVal(request);// 페이징 화면에 표기할 값 쿠키에 저장
		List<approvalVO> list = service.SndngWaitDocList(scri);
		model.addAttribute("list",list);

		//결재함 사이드 메뉴
		List<folderVO> ApprfldrSidebar = folderService.ApprfldrSidebar(id);
		model.addAttribute("ApprfldrSidebar",ApprfldrSidebar);	

		pageMaker.setTotalCount(service.TotalSndngWaitCnt(scri));
		model.addAttribute("pageMaker",pageMaker);
			
		for(approvalVO ap : list) {	
			List<participantVO> SndngWaitflowInfo = serviceP.SndngWaitflowInfo(ap.getAppr_seq());
			model.addAttribute("SndngWaitflowInfo",SndngWaitflowInfo);
		}			
		FolderCounts(request,fd,model);
		return "/approval/SndngWaitDocList";
	}
	//발송현황
	@GetMapping("/SndngSttusDocList")
	public String SndngSttusDocList(Model model, HttpServletRequest request,SearchCriteria scri,folderVO fd) {
		if(request.getSession(false).getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		String id =(String)request.getSession().getAttribute("userId");
		String drafterdeptid = (String)request.getSession().getAttribute("deptId");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		scri.setDrafterdeptid(drafterdeptid);
		scri.setId(id);
		scri.setOwnerid(fd.getOwnerid());
		scri.setFldrid(fd.getFldrid());
		scri.setFldrname(fd.getFldrname());
		scri.setApplid(fd.getApplid());
		scri.setSignerid(id);
			
		scri.cookieVal(request);// 페이징 화면에 표기할 값 쿠키에 저장
		List<approvalVO> list = service.SndngSttusDocList(scri);
		model.addAttribute("list",list);

		//결재함 사이드 메뉴
		List<folderVO> ApprfldrSidebar = folderService.ApprfldrSidebar(id);
		model.addAttribute("ApprfldrSidebar",ApprfldrSidebar);	

		pageMaker.setTotalCount(service.TotalRceptWaitCnt(scri));
		model.addAttribute("pageMaker",pageMaker);			
		FolderCounts(request,fd,model);
		
		return "/approval/SndngSttusDocList";
	}
	//접수대기
	@GetMapping("/RceptWaitDocList")
	public String RceptWaitDocList(Model model, HttpServletRequest request,SearchCriteria scri,folderVO fd) {
		if(request.getSession(false).getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		String id =(String)request.getSession().getAttribute("userId");
		String drafterdeptid = (String)request.getSession().getAttribute("deptId");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		scri.setReceiverid(drafterdeptid);
		scri.setId(id);
		scri.setOwnerid(fd.getOwnerid());
		scri.setFldrid(fd.getFldrid());
		scri.setFldrname(fd.getFldrname());
		scri.setApplid(fd.getApplid());
		scri.setSignerid(id);
			
		scri.cookieVal(request);// 페이징 화면에 표기할 값 쿠키에 저장
		List<approvalVO> list = service.RceptWaitDocList(scri);
		model.addAttribute("list",list);

		List<fldrmbrVO> fldrmbr_ = folderService.RecDeptDocInfo(scri.getFldrid()); //접수한 문서에 대한 데이터값 조회.
		for(fldrmbrVO fldr : fldrmbr_) {
			log.info("fldrmbrid? "+fldr.getFldrmbrid());
			sendVO Info = service.SndngSttusApprInfo(fldr.getFldrmbrid()); //해당 접수대기 폴더테이블에 접수ID로 등록된 값들에 대해
			approvalVO a = service.apprInfo(Info.getReceiptappr_seq());
			if(Info.getRecdocstatus().equals("4")) {
				log.info("receiptapprid? "+Info.getReceiptappr_seq());
				log.info("apprid? "+a.getAppr_seq());
				log.info("Recdocstatus? "+Info.getRecdocstatus());
				//model.addAttribute("draftscrtype",a.getDraftsrctype());
				model.addAttribute("orgappr_seq",a.getOrgappr_seq());				
			}
		}
		//결재함 사이드 메뉴
		List<folderVO> ApprfldrSidebar = folderService.ApprfldrSidebar(id);
		model.addAttribute("ApprfldrSidebar",ApprfldrSidebar);	

		pageMaker.setTotalCount(service.TotalRceptWaitCnt(scri));
		model.addAttribute("pageMaker",pageMaker);

		FolderCounts(request,fd,model);
		return "/approval/RceptWaitDocList";
	}
	//수신반송
	@GetMapping("/ReceptReturnDocList")
	public String ReceptReturnDocList(Model model, HttpServletRequest request,SearchCriteria scri,folderVO fd) {
		if(request.getSession(false).getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		String id =(String)request.getSession().getAttribute("userId");
		String drafterdeptid = (String)request.getSession().getAttribute("deptId");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		scri.setReceiverid(drafterdeptid);
		scri.setId(id);
		scri.setOwnerid(fd.getOwnerid());
		scri.setFldrid(fd.getFldrid());
		scri.setFldrname(fd.getFldrname());
		scri.setApplid(fd.getApplid());
		scri.setSignerid(id);
			
		scri.cookieVal(request);// 페이징 화면에 표기할 값 쿠키에 저장
		List<approvalVO> list = service.ReceptReturnDocList(scri);
		model.addAttribute("list",list);

		//결재함 사이드 메뉴
		List<folderVO> ApprfldrSidebar = folderService.ApprfldrSidebar(id);
		model.addAttribute("ApprfldrSidebar",ApprfldrSidebar);	

		pageMaker.setTotalCount(service.TotalRceptReturnDocCnt(scri));
		model.addAttribute("pageMaker",pageMaker);

		FolderCounts(request,fd,model);
		
		//해당 apprid로 반송 시 찍힌 send데이터
		List<fldrmbrVO> sendList = folderService.RecDeptDocInfo(scri.getFldrid()); //접수한 문서에 대한 데이터값 조회.
		for(fldrmbrVO fldr : sendList) {
			//log.info("fldrmbrid? "+fldr.getFldrmbrid());
			sendVO Info = service.SndngSttusApprInfo(fldr.getFldrmbrid()); //해당 접수대기 폴더테이블에 접수ID로 등록된 값들에 대해
			deptVO dept = deptService.info(Info.getReceiverid());
			if(Info.getRecdocstatus().equals("16")) {
				model.addAttribute("registerid",Info.getRegisterid());				
				model.addAttribute("deptname",dept.getOrg_deptname());				
				model.addAttribute("rejectdate",Info.getRejectdate());				
			}
		}	
		return "/approval/ReceptReturnDocList";
	}
	//문서함
	@GetMapping("/docFrame")
	public void docFrame(Model model, HttpServletRequest request, SearchCriteria scri, folderVO fd, approvalVO ap) {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);

		String id =(String)request.getSession().getAttribute("userId");
		String drafterdeptid = (String)request.getSession().getAttribute("deptId");
		scri.setDrafterdeptid(drafterdeptid);
		scri.setId(id);
		scri.setOwnerid(fd.getOwnerid());
		scri.setFldrid(fd.getFldrid());
		scri.setFldrname(fd.getFldrname());
		scri.setApplid(fd.getApplid());
		
		scri.cookieVal(request);// 페이징 화면에 표기할 값 쿠키에 저장
		
		List<approvalVO> docframe = service.docFrame(scri);
		model.addAttribute("docframe",docframe);
		
		pageMaker.setTotalCount(service.totalDocCnt(scri));
		model.addAttribute("pageMaker",pageMaker);
		
		List<folderVO> docfldrSidebar = folderService.docfldrSidebar(drafterdeptid);
		model.addAttribute("docfldrSidebar",docfldrSidebar);
		model.addAttribute("fldrname",fd.getFldrname());
		model.addAttribute("applid",fd.getApplid());// procedure applid
		model.addAttribute("fldrid",fd.getFldrid());// fldrmng view data
		
		DocFolderCnt(request,fd,model);
	}
	
	//문서 상세보기
	@GetMapping("/apprInfo")
	public String apprInfo(String appr_seq, Model model,participantVO pp,HttpServletRequest request) {		
		approvalVO Info = service.apprInfo(appr_seq);
		model.addAttribute("info",Info);
		approvalVO OrgDocReceivers = service.apprInfo(Info.getOrgappr_seq());//
		model.addAttribute("OrgDocReceivers",OrgDocReceivers);//
		//일반 결재 시 상세보기에서의 결재선 정보 
		String userId = (String) request.getSession().getAttribute("userId");
		Map<String,Object> res = new HashMap<>();
		res.put("appr_seq", pp.getAppr_seq());
		res.put("participant_seq", pp.getParticipant_seq());
		res.put("approvaltype", pp.getApprovaltype());
		res.put("approvalstatus", pp.getApprovalstatus());
		res.put("signerid", userId);
		
		participantVO pInfo = serviceP.pInfo(res);
		model.addAttribute("pInfo",pInfo);
		
		List<participantVO> pList = serviceP.getRe_pInfo(appr_seq);
		model.addAttribute("pList",pList);
		//접수문서 일 시 기안부서 결재선 보이게
		List<participantVO> DraftflowList = serviceP.getRcept_pInfo(appr_seq);
		model.addAttribute("DraftflowList",DraftflowList);
		
		//상세보기에서의 접수를 해야할 문서인지 체크
		String deptid = (String) request.getSession().getAttribute("deptId");
		Map<String,Object> send = new HashMap<>();
		send.put("appr_seq", appr_seq);
		send.put("receiverid", deptid);
		
		sendVO SendInfo = service.getSendInfo(send);
		model.addAttribute("SendInfo",SendInfo);
		//접수문서에 첨부파일이 존재하는경우[기안부서에서 생성된 apprid를 가져오기 위함]
		model.addAttribute("ReceptInfo",service.getReceptInfo(send));
		
		List<opinionVO> DocOpinion = service.DocOpinionList(appr_seq);
		for(int i=0; i<DocOpinion.size(); i++) {
			log.info(DocOpinion.get(i).getParticipant_seq());
			log.info(DocOpinion.get(i).getOpiniontype());
			if(DocOpinion.size() >= 1) {
				model.addAttribute("OpinionCheck",true);
			}
		}
		return "/approval/apprInfo";
	}
	
	//재기안
	@GetMapping("/Resubmission")
	public String Resubmission(String appr_seq, Model model, HttpServletRequest request) {
		approvalVO Info = service.apprInfo(appr_seq);
		model.addAttribute("info",Info);
		
		//결재선 정보 	
		List<participantVO> pInfo = serviceP.getRe_pInfo(appr_seq);
		model.addAttribute("pInfo",pInfo);
		//jsp ForEach해서 데이터 불러올라꼬.,.
		List<AttachVO> attach = fileService.AttachModifyForm(appr_seq);
		model.addAttribute("attach",attach);
		
		List<participantVO> pList = serviceP.getRe_pInfo(appr_seq);
		model.addAttribute("pList",pList);
		//접수문서 일 시 기안부서 결재선 보이게
		List<participantVO> DraftflowList = serviceP.getRcept_pInfo(appr_seq);
		model.addAttribute("DraftflowList",DraftflowList);
		
		return "/approval/Resubmission";
	}
	
	@ResponseBody
	@PostMapping("/Resubmission")
	public void Resubmission(approvalVO approval)throws IOException {
		service.Resubmission(approval);
	}
	
	//문서발송
	@ResponseBody
	@PostMapping("DocSend")
	public ResponseEntity<String> DocSndng(String appr_seq,String stampname, HttpServletRequest request){
		approvalVO Info = service.apprInfo(appr_seq);
		String id = (String) request.getSession().getAttribute("userId");
		//String name = (String) request.getSession().getAttribute("userName");
		
		//발송 시 수신처에 대한 값 send테이블 insert
		if(Info.getDocattr().equals("1") && Info.getPoststatus().equals("1") && Info.getReceivers() != null) {

			//발송 시 발송부서[기안부서]의 발송현황에서 접수부서가 최종결재완료전까지의 현황을 보기위해
			List<deptVO> SndngSttus = deptService.UserSosck(Info.getDrafterdeptid());			
			for(int i=0; i<SndngSttus.size(); i++) {
				log.info("발송 후 기안부서의 발송현황 폴더테이블에 INSERT");
				deptVO dp = SndngSttus.get(i);
				fldrmbrVO ss = new fldrmbrVO();
	
				List<folderVO> getFolderInfo_4050 = folderService.ApprFldrmbr_4050(dp.getUsers().get(i).getId());
				for(int j=0; j<getFolderInfo_4050.size(); j++) {
					folderVO f = getFolderInfo_4050.get(j);
					ss.setFldrid(f.getFldrid());
					ss.setFldrmbrid(Info.getAppr_seq());
					ss.setRegisterid(dp.getUsers().get(i).getId());
					
					folderService.ApprFldrmbrInsert(ss);
				}
				log.info("폴더ID? "+ss.getFldrid());
				log.info("문서ID? "+ss.getFldrmbrid());
				log.info("유저ID? "+dp.getUsers().get(i).getId());	
			}
			sendVO send = new sendVO();
			send.setAppr_seq(Info.getAppr_seq());
			send.setSenderid(id);
			send.setSendername(Info.getSendername());
			send.setRegisterid(id);
			send.setSenddeptid(Info.getDrafterdeptid());
			send.setRecdocstatus("1");
			service.DocSend(send);
			
			String[] receiveDept = Info.getReceivers().split(",");
			for(String sender : receiveDept) {
				log.info("해당 문서 수신부서 "+sender);
				//수신처에 대한 부서정보값 가져오기
				List<deptVO> SndngDeptInfo = deptService.SndngDeptInfo(sender);
				for(deptVO dp : SndngDeptInfo) {
					sendVO Receive = new sendVO();
					Receive.setAppr_seq(Info.getAppr_seq());
					Receive.setReceiverid(dp.getDeptid());
					Receive.setReceivername(dp.getSendername());
					Receive.setSenderid(id);
					Receive.setSendername(Info.getSendername());
					Receive.setSenddeptid(Info.getDrafterdeptid());
					Receive.setReceiptappr_seq(Info.getAppr_seq());
					Receive.setRegisterid(id);
					Receive.setParsendid(send.getSendid());
					if(dp.getDeptid() != null) {
						Receive.setRecdocstatus("2");
					}else {
						Receive.setRecdocstatus("64");
					}
					Receive.setSendtype("2");
					service.ReceiveDeptIn(Receive);
					//발송 후 수신부서들에 대해 FLDRMBR 테이블에 접수대기폴더 값을 넣기위해 && 부서ID에 대한 유저들 리스트
					List<userVO> DeptUseInfo = userService.DeptUseInfo(dp.getDeptid());
					Map<String,Object>res = new HashMap<>();
					res.put("appr_seq", Info.getAppr_seq());
					res.put("receiverid", dp.getDeptid());
					sendVO getSendId = service.getSendId(res);
					
					for(userVO use : DeptUseInfo) {
						log.info("수신 부서 부서ID: "+use.getDeptid());
						log.info("수신 부서에 속한 인원들: "+use.getId());
						log.info("수신 부서의 SENDID: "+getSendId.getSendid());
						folderVO fldrmbr5010 = folderService.ApprFldrmbr_5010(use.getId());
						fldrmbrVO fm = new fldrmbrVO();
						fm.setFldrid(fldrmbr5010.getFldrid());
						fm.setFldrmbrid(getSendId.getSendid());
						fm.setRegisterid(use.getId());
						folderService.ApprFldrmbrInsert(fm);
					}
				}
			}
			log.info("stampname? "+stampname);
			String DrafterSetSendId = Info.getSendid();			
			DrafterSetSendId = "00000000000000000000";
			Info.setSendid(DrafterSetSendId);
			Map<String ,Object> drafterRes = new HashMap<>();
			drafterRes.put("appr_seq", appr_seq);
			drafterRes.put("stampname", stampname);
			drafterRes.put("sendid", DrafterSetSendId);
			service.UpdDocPostStatus(drafterRes);
			log.info("발송 후 기안부서의 approval senid? "+Info.getSendid());
			//해당 문서에 대한 fldrmbr테이블에서 발송대기 폴더값 삭제
			folderVO ApprFldrId = folderService.ApprFldrmbr_4030(Info.getDrafterid());
			Map<String,Object> sendData_4030 = new HashMap<>();
			sendData_4030.put("fldrmbrid", Info.getAppr_seq());
			sendData_4030.put("registerid", Info.getDrafterid());
			sendData_4030.put("fldrid", ApprFldrId.getFldrid());
			folderService.deleteApprFldrmbr_4030(sendData_4030);
		}
		return ResponseEntity.ok("Success Document Sndng!");
	}
	
	//접수하기
	@GetMapping("/RceptDocForm")
	public String RceptDocForm(Model model, String appr_seq, HttpServletRequest request, userVO user) {
		approvalVO Info = service.apprInfo(appr_seq);
		model.addAttribute("info",Info);
		
		List<participantVO> flowList = serviceP.getRe_pInfo(appr_seq);
		model.addAttribute("flowList",flowList);
		
		//접수 시 필요한 문서값 확인
		String deptid = (String)request.getSession().getAttribute("deptId");
		Map<String,Object> send = new HashMap<>();
		send.put("appr_seq", appr_seq);
		send.put("receiverid", deptid);
		
		sendVO sendInfo = service.getSendInfo(send);
		model.addAttribute("sendInfo",sendInfo);
		log.info("send table data.. sendid: "+sendInfo.getSendid());
		
		String id = (String) request.getSession().getAttribute("userId");
		String abbreviation = user.getAbbreviation();
		
		//사용자의 부서에 대한 약어값
		Map<String,Object> res = new HashMap<>();
		res.put("id", id);
		res.put("abbreviation", abbreviation);
		
		userVO Info_u = service.getUserDeptInfo(res);
		model.addAttribute("res",Info_u);
		
		return "/approval/RceptDocForm";
	}
	
	@ResponseBody
	@PostMapping("/RceptDocSang")
	public ResponseEntity<String> RceptDocSang(approvalVO ap,HttpServletRequest request) throws IOException{
		service.RceptDocSang(ap);
		return ResponseEntity.ok("RceptDocSang Success");
	}
	
	//문서 삭제
	@ResponseBody
	@PostMapping("/DeleteDoc")
	public ResponseEntity<String> DeleteDoc(@RequestBody List<approvalVO> list, HttpServletRequest request){
		for(approvalVO ap: list) {
			log.info("Delete Document.. "+ap.getAppr_seq());
			List<AttachVO> attachList = fileService.getAttachList(ap.getAppr_seq());
			
			if(ap.getDraftsrctype().equals("1")) {
				log.info("접수문서 삭제 처리...");
				sendVO send = service.SndngSttusApprInfo(ap.getSendid());
				String setRecdoc = send.getRecdocstatus();
				setRecdoc = "2";
				Map<String,Object> res = new HashMap<>();
				res.put("sendid", send.getSendid());
				res.put("recdocstatus", setRecdoc);
				log.info("삭제 처리 된 접수문서 접수아이디? "+send.getSendid());
				service.SendDocRecdocStatus(res);
				if(service.DeleteDoc(ap.getAppr_seq())) {
					serviceP.deleteFlowInfo(ap.getAppr_seq());
					folderService.deleteDocFldrmbr(ap.getAppr_seq());	
				}					
			}else {
				log.info("기안문서 삭제 처리...");
				deleteFiles(attachList,ap.getAppr_seq());
				serviceP.deleteFlowInfo(ap.getAppr_seq());
				folderService.deleteDocFldrmbr(ap.getAppr_seq());
				fileService.deleteDocAttach(ap.getAppr_seq());					
			}
	
		}
		return ResponseEntity.ok("Sucess Delete Document..");
	}

	//문서함 [기록물철 -함관리]
	@GetMapping("/DocFldrMng")
	public String DocFldrMng(String fldrid, Model model, HttpServletRequest request) {
		String deptid = (String)request.getSession().getAttribute("deptId");
		Map<String,Object> res = new HashMap<>();
		res.put("fldrid", fldrid);
		res.put("procdeptid", deptid);
		apprfolderVO Info = folderService.ApprFldrInfo(res);
		model.addAttribute("Info",Info);
		
		List<folderVO> docfldrSidebar = folderService.docfldrSidebar(deptid);
		model.addAttribute("docfldrSidebar",docfldrSidebar);
		
		return "/approval/DocFldrMng";
	}
	
	//문서함 [기록물철-함관리- 문서이관페이지]
	@GetMapping("/TransferFldrMng")
	public String TransferFldrMng(String fldrid, Model model, HttpServletRequest request) {
		String deptid = (String)request.getSession().getAttribute("deptId");
		Map<String,Object>res = new HashMap<>();
		res.put("fldrid", fldrid);
		res.put("procdeptid", deptid);
		apprfolderVO Info = folderService.ApprFldrInfo(res);
		model.addAttribute("Info",Info);
		
		List<folderVO> docfldrSidebar = folderService.docfldrSidebar(deptid);
		model.addAttribute("docfldrSidebar",docfldrSidebar);
		
		deptVO procDeptName = deptService.getDeptName(res);
		model.addAttribute("procDeptName",procDeptName);
		
		return "/approval/TransferFldrMng";
	}
	
	//접수대기 반송
	@ResponseBody
	@PostMapping("/RecptDocReturn")
	public void RecptDocReturn(HttpServletRequest request, @RequestBody approvalVO RequestApproval) throws ParseException{
		String deptid = (String)request.getSession().getAttribute("deptId");
		String userid = (String)request.getSession().getAttribute("userId");
		String name = (String)request.getSession().getAttribute("userName");
		String appr_seq = RequestApproval.getAppr_seq();
		String opinioncontent = RequestApproval.getOpinioncontent();
		Date regdate = RequestApproval.getCredate();
		service.RecptDocReturn(appr_seq,deptid,userid,opinioncontent,regdate,name);
	}
	
	//문서 의견달기 뷰
		@GetMapping("/DocOpinionForm")
		public String DocOpinionForm(Model model) {
			return "/approval/DocOpinionForm";
		}
		//의견리스트
		@GetMapping("/DocOpinionListForm")
		public String DocOpinionListForm(Model model,String appr_seq) {
			return "/approval/DocOpinionListForm";
		}
		@ResponseBody
		@GetMapping("/DocOpinionList")
		public ResponseEntity<List<opinionVO>>DocOpinionList(String appr_seq){
			return new ResponseEntity<>(service.DocOpinionList(appr_seq), HttpStatus.OK);
		}
		//기안자 제외 중간결재들이 등록하는 경우.
		@ResponseBody
		@PostMapping("/FlowOpinionAdd")
		public ResponseEntity<String>FlowOpinionAdd(@RequestBody opinionVO OpinionRequest,HttpServletRequest request){
			String userid = (String)request.getSession().getAttribute("userId");
			opinionVO op = new opinionVO();
			op.setOpinionid(OpinionRequest.getParticipant_seq());
			op.setOpiniontype("P1");
			op.setOpinioncontent(OpinionRequest.getOpinioncontent());
			op.setRegisterid(userid);
			op.setCredate(OpinionRequest.getCredate());
			
			log.info("중간결재자 의견기입 "+OpinionRequest.getOpinioncontent());
			service.DocOpinionAdd(op);
			return ResponseEntity.ok("SUCCESS");
		}
		//의견 삭제(기안자 외)
		@ResponseBody
		@PostMapping("/DocOpinionDel")
		public ResponseEntity<String>DocOpinionDel(@RequestBody opinionVO OpinionRequest){
			log.info("delete opinion id value? "+OpinionRequest.getParticipant_seq());
			log.info("delete opinion content value? "+OpinionRequest.getOpinioncontent());
			log.info("delete opinion type value? "+OpinionRequest.getOpiniontype());
			Map<String,Object> res = new HashMap<>();
			res.put("opinionid", OpinionRequest.getParticipant_seq());
			res.put("opinioncontent", OpinionRequest.getOpinioncontent());
			res.put("opiniontype", OpinionRequest.getOpiniontype());
			service.DocOpinionDel(res);
			return ResponseEntity.ok("Delete Opinion");
		}
}
