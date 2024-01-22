package com.example.kwangs.participant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kwangs.participant.service.participantService;

@Controller
@RequestMapping("/participant")
public class participantController {
	@Autowired
	private participantService service;
}
