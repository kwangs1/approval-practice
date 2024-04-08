package com.example.kwangs.folder;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.bizunit.service.bizunitService;
import com.example.kwangs.bizunit.service.bizunitVO;
import com.example.kwangs.folder.service.IfolderS;
import com.example.kwangs.folder.service.folderVO;

@Controller
@RequestMapping("/folder")
public class folderC {

	@Autowired
	private IfolderS service;
	@Autowired
	private bizunitService bizService;
	
	//부서 별 폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	@GetMapping("/deptAllFolderAdd")
	public void deptAllFolderAdd() {}
	
	@ResponseBody
	@PostMapping("/deptAllFolderAdd")
	public void deptAllFolderAdd(folderVO fd) {
		service.deptAllFolderAdd(fd);
	}
	
	//문서목록
	@GetMapping("list")
	public void list(Model model,HttpServletRequest request) {
		List<folderVO> list = service.list();
		model.addAttribute("list",list);
	}
	
	//문서상세보기
	@GetMapping("info")
	public void info(Model model,String fldrid) {
		folderVO info = service.info(fldrid);
		model.addAttribute("info",info);
	}
	
	//기록물철 추가
	@GetMapping("/apprfolderAdd")
	public void apprfolderInfo(Model model,String fldrid) {
		folderVO info =  service.info(fldrid);
		model.addAttribute("info",info);
		
		//폴더 깊이
		int depth = info.getFldrdepth() +1;
		model.addAttribute("depth",depth);
		
		//기록물철 정보 가져오기
		bizunitVO bInfo = bizService.bInfo(info.getFldrname());
		model.addAttribute("bInfo",bInfo);
	}
	
	@ResponseBody
	@PostMapping("/apprfolderAdd")
	public void apprfolderIn(folderVO fl, HttpServletRequest request)throws Exception {
		String userid = (String)request.getSession().getAttribute("userId");
		service.apprfolderAdd(fl,userid);
	}
}
