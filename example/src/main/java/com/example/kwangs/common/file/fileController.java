package com.example.kwangs.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.kwangs.common.file.service.AttachFileDTO;
import com.example.kwangs.common.file.service.AttachVO;
import com.example.kwangs.common.file.service.fileService;

@Controller
public class fileController {

	@Autowired
	private fileService service;
	
	private static Logger log = LoggerFactory.getLogger(fileController.class.getName());
	
	//날짜 별 폴더 생성
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}

	
	//파일 업로드
	@PostMapping(value="/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadFile(MultipartFile[] uploadFile, HttpServletRequest request){
		log.info("file upload response....");
		String id = (String)request.getSession().getAttribute("userId");
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "/Users/kwangs/Desktop/SpringEx/example/src/"+id;
		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("===========================");
			log.info("Upload File Name: "+multipartFile.getOriginalFilename());
			log.info("Upload File Size: "+multipartFile.getSize());
			AttachFileDTO attachDTO = new AttachFileDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			
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
				list.add(attachDTO);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//업로드 된 파일 삭제
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type, HttpServletRequest request){
		log.info("deleteFile: "+fileName);
		String id = (String)request.getSession().getAttribute("userId");
		File file;
		try {
			file = new File("/Users/kwangs/Desktop/SpringEx/example/src/"+ id + "/" + URLDecoder.decode(fileName,"UTF-8"));
			file.delete();
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
	
	//문서에 붙은 첨부파일 가져오기
	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachVO>>getAttachList(String appr_seq){
		return new ResponseEntity<>(service.getAttachList(appr_seq),HttpStatus.OK);
	}
	
	//첨부파일 다운로드
	@GetMapping("/download")
	public void download(String fileName, String id, HttpServletResponse response)throws IOException{
		File file = new File("/Users/kwangs/Desktop/SpringEx/example/src/"+ id + "/" + fileName);
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
