package com.example.kwangs.dept;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.dept.service.deptService;
import com.example.kwangs.dept.service.deptVO;
import com.example.kwangs.folder.service.folderVO;
import com.example.kwangs.folder.service.impl.folderServiceimpl;

@Controller
@RequestMapping("/dept")
public class deptController {

	@Autowired
	private deptService service;
	@Autowired
	private folderServiceimpl folderService;
	@Autowired
	private approvalService approvalService;
	
	//목록
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("list",service.list());
		return "dept/list";
	}
	
	//부서 생성
	@GetMapping("/write")
	public void write() {}
	
	@PostMapping("/write")
	public String write(deptVO dept) {
		service.write(dept);
		return "redirect:/dept/list";
	}
	
	//하위부서 등록
	@GetMapping("/subDept")
	public void subDept(String deptid, Model model) {
		model.addAttribute("info",service.info(deptid));
	}
	
	@PostMapping("/subDept")
	public String subDept(deptVO dept) {
		service.subDept(dept);
		return "redirect:/dept/list";
	}
	
	//상세보기
	@GetMapping("/info")
	public deptVO info(String deptid, Model model) {
		deptVO dept = service.info(deptid);
		model.addAttribute("info",dept);
		return dept;
	}
	
	//회원가입 시 불러올 부서목록
	@GetMapping("/joinUseDept")
	public String joinUseDept(Model model) {
		model.addAttribute("joinUseDept",service.joinUseDept());
		return "dept/joinUseDept";
	}
	
	//결재선 정보 가져올 부서 및 유저목록 & 기록물철 정보
	@GetMapping("/flowUseInfo")
	public String flowUseInfo(Model model, HttpServletRequest request) {
		String id = (String) request.getSession().getAttribute("userId");
		model.addAttribute("user",id);
		
		List<deptVO>flowUseInfo = service.flowUseInfo();
		model.addAttribute("flowUseInfo",flowUseInfo);
		
		String deptid = (String)request.getSession().getAttribute("deptId");
		List<folderVO> DeptFolderList = folderService.DeptFolderList(deptid);
		model.addAttribute("list",DeptFolderList);
		
		List<deptVO> getSender = service.getSender(id);
		model.addAttribute("sender",getSender);
		model.addAttribute("deptList",service.joinUseDept());
		
		return "dept/flowUseInfo";
	}
	
	//결재선 정보 가져올 부서 및 유저목록 & 기록물철 정보[접수]
	@GetMapping("/RceptflowUseInfo")
	public String RceptflowUseInfo(Model model, HttpServletRequest request,String appr_seq) {
		String id = (String) request.getSession().getAttribute("userId");
		model.addAttribute("user",id);
		
		List<deptVO>flowUseInfo = service.flowUseInfo();
		model.addAttribute("flowUseInfo",flowUseInfo);
		
		String deptid = (String)request.getSession().getAttribute("deptId");
		List<folderVO> DeptFolderList = folderService.DeptFolderList(deptid);
		model.addAttribute("list",DeptFolderList);
		
		List<deptVO> getSender = service.getSender(id);
		model.addAttribute("sender",getSender);
		model.addAttribute("deptList",service.joinUseDept());
		
		model.addAttribute("DocInfo",approvalService.apprInfo(appr_seq));
		
		return "dept/RceptflowUseInfo";
	}
	
	//재기안 시 일반 문서 결재선 정보 가져올 부서 및 유저목록 & 기록물철 정보
	@GetMapping("/ResubmissionFlowUseInfo")
	public String ResubmissionFlowUseInfo(Model model, HttpServletRequest request,String appr_seq) {
		String id = (String) request.getSession().getAttribute("userId");
		model.addAttribute("user",id);
		
		List<deptVO>flowUseInfo = service.flowUseInfo();
		model.addAttribute("flowUseInfo",flowUseInfo);
		
		String deptid = (String)request.getSession().getAttribute("deptId");
		List<folderVO> DeptFolderList = folderService.DeptFolderList(deptid);
		model.addAttribute("list",DeptFolderList);
		
		List<deptVO> getSender = service.getSender(id);
		model.addAttribute("sender",getSender);
		model.addAttribute("deptList",service.joinUseDept());
		
		model.addAttribute("DocInfo",approvalService.apprInfo(appr_seq));
		
		return "dept/ResubmissionFlowUseInfo";
	}
}
