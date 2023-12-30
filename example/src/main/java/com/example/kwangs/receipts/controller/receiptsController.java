package com.example.kwangs.receipts.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String write(receiptsVO receipts, @RequestBody List<paticipantVO> paticipant) {
	    int result = service.write(receipts, paticipant);
	    if(result == 1) {
	    	log.info("값 체크: "+paticipant);
		    return "success";
	    }else {
	    	return "failure";
	    }
	}
}
