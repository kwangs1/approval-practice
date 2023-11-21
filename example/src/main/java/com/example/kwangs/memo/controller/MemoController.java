package com.example.kwangs.memo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kwangs.memo.domain.memoVO;
import com.example.kwangs.memo.service.MemoService;

@Controller
@RequestMapping("/memo")
public class MemoController {
	
	private MemoService service;
	
	//메모 작성
	@GetMapping("/write")
	public void write() {}
	
	@PostMapping("/write")
	public void write(memoVO memo) {
		service.write(memo);
	}
	
}
