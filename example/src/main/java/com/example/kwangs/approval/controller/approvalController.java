package com.example.kwangs.approval.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.approval.domain.approvalVO;
import com.example.kwangs.approval.domain.participantVO;
import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.approval.service.participantService;

@Controller
@RequestMapping("/approval")
public class approvalController {
	private Logger log = LoggerFactory.getLogger(approvalController.class.getName());
	@Autowired
	private approvalService service;
	@Autowired
	private participantService serviceP;
	
	//문서 기안 시 결재선 지정
	@GetMapping("/ParticipantWrite")
	public void ParticipantWrite() {}

	@ResponseBody
	@PostMapping("/ParticipantWrite")
	public ResponseEntity<String> ParticipantWrite(@RequestBody List<participantVO> participant) {
		log.info("Received data {} "+participant);
		service.ParticipantWrite(participant);
	    return ResponseEntity.ok("Success");
	}
	
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
	public void apprWaitList(Model model, String id, approvalVO approval) {
		model.addAttribute("list",service.apprWaitList(id));
		
		//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
		List<participantVO> participantInfo = serviceP.getParticipantInfo(approval.getAppr_seq());
		model.addAttribute("participantInfo",participantInfo);
	}
	
	//문서 상세보기
	@GetMapping("/apprInfo")
	public approvalVO apprInfo(String appr_seq, Model model) {
		approvalVO Info = service.apprInfo(appr_seq);
		model.addAttribute("info",Info);
		
		return Info;
	}

}
