package com.example.kwangs.approval.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public void participantCheck(@RequestBody List<participantVO> participant) {	
		List<participantVO> participantInfo = new ArrayList<>();
		for(participantVO participants : participant) {
	        log.info("participant check controller...in");
	        log.info("apprseq : {}", participants.getAppr_seq());
	        log.info("participantseq : {}", participants.getParticipant_seq());
	        log.info("id : {}", participants.getId());
	        log.info("status : {}", participants.getApprovalstatus());
	        log.info("type : {}", participants.getApprovaltype());
	        

			List<participantVO> info = service.getParticipantInfo(participants.getAppr_seq());
			participantInfo.addAll(info);
	    }
		
		service.participantCheck(participant);
	}
}
