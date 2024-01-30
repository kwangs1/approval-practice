package com.example.kwangs.approval.controller;

import java.util.List;
import java.util.logging.Logger;

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
import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.participant.domain.participantVO;

@Controller
@RequestMapping("/approval")
public class approvalController {
	private static Logger log = Logger.getLogger(approvalController.class.getName());
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
	public void apprWaitList(Model model, String id) {
		model.addAttribute("list",service.apprWaitList(id));
	}
	
}
