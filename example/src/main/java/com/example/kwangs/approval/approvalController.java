package com.example.kwangs.approval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.approval.service.approvalVO;
import com.example.kwangs.common.PageMaker;
import com.example.kwangs.common.SearchCriteria;
import com.example.kwangs.folder.service.folderVO;
import com.example.kwangs.folder.service.impl.folderServiceimpl;
import com.example.kwangs.participant.service.participantService;
import com.example.kwangs.participant.service.participantVO;
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
	private folderServiceimpl folderService;
	
	
	//문서작성
	@GetMapping("/apprWrite")
	public void apprWrite(userVO userVO,HttpServletRequest req, Model model) {
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
	public void apprWrite(approvalVO approval) {
		service.apprWrite(approval);
	}

	//결재함
	@GetMapping("/ApprFrame")
	public String apprFrame(Model model, HttpServletRequest request) {
		String ownerid = (String) request.getSession().getAttribute("userId");
		
		//결재함 사이드 메뉴
		List<folderVO> ApprfldrSidebar = folderService.ApprfldrSidebar(ownerid);
		model.addAttribute("ApprfldrSidebar",ApprfldrSidebar);
		
		return "/approval/ApprFrame";		
	}
	
	//결재대기
	@GetMapping("/apprWaitList")
	public String apprWaitList(Model model,HttpServletRequest req) {
		
		if(req.getSession(false).getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		String id =(String)req.getSession().getAttribute("userId");
		List<approvalVO> wait = service.apprWaitList(id);
		model.addAttribute("list",wait);
		
		for(approvalVO ap : wait) {	
			List<participantVO> participantInfo = serviceP.ApprWaitFLowInfo(ap.getAppr_seq());
			model.addAttribute("participantInfo",participantInfo);	
		}
		
		return "/approval/apprWaitList";
	}
	
	//결재진행
	@GetMapping("/SanctnProgrsList")
	public String SanctnProgrsList(Model model,HttpServletRequest req) {
		
		if(req.getSession(false).getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		String id =(String)req.getSession().getAttribute("userId");
		List<approvalVO> progrs = service.SanctnProgrsList(id);
		model.addAttribute("list",progrs);
		
		for(approvalVO ap : progrs) {	
			List<participantVO> participantInfo = serviceP.ApprProgrsFLowInfo(ap.getAppr_seq());
			model.addAttribute("participantInfo",participantInfo);	
		}
		
		return "/approval/SanctnProgrsList";		
	}
	
	//문서함
	@GetMapping("/docFrame")
	public void docFrame(Model model, HttpServletRequest request, SearchCriteria scri, folderVO fd, approvalVO ap) {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		String drafterdeptid = (String)request.getSession().getAttribute("deptId");
		scri.setDrafterdeptid(drafterdeptid);
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
	}
	
	//문서 상세보기
	@GetMapping("/apprInfo")
	public String apprInfo(String appr_seq, Model model,participantVO pp,HttpServletRequest request) {		
		approvalVO Info = service.apprInfo(appr_seq);
		model.addAttribute("info",Info);
		
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
		return "/approval/Resubmission";
	}
	
	@ResponseBody
	@PostMapping("/Resubmission")
	public void Resubmission(approvalVO approval) {
		service.Resubmission(approval);
	}

}
