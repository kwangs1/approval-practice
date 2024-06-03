package com.example.kwangs.participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.approval.service.sendVO;
import com.example.kwangs.participant.service.participantService;
import com.example.kwangs.participant.service.participantVO;

@Controller
@RequestMapping("/participant")
public class participantController {
	private Logger log = LoggerFactory.getLogger(participantController.class.getName());
	@Autowired
	private participantService service;
	@Autowired
	private approvalService approvalService;
	
	//일괄 결재
	@ResponseBody
	@PostMapping("/BulkAppr")
	public ResponseEntity<String> BulkAppr(@RequestBody List<participantVO> participant){
		service.BulkAppr(participant);
		return ResponseEntity.ok("BulkAppr update success");
	}
	
	//문서 기안 시 결재선 지정
	@GetMapping("/ParticipantWrite")
	public void ParticipantWrite(Model model,String appr_seq,HttpServletRequest request) {
		model.addAttribute("info",approvalService.apprInfo(appr_seq));
		
		String deptid = (String)request.getSession().getAttribute("deptId");
		Map<String,Object> send = new HashMap<>();
		send.put("appr_seq", appr_seq);
		send.put("receiverid", deptid);
		sendVO sendInfo = approvalService.getSendInfo(send);
		model.addAttribute("sendInfo",sendInfo);
	}

	@ResponseBody
	@PostMapping("/ParticipantWrite")
	public ResponseEntity<String> ParticipantWrite(@RequestBody List<participantVO> participant, HttpServletRequest request) {
		String id = (String) request.getSession().getAttribute("userId");
		service.ParticipantWrite(participant,id);
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
	
	//회수 [리스트]
	@ResponseBody
	@PostMapping("/RetireAppr")
	public ResponseEntity<String> RetireAppr(@RequestBody List<participantVO> participant){
		for(participantVO pp : participant) {
			Map<String,Object> res = new HashMap<>();
			res.put("participant_seq", pp.getParticipant_seq());
			res.put("appr_seq", pp.getAppr_seq());
			res.put("signerid", pp.getSignerid());
			res.put("deptid", pp.getDeptid());
			res.put("status", pp.getStatus());
			
			service.RetireAppr(res);
			approvalService.RetireApprStatus(pp.getAppr_seq());
			log.info("RetireAppr SendData {}" +res);
		}
		
		return ResponseEntity.ok("RetireAppr Update Success");
	}
	
	//재기안 시 결재자 상태값 업데이트
	@ResponseBody
	@PostMapping("/ResubmissionFlowStatusUpd")
	public ResponseEntity<String> ResubmissionFlowStatusUpd(@RequestBody List<participantVO> participant){
		service.ResubmissionFlowStatusUpd(participant);
		return ResponseEntity.ok("ResubmissionFlowStatusUpd Success Update");
	}
	
	//재기안 시 결재선 새로 추가
	@ResponseBody
	@PostMapping("/ResubmissionParticipantWrite")
	public ResponseEntity<String> ResubmissionParticipantWrite(@RequestBody List<participantVO> participant, HttpServletRequest request){
		String id = (String)request.getSession().getAttribute("userId");
		try {
			service.ResubmissionParticipantWrite(participant,id);
			return ResponseEntity.ok("success");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
		}
	}
}
