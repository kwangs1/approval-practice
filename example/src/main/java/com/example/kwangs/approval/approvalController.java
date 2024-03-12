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
import com.example.kwangs.participant.service.participantService;
import com.example.kwangs.participant.service.participantVO;

@Controller
@RequestMapping("/approval")
public class approvalController {
	private Logger log = LoggerFactory.getLogger(approvalController.class.getName());
	@Autowired
	private approvalService service;
	@Autowired
	private participantService serviceP;
	
	
	//문서작성
	@GetMapping("/apprWrite")
	public void apprWrite() {}
	
	@ResponseBody
	@PostMapping("/apprWrite")
	public void apprWrite(approvalVO approval) {
		service.apprWrite(approval);
	}
	
	//결재대기
	@GetMapping("/apprWaitList")
	public String apprWaitList(Model model, String id, approvalVO approval,HttpServletRequest req) {
		
		if(req.getSession(false).getAttribute("user") == null) {
			return "redirect:/";
		}
		model.addAttribute("list",service.apprWaitList(id));
		
		//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
		List<participantVO> participantInfo = serviceP.getParticipantInfo(approval.getAppr_seq());
		model.addAttribute("participantInfo",participantInfo);
		
		return "/approval/apprWaitList";
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
		res.put("id", userId);
		
		participantVO pInfo = serviceP.pInfo(res);
		model.addAttribute("pInfo",pInfo);
		
		return "/approval/apprInfo";
	}

}
