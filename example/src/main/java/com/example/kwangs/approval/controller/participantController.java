package com.example.kwangs.approval.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.approval.domain.participantVO;
import com.example.kwangs.approval.service.participantService;

@Controller
@RequestMapping("/participant")
public class participantController {
	private Logger log = LoggerFactory.getLogger(participantController.class.getName());
	@Autowired
	private participantService service;
	
	@ResponseBody
	@PostMapping("/participantCheck")
	public void participantCheck(participantVO participant,Model model) {
		log.info("partichpantCheck controller...in");
		log.info("apprseq{}.."+participant.getAppr_seq());
		log.info("participantseq{}.."+participant.getParticipant_seq());
		log.info("name{}.."+participant.getName());
		log.info("status{}.."+participant.getApprovalstatus());
		log.info("type{}.."+participant.getApprovaltype());

		//체크 문서 결재선 정보 가져오기
		participantVO participantInfo = service.getParticipantInfo(participant.getAppr_seq());
		model.addAttribute("participantInfo",participantInfo);
		//이후 업데이트
		service.participantCheck(participant);
		
	}
}
