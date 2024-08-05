package com.example.kwangs.folder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.bizunit.service.bizunitService;
import com.example.kwangs.bizunit.service.bizunitVO;
import com.example.kwangs.folder.service.apprfolderVO;
import com.example.kwangs.folder.service.folderService;
import com.example.kwangs.folder.service.folderVO;

@Controller
@RequestMapping("/folder")
public class folderController {
	private Logger log = LoggerFactory.getLogger(folderController.class.getName());

	@Autowired
	private folderService service;
	@Autowired
	private bizunitService bizService;
	
	//폴더 생성(단위과제 작성 시 폴더 테이블 인서트 부분도 포함]
	@GetMapping("/FolderAdd")
	public void FolderAdd() {}
	
	@ResponseBody
	@PostMapping("/FolderAdd")
	public void FolderAdd(folderVO fd) {
		service.FolderAdd(fd);
	}
	
	//하위 폴더 생성
	@GetMapping("/subFolderAdd")
	public void subFloderAdd(Model model, folderVO fd) {
		folderVO info = service.info(fd.getFldrid());
		model.addAttribute("info",info);
		
		int subDepth = info.getFldrdepth()+1;
		model.addAttribute("subDepth",subDepth);
	}
	
	@PostMapping("/subFolderAdd")
	public String subFolderAdd(folderVO fd) {
		service.subFolderAdd(fd);
		return "redirect:/folder/list";
	}
	
	//문서목록
	@GetMapping("list")
	public void list(Model model,HttpServletRequest request) {
		String ownerid = (String)request.getSession().getAttribute("deptId");
		List<folderVO> list = service.list(ownerid);
		model.addAttribute("list",list);
	}
	
	//문서상세보기
	@GetMapping("info")
	public void info(Model model,String fldrid) {
		folderVO info = service.info(fldrid);
		model.addAttribute("info",info);
		//단위과제 정보 가져오기
		Map<String,Object>res = new HashMap<>();
		res.put("bizunitname", info.getFldrname());
		res.put("procdeptid", info.getOwnerid());
		bizunitVO bInfo = bizService.bInfo(res);
		model.addAttribute("bInfo",bInfo);
	}
	
	//기록물철 추가
	@GetMapping("/folderAddAndApprF")
	public void folderAddAndApprF(Model model,String fldrid) {
		folderVO info =  service.info(fldrid);
		model.addAttribute("info",info);
		
		//폴더 깊이
		folderVO getFldrDepth = service.getListFldrDepth(fldrid);
		if(getFldrDepth != null) {
			int value =getFldrDepth.getFldrdepth()+1;
			model.addAttribute("depth",value);
			log.info("몇번인가? "+value);			
		}else {
			int value2 = info.getFldrdepth()+1;
			model.addAttribute("depth",value2);
			log.info("___"+info.getFldrdepth());
		}
		
		//단위과제 정보 가져오기
		Map<String,Object>res = new HashMap<>();
		res.put("bizunitname", info.getFldrname());
		res.put("procdeptid", info.getOwnerid());
		bizunitVO bInfo = bizService.bInfo(res);
		model.addAttribute("bInfo",bInfo);
	}
	
	@ResponseBody
	@PostMapping("/folderAddAndApprF")
	public void folderAddAndApprF(folderVO fd, String fldrmanagerid, String fldrmanagername, bizunitVO biz,
			HttpServletRequest request)throws Exception {
		service.folderAddAndApprF(fd,fldrmanagerid,fldrmanagername,biz);
	}
	
	//폴더에 대한 문서 카운트[결재함]
	@ResponseBody
	@GetMapping("/getFolderCounts")
	public Map<String,Object> FolderCounts(HttpServletRequest request, folderVO fd,Model model) {
		String sabun = (String)request.getSession().getAttribute("sabun");
		
		Map<String,Object> res = new HashMap<>();
		res.put("sabun", sabun);
		res.put("applid", fd.getApplid());
		
		Map<String,Object> result = service.getFolderCounts(res);
		return result;
	}
	
	//수정[기록물철-정보]
	@GetMapping("/edit")
	public void edit(String fldrid,Model model, HttpServletRequest request) {
		String deptid = (String)request.getSession().getAttribute("deptId");
		Map<String, Object> res = new HashMap<>();
		res.put("fldrid", fldrid);
		res.put("procdeptid", deptid);
			
		apprfolderVO Info = service.ApprFldrInfo(res);
		model.addAttribute("Info",Info);
	}
	@ResponseBody
	@PostMapping("/edit")
	public ResponseEntity<String> edit(apprfolderVO af, HttpServletRequest request) {
		String userid = (String)request.getSession().getAttribute("userId");
		service.edit(af,userid);
		return ResponseEntity.ok("edit success");
	}
	//수정[기록물철-이관년도]
	@GetMapping("/transferYear")
	public void transferYear(String fldrid, Model model, HttpServletRequest request) {
		String deptid = (String)request.getSession().getAttribute("deptId");
		Map<String, Object> res = new HashMap<>();
		res.put("fldrid", fldrid);
		res.put("procdeptid", deptid);
			
		apprfolderVO Info = service.ApprFldrInfo(res);
		model.addAttribute("Info",Info);
			
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int futureYear = currentYear +3;
		String transferYear = String.valueOf(futureYear);
		model.addAttribute("transferYear",transferYear);
	}
	@ResponseBody
	@PostMapping("/transferYear")
	public ResponseEntity<String> transferYear(apprfolderVO af){
		service.transferYear(af);
		return ResponseEntity.ok("success transferYear kkk!");
	}
	//수정[기록물철-편철확인상태취소]
	@ResponseBody
	@PostMapping("/CancelFldrStatus")
	public ResponseEntity<String> CancelFldrStatus(apprfolderVO af){
		service.CancelFldrStatus(af);
		return ResponseEntity.ok("success CancelFldrStatus");
	}
	
	//정리할 기록물철 -> 단위과제 하위 이동
	@GetMapping("/MoveApprFldr")
	public String MoveApprFldr(Model model,String fldrid) {
		folderVO Info = service.info(fldrid);
		model.addAttribute("Info",Info);
		
		List<folderVO> DeptFolderList = service.DeptFolderList(Info.getOwnerid());
		model.addAttribute("list",DeptFolderList);

		return "/folder/MoveApprFldr";
	}
	
	@ResponseBody
	@PostMapping("/MoveApprFldr")
	public ResponseEntity<String> MoveApprFldr(folderVO fd,HttpServletRequest request) {
		String userid = (String)request.getSession().getAttribute("userId");
		String name = (String)request.getSession().getAttribute("userName");
		service.MoveApprFldr(fd,userid,name);
		return ResponseEntity.ok("Move fldr success");
	}
	
	//이관 시 인계부서 기록물철 -> 인수부서에 같은기록물철로 생성
	@ResponseBody
	@PostMapping("/MoveFldrMng")
	public ResponseEntity<String> MoveFldrMng(String fldrid,HttpServletRequest request,apprfolderVO af){
		String deptid = (String)request.getSession().getAttribute("deptId");
		Map<String,Object>res = new HashMap<>();
		res.put("fldrid", fldrid);
		res.put("procdeptid", deptid);
		res.put("biztranstype", af.getBiztranstype());
		res.put("fromdeptid", af.getFromdeptid());
		res.put("todeptid", af.getTodeptid());
		
		log.info("MoveFldrMng Fldrid.. "+fldrid);
		log.info("MoveFldrMng Procdeptid.. "+deptid);
		log.info("MoveFldrMng biztranstype.. "+af.getBiztranstype());
		log.info("MoveFldrMng fromdeptid.. "+af.getFromdeptid());
		log.info("MoveFldrMng todeptid.. "+af.getTodeptid());
		
		service.MoveFldrMng(res);
		return ResponseEntity.ok("Success MoveFldrMng.. kk");
	}
}
