package com.example.kwangs.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.approval.service.approvalVO;
import com.example.kwangs.common.file.service.AttachFileDTO;
import com.example.kwangs.common.file.service.AttachVO;
import com.example.kwangs.common.file.service.fileService;

@Controller
public class fileController {

	@Autowired
	private fileService service;
	@Autowired
	private approvalService approvalService;
	
	private static Logger log = LoggerFactory.getLogger(fileController.class.getName());
	
	//날짜 별 폴더 생성
	public static String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}	
	//첨부파일 수정폼
	@GetMapping("/AttachModifyForm")
	public void AttachModifyForm(String appr_seq, Model model) {
		List<AttachVO> attach = service.AttachModifyForm(appr_seq);
		model.addAttribute("attach",attach);
		model.addAttribute("info",approvalService.apprInfo(appr_seq));
	}
	//문서에 붙은 첨부파일 가져오기[기안]
	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachVO>>getAttachList(String appr_seq){
		return new ResponseEntity<>(service.getAttachList(appr_seq),HttpStatus.OK);
	}
	//문서에 붙은 첨부파일 가져오기[접수]
	@GetMapping(value="/getRceptAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachVO>>getRceptAttachList(String appr_seq){
		return new ResponseEntity<>(service.getRceptAttachList(appr_seq),HttpStatus.OK);
	}
	/*
	 * 기안 전 파일 업로드&삭제&등록
	 */
	//파일 업로드[기안 상신 전]
	@PostMapping(value="/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadFile(MultipartFile[] uploadFile, HttpServletRequest request){
		log.info("file upload response....");
		List<AttachFileDTO> list = new ArrayList<>();
		String id = (String) request.getSession().getAttribute("userId");
		String uploadFolderPath = getFolder();
		String uploadFolder = "/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+uploadFolderPath+"/temp/"+id;
		File uploadPath = new File(uploadFolder);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("===========================");
			log.info("Upload File Name: "+multipartFile.getOriginalFilename());
			log.info("Upload File Size: "+multipartFile.getSize());
			AttachFileDTO attachDTO = new AttachFileDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			int fileSize = (int) multipartFile.getSize();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1);
			log.info("only fileName: "+uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString()+"_"+uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				attachDTO.setFileType(100);
				attachDTO.setFilesize(fileSize);
				list.add(attachDTO);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	//업로드 된 파일 삭제[기안 상신 전]
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type, HttpServletRequest request, HttpServletResponse response){
		log.info("deleteFile: "+fileName);
		String id = (String) request.getSession().getAttribute("userId");
		try {
			String uploadFolderPath = getFolder();		
			File file = new File("/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+ uploadFolderPath+ "/temp/"+id+"/"+ URLDecoder.decode(fileName,"UTF-8"));
			file.delete();
			response.setHeader("Cache-Control","no-store");
			response.setHeader("Cache-Control","no-cache");
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
	//첨부파일 다운로드[기안 상신 전]
	@GetMapping("/download")
	public void download(String fileName, HttpServletResponse response,HttpServletRequest request)throws IOException{
		String uploadFolderPath = getFolder();
		String id = (String) request.getSession().getAttribute("userId");
		File file = new File("/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+ uploadFolderPath + "/temp/"+id+"/"+ fileName);
		//file 다운르도 요청값 설정
		response.setContentType("application/download");
		response.setContentLength((int)file.length());
		//파일명 한글일 경우 파일명 제대로 나오게..
		String encodedFileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
		response.setHeader("Content-disposition", "attachment;fileName=\""+ encodedFileName +"\"");
		
		//response 객체를 통해 서버로 부터 파일 다운
		OutputStream os = response.getOutputStream();
		//파일입력 객체생성
		FileInputStream fis = new FileInputStream(file);
		FileCopyUtils.copy(fis, os);
		fis.close();
		os.close();
	}
	/*
	 * 기안 이후 파일 업로드&삭제&등록
	 */
	//파일 업로드[기안 상신 후]
		@PostMapping(value="/InfoUploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public ResponseEntity<List<AttachFileDTO>> InfoUploadFile(MultipartFile[] uploadFile, HttpServletRequest request,
				String uploadPath, String appr_seq) {
			log.info("InfoUploadFile response....");
			List<AttachFileDTO> list = new ArrayList<>();			
			String appr_seq_ = appr_seq.substring(16);
			//해당 기안기의 파일폴더가 존재하지 않는경우
			if(uploadPath.equals("undefined")) {
				approvalVO apprInfo = approvalService.apprInfo(appr_seq);
				String regDate = apprInfo.getRegdate();
				String newPath = regDate.substring(0,10);
				String NewUploadFolder = "/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+newPath+"/"+appr_seq_;	
				log.info("NewUploadFolder "+NewUploadFolder);
			
				File uploadPath_ = new File(NewUploadFolder);		
				if(uploadPath_.exists() == false) {
					uploadPath_.mkdirs();
				}
				
				for(MultipartFile multipartFile : uploadFile) {
					log.info("===========================");
					log.info("Upload File Name: "+multipartFile.getOriginalFilename());
					log.info("Upload File Size: "+multipartFile.getSize());
					AttachFileDTO attachDTO = new AttachFileDTO();
					String uploadFileName = multipartFile.getOriginalFilename();
					int fileSize = (int) multipartFile.getSize();
					
					uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1);
					log.info("only fileName: "+uploadFileName);
					attachDTO.setFileName(uploadFileName);
					
					UUID uuid = UUID.randomUUID();
					uploadFileName = uuid.toString()+"_"+uploadFileName;
					
					try {
						File saveFilePath = new File(uploadPath_,uploadFileName);
						multipartFile.transferTo(saveFilePath);
						
						attachDTO.setUuid(uuid.toString());
						attachDTO.setUploadPath(newPath);
						attachDTO.setFileType(100);
						attachDTO.setFilesize(fileSize);
						list.add(attachDTO);
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			}else {
				String uploadFolder = "/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+uploadPath+"/"+appr_seq_;	
				log.info("InfoUploadFile "+uploadFolder);
			
				File uploadPath_ = new File(uploadFolder);		
				if(uploadPath_.exists() == false) {
					uploadPath_.mkdirs();
				}
				for(MultipartFile multipartFile : uploadFile) {
					log.info("===========================");
					log.info("Upload File Name: "+multipartFile.getOriginalFilename());
					log.info("Upload File Size: "+multipartFile.getSize());
					AttachFileDTO attachDTO = new AttachFileDTO();
					String uploadFileName = multipartFile.getOriginalFilename();
					int fileSize = (int) multipartFile.getSize();
					
					uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1);
					log.info("only fileName: "+uploadFileName);
					attachDTO.setFileName(uploadFileName);
					
					UUID uuid = UUID.randomUUID();
					uploadFileName = uuid.toString()+"_"+uploadFileName;
					
					try {
						File saveFilePath = new File(uploadPath_,uploadFileName);
						multipartFile.transferTo(saveFilePath);
						
						attachDTO.setUuid(uuid.toString());
						attachDTO.setUploadPath(uploadPath);
						attachDTO.setFileType(100);
						attachDTO.setFilesize(fileSize);
						list.add(attachDTO);
					}catch(IOException e) {
						e.printStackTrace();
					}
				}	
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		
		//업로드 된 파일 삭제[기안 상신 후]
		@PostMapping("/InfoDeleteFile")
		@ResponseBody
		public ResponseEntity<String> InfoDeleteFile(String fileName, String type, String appr_seq, String uploadPath,
				HttpServletRequest request, HttpServletResponse response){
			log.info("InfoDeleteFile: "+fileName);
			String appr_seq_ = appr_seq.substring(16);
			try {
				File file = new File("/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+ uploadPath+ "/" + appr_seq_ +URLDecoder.decode(fileName,"UTF-8"));
				log.info("InfoDeleteFile Path "+file);
				file.delete();
				response.setHeader("Cache-Control","no-store");
				response.setHeader("Cache-Control","no-cache");
			}catch(UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<String>("deleted",HttpStatus.OK);
		}
		//첨부파일 다운로드[기안 상신 후]
		@GetMapping("/InfoFileDownload")
		public void InfoFileDownload(String fileName, String appr_seq, HttpServletResponse response)throws IOException{
			List<AttachVO> attach = service.getAttachList(appr_seq);
			String appr_seq_ = appr_seq.substring(16);
			for(AttachVO attachment : attach) {
				File file = new File("/Users/kwangs/Desktop/SpringEx/example/src/FILE/"+ attachment.getUploadPath() + "/" + appr_seq_ +"/"+fileName);
				
				//file 다운르도 요청값 설정
				response.setContentType("application/download");
				response.setContentLength((int)file.length());
				//파일명 한글일 경우 파일명 제대로 나오게..
				String encodedFileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
				response.setHeader("Content-disposition", "attachment;fileName=\""+ encodedFileName +"\"");
				
				//response 객체를 통해 서버로 부터 파일 다운
				OutputStream os = response.getOutputStream();
				//파일입력 객체생성
				FileInputStream fis = new FileInputStream(file);
				FileCopyUtils.copy(fis, os);
				fis.close();
				os.close();
			}
		}
	
	//문서에 등록된 첨부파일 삭제
	@PostMapping("/ApprDocDeleteFiles")
	@ResponseBody
	public ResponseEntity<String> ApprDocDeleteFiles(String fileName, String type, String uuid,
			String appr_seq, HttpServletRequest request, HttpServletResponse response){
		log.info("ApprDocDeleteFiles: "+ fileName);
		Map<String,Object> res = new HashMap<>();
		res.put("appr_seq", appr_seq);
		res.put("fileName", fileName);
		res.put("uuid", uuid);
		service.ApprDocDeleteFiles(res);
		UpdAttachCnt(appr_seq);
		
		return new ResponseEntity<String>("deleteFiles",HttpStatus.OK);
	}
	
	//첨부파일 수정 폼에서의 등록[추가]
	@PostMapping("/ApprDocInsertFiles")
	@ResponseBody
	public ResponseEntity<String> ApprDocInsertFiles(approvalVO approval){	
		List<AttachVO> attach = approval.getAttach();
		for(int i=0; i < attach.size(); i++) {
			AttachVO attachVO = attach.get(i);
			attachVO.setAppr_seq(approval.getAppr_seq());		
			service.ApprDocInsertFiles(attachVO);
			UpdAttachCnt(approval.getAppr_seq());
		}
		
		return new ResponseEntity<String>("InsertFiles",HttpStatus.OK);
	}
	
	//재기안 및 결재진행 중 첨부파일 등록 및 삭제 시 카운트 업데이트
	public void UpdAttachCnt(String appr_seq) {
		int cnt = service.getAttachCnt(appr_seq);
		log.info("Current Cnt.. " +cnt);
		approvalVO apprInfo = approvalService.apprInfo(appr_seq);
		apprInfo.setAttachcnt(cnt);
		log.info("Update Cnt.. "+apprInfo.getAttachcnt());
		Map<String,Object>res = new HashMap<>();
		res.put("appr_seq", appr_seq);
		res.put("attachcnt", apprInfo.getAttachcnt());
		approvalService.UpdAttachCnt(res);
	}
}
