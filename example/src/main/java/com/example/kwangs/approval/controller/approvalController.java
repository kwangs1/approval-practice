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
	
	@GetMapping("/write")
	public void write() {}

	@ResponseBody
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody List<participantVO> participant) {
		log.info("Received data {} "+participant);
		service.write(participant);
	    return ResponseEntity.ok("Success");
	}
	
	@GetMapping("/apprView")
	public void apprView() {}
	
	@ResponseBody
	@PostMapping("/apprView")
	public void apprView(approvalVO approval) {
		service.apprView(approval);
	}
	
	
	@GetMapping("/apprWaitList")
	public void apprWaitList(Model model, String id, participantVO participant) {
		model.addAttribute("list",service.apprWaitList(id));
		
	}
	
	@GetMapping("/apprInfo")
	public approvalVO apprInfo(String appr_seq, Model model) {
		approvalVO Info = service.apprInfo(appr_seq);
		model.addAttribute("info",Info);
		
		return Info;
	}
	
	

}
