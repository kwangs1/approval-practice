package com.example.kwangs.receipts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kwangs.receipts.domain.receiptsVO;
import com.example.kwangs.receipts.service.receiptsService;

@Controller
@RequestMapping("/receipts")
public class receiptsController {
	@Autowired
	private receiptsService service;
	
	@GetMapping("/write")
	public void write() {
		
	}
	@PostMapping("/write")
	public void write(receiptsVO vo) {
		service.write(vo);
	}
}
