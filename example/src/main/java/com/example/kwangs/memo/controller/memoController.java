package com.example.kwangs.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kwangs.memo.domain.memoVO;
import com.example.kwangs.memo.service.memoService;

@Controller
@RequestMapping("/memo")
public class memoController {
	@Autowired
	private memoService service;
	
	//메모 작성
	@GetMapping("/write")
	public void write() {}
	
	@PostMapping("/write")
	public void write(memoVO memo) {
		service.write(memo);
	}
	
}
