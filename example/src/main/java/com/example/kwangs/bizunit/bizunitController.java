package com.example.kwangs.bizunit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.kwangs.bizunit.service.bizunitService;
import com.example.kwangs.bizunit.service.bizunitVO;

@Controller
@RequestMapping("/bizunit")
public class bizunitController {
	private static Logger log = Logger.getLogger(bizunitController.class.getName());
	
	@Autowired
	private bizunitService service;
	
	//단위과제 CSV파일 업로드[insert]
	@GetMapping("/uploadCSV")
	public void upload() {}
	
	@ResponseBody
	@PostMapping("/uploadCSV")
	public String uploadCSV(MultipartFile file) {
		if(file.isEmpty()) {
			log.info("Pleas select a CSV file to upload.");
			return "/bizunit/uploadCSV";
		}
		
		try {
			service.uploadCSV(file);
			log.info("File upload success");
		}catch(Exception e) {
			log.info("File upload Fail.. "+e.getMessage());
		}
		return "/bizunit/uploadCSV";
	}
	
	//단위과제 CSV 익스포트
	@GetMapping("exportCSV")
	public void exportCSV(HttpServletResponse response) {
		List<bizunitVO> bizList = service.getbizList();
		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=bizunits.csv");
			try(PrintWriter writer = response.getWriter()){
				//csv 헤더
				writer.append("Bizunitcd,Procdeptid,Procdeptname,Bizunitname,Bizunitdesc,Bizunitchargeid,Keepperiod\n");
				//csv 데이터
				for(bizunitVO biz : bizList) {
					writer.append(biz.getBizunitcd());
					writer.append(",").append(biz.getProcdeptid());
					writer.append(",").append(biz.getProcdeptname());
					writer.append(",").append(biz.getBizunitname());
					writer.append(",").append(biz.getBizunitdesc());
					writer.append(",").append(biz.getBizunitchargeid());
					writer.append(",").append(biz.getKeepperiod());
					writer.append("\n");
					
					log.info("SendData {} "+biz);
				}
			}catch(IOException e) {
				log.info("Export CSVFile SendData Error :"+e.getMessage());
			}
		}catch(Exception e) {
			log.info("Export CSVFile DOWNLOAD Error :"+e.getMessage());
		}
	
	}
	
	//단위과제 목록
	@GetMapping("/list")
	public void list(Model model) {
		List<bizunitVO> list = service.list();
		model.addAttribute("list",list);
	}
	
	//작성
	@GetMapping("/write")
	public void write() {}
	
	@ResponseBody
	@PostMapping("/write")
	public void write(bizunitVO biz, HttpServletRequest request) {
		String deptid = (String) request.getSession().getAttribute("deptId");
		service.write(biz,deptid);
	}
}
