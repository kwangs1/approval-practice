package com.example.kwangs.receipts.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kwangs.memo.controller.memoController;
import com.example.kwangs.paticipant.domain.paticipantVO;
import com.example.kwangs.paticipant.service.paticipantService;
import com.example.kwangs.receipts.domain.receiptsVO;
import com.example.kwangs.receipts.service.receiptsService;

@Controller
@RequestMapping("/receipts")
public class receiptsController {
	private static Logger log = Logger.getLogger(receiptsController.class.getName());
	@Autowired
	private receiptsService service;
	@Autowired
	private paticipantService paticipantService;
	
	@GetMapping("/write")
	public void write() {
		
	}
	@PostMapping("/write")
	public String write(receiptsVO rVO, paticipantVO pVO) {
		//결재 데이터 먼저 insert
		service.write(rVO, pVO);
		
		return "redirect:/receipts/write";
	}
}
