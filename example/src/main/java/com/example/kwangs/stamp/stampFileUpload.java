package com.example.kwangs.stamp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.kwangs.stamp.service.stampDTO;
import com.example.kwangs.stamp.service.stampService;
import com.example.kwangs.stamp.service.stampVO;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class stampFileUpload {

	static Logger log = LoggerFactory.getLogger(stampFileUpload.class);
	@Autowired
	private stampService service;
	@Value("${stamp.data.path}")
	private String basePath;
	
	//파일 체크
	private boolean checkimageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@ResponseBody
	@GetMapping(value="/stamp/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<stampVO>> getAttachList(){
		return new ResponseEntity<>(service.getAttachList(),HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value="/stampUpload", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<stampDTO>> stampUpload(MultipartFile[] uploadFile,String id){
		log.info("stamp FileUpload Start /// ");
		List<stampDTO> list = new ArrayList<>();
		String uploadFolder = basePath;
		File uploadPath = new File(uploadFolder);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("stamp originalFile name: "+multipartFile.getOriginalFilename());
			
			stampDTO sdto = new stampDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1);
			
			log.info("stamp fileName: "+uploadFileName);		
			sdto.setId(id);
			sdto.setName(uploadFileName);
			
			try {
				String deptStampFileName = id+"."+uploadFileName;
				File saveFile = new File(uploadPath, deptStampFileName);
				multipartFile.transferTo(saveFile);
				if(checkimageType(saveFile)) {
					sdto.setType(0);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath,"s_"+deptStampFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100); //가로x세로 100,100으로
					thumbnail.close();
				}
				list.add(sdto);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String name)throws IOException{
		log.info("display FileName? "+name);
		File file = new File(basePath+"/"+name);
		
		log.info("display filePath: "+file);
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			byte[] fileContent = FileCopyUtils.copyToByteArray(file);
			result = new ResponseEntity<>(fileContent, header, HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//DB저장 전 업로드 후 로컬 에서의 삭제
		@PostMapping("/stamp/deleteFile")
		@ResponseBody
		public ResponseEntity<String> deleteFile(String name){
			log.info("deleteFile: "+ name);
			//기존 경로
			String path = basePath +"/";
			//파일명 디코딩
			String decoder;
			try {
				decoder = URLDecoder.decode(name,"UTF-8");
			}catch(UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<>("Error decoding file name ",HttpStatus.BAD_REQUEST);
			}
			
			//썸네일이미지 및 일반 이미지 업로드 경로
			File file = new File(path + decoder);
			File thumbnail = new File(path+"s_"+decoder);
			
			//조건에 필요한 불리언 변수
			boolean fileDeleted =false;
			boolean thumbnailDeleted = false;
			
			if(file.exists()) {
				fileDeleted = file.delete();
				if(fileDeleted) {
					log.info("Original file deleted: "+file.getAbsolutePath());
				}else {
					log.info("Failed to delete original file: "+file.getAbsolutePath());
				}
			}else {
				log.info("Original file not found: "+file.getAbsolutePath());
			}
			
			if(thumbnail.exists()) {
				thumbnailDeleted = thumbnail.delete();
				if(thumbnailDeleted) {
					log.info("thumbnail file deleted: "+thumbnail.getAbsolutePath());
				}else {
					log.info("Failed to delete thumbnail file: "+thumbnail.getAbsolutePath());
				}
			}else {
				log.info("thumbnail file not found: "+thumbnail.getAbsolutePath());
			}
			
			//최종적으로 썸네일 및 이미지 파일이 삭제가 되엇다면 리턴
			if(fileDeleted || thumbnailDeleted) {
				return new ResponseEntity<String>("Files deleted successfully", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("No files deleted",HttpStatus.NOT_FOUND);		
			}
		}
		
		//DB, 로컬 파일삭제
		@PostMapping("/stamp/InfoDeleteInfo")
		@ResponseBody
		public ResponseEntity<String> InfoDeleteInfo(String id, String name, int fno,
				HttpServletRequest request, HttpServletResponse response){
			log.info("stamp Info Delete FileName? "+name);
			log.info("stamp Info Delete Deptid? "+id);
			log.info("stamp Info Delete fno(depth)? "+fno);
			
			Map<String,Object> res= new HashMap<>();
			res.put("id", id);
			res.put("name", name);
			res.put("fno", fno);
			service.StampDeleteFiles(res);
			
			try {
				String uploadFolder = basePath;
				name = id+"."+name;
				File file = new File(uploadFolder + "/" + URLDecoder.decode(name,"UTF-8"));
				File thumbnail = new File(uploadFolder + "/s_"  +URLDecoder.decode(name,"UTF-8"));
				file.delete();
				thumbnail.delete();
				response.setHeader("Cache-control", "no-store");
				response.setHeader("Cache-control", "no-cache");
				log.info(uploadFolder);
				log.info(file+" original File delete Success");
				log.info(thumbnail+" thumbnail File delete Success");
			}catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<String>("deleted",HttpStatus.OK);
		}
		
		//파일 다운로드
		@GetMapping("/stamp/download")
		public void stampDownload(String name,HttpServletResponse response) throws IOException{
			log.info("download FileName? "+name);
			File file = new File(basePath +"/"+name);
			
			if(!file.exists()) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				log.info("Not define File");
				return;
			}
			response.setContentType("application/download");
			response.setContentLength((int)file.length());
			
			String originalFileName = name.substring(name.indexOf(".")+1);
			String encodedFileName = new String(originalFileName.getBytes("UTF-8"),"ISO-8859-1");
			response.setHeader("Content-disposition", "attachment;fileName=\""+ encodedFileName +"\"");
			
			OutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, os);
			fis.close();
			os.close();
		}
}
