package com.example.kwangs.memo.controller;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.memo.domain.memoVO;
import com.example.kwangs.memo.service.memoService;

@Controller
@RequestMapping("/memo")
public class memoController {
	private static Logger log = Logger.getLogger(memoController.class.getName());
	@Autowired
	private memoService service;
	
	@GetMapping("/read")
	public void read(Model model,int mno) {
		model.addAttribute("read",service.read(mno));
	}
	@ResponseBody
	@GetMapping(value="/list", produces="application/json")
	public ResponseEntity<HashMap<String,Object>> list(HttpServletResponse response) {
		HashMap<String,Object> result = new HashMap<>();
		
		result.put("list", service.list());

		log.info("memo list controller success");
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/write")
	public void write() {}
	
	/**
	 * @RequestBody : 클라이언트에서 전송한 JSON데이터를 자바 객체로 변환 하는데 사용.
	 * @ResponseBody :자바 객체를 JSON 형식으로 응답 하는데 사용.
	 * @return ResponseEntity.ok("") : 클라이언트에서는 응답을 받아오기 위해 success 콜백에서 인자 추가 처리.
	 */
	@ResponseBody
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody memoVO memo) {
		service.write(memo);
		return ResponseEntity.ok("Memo inserted successfully");
	}
	
	@GetMapping("/TitleUpdate")
	public void TitleUpdate() {
		
	}
	@ResponseBody
	@PostMapping("/TitleUpdate")
	public ResponseEntity<String> TitleUpdate(memoVO memo){
		service.TitleUpdate(memo);
		return ResponseEntity.ok("Memo Title Update SuccessFully");
	}
}
