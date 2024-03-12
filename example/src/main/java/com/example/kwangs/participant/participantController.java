package com.example.kwangs.participant;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.participant.service.participantService;
import com.example.kwangs.participant.service.participantVO;

@Controller
@RequestMapping("/participant")
public class participantController {
	private Logger log = LoggerFactory.getLogger(participantController.class.getName());
	@Autowired
	private participantService service;
	
	//일괄 결재
	@ResponseBody
	@PostMapping("/BulkAppr")
	public ResponseEntity<String> BulkAppr(@RequestBody List<participantVO> participant){
		service.BulkAppr(participant);
		return ResponseEntity.ok("BulkAppr update success");
	}
	
	//문서 기안 시 결재선 지정
	@GetMapping("/ParticipantWrite")
	public void ParticipantWrite() {}

	@ResponseBody
	@PostMapping("/ParticipantWrite")
	public ResponseEntity<String> ParticipantWrite(@RequestBody List<participantVO> participant) {
		service.ParticipantWrite(participant);
	    return ResponseEntity.ok("Success");
	}
	
	//결재
	@ResponseBody
	@PostMapping("/FlowAppr")
	public ResponseEntity<String> FlowAppr(participantVO participant){	
		try {
			service.FlowAppr(participant);
			return ResponseEntity.ok("결재성공");
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결재 처리에 실패했습니다.");
		}
	}
}
