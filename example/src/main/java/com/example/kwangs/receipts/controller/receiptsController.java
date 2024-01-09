package com.example.kwangs.receipts.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.paticipant.domain.paticipantVO;
import com.example.kwangs.receipts.domain.receiptsVO;
import com.example.kwangs.receipts.service.receiptsService;

@Controller
@RequestMapping("/receipts")
public class receiptsController {
	private static Logger log = Logger.getLogger(receiptsController.class.getName());
	@Autowired
	private receiptsService service;
	
	@GetMapping("/write")
	public void write() {}

	@ResponseBody
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody List<paticipantVO> paticipant) {
		log.info("Received data {} "+paticipant);
		service.write(paticipant);
	    return ResponseEntity.ok("Success");
	}
	
	@GetMapping("/apprView")
	public void apprView() {}
	
	@PostMapping("/apprView")
	public void apprView(receiptsVO receipts) {
		service.apprView(receipts);
	}
	
}
