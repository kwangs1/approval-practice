package com.example.kwangs.paticipant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kwangs.paticipant.service.paticipantService;

@Controller
@RequestMapping("/paticipant")
public class paticipantController {
	@Autowired
	private paticipantService service;
	/*
	@GetMapping("/write")
	public void write() {}
	
	@PostMapping("/write")
	public void write(paticipantVO vo) {
		service.write(vo);
	}*/
}
