package com.example.kwangs.receipts.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@PostMapping("/write")
	public String write(receiptsVO rVO, paticipantVO pVO) {
	    service.write(rVO, pVO);
	    return "redirect:/receipts/write";
	}


}
