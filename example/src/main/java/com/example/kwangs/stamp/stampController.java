package com.example.kwangs.stamp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.dept.service.deptService;
import com.example.kwangs.dept.service.deptVO;
import com.example.kwangs.stamp.service.stampService;
import com.example.kwangs.stamp.service.stampVO;

@Controller
public class stampController {

	static Logger log = LoggerFactory.getLogger(stampController.class);
	@Autowired
	private stampService service;
	@Autowired
	private deptService deptService;
	
	@GetMapping("/stamp/stampCheckdeptList")
	public void stampCheckdeptList(Model model) throws Exception{
		List<deptVO> checkList = deptService.joinUseDept();
		model.addAttribute("list",checkList);
	}
	
	@ResponseBody
	@GetMapping("/stamp/list")
	public ResponseEntity<List<stampVO>>list() throws Exception {
		return new ResponseEntity<>(service.list(), HttpStatus.OK);
	}
	
	@GetMapping("/stamp/write.do")
	public void write() {}
	
	@ResponseBody
	@PostMapping("/stamp/write")
	public ResponseEntity<String> write(stampVO stamp){
		service.write(stamp);
		return ResponseEntity.ok("stamp add success");
	}
	
	//발송 시 관인 데이터 가져오기
	@GetMapping("/stamp/getFlowDeptStampList")
	public void getFlowDeptStampList(Model model, String appr_seq) {
		List<stampVO> stamp = service.getFlowDeptStampList(appr_seq);
		model.addAttribute("stamp",stamp);
	}
}
