package com.example.kwangs.bizunit.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.approval.service.Document;
import com.example.kwangs.bizunit.mapper.bizunitMapper;
import com.example.kwangs.bizunit.service.bizunitService;
import com.example.kwangs.bizunit.service.bizunitVO;
import com.example.kwangs.folder.mapper.folderMapper;
import com.example.kwangs.folder.service.folderVO;

@Service
public class bizunitServiceimpl implements bizunitService{
	private static Logger log = Logger.getLogger(bizunitServiceimpl.class.getName());
	
	@Autowired
	private bizunitMapper mapper;
	@Autowired
	private folderMapper folderMapper;
	
	//csv upload
	@Override
	public void uploadCSV(MultipartFile file) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
			String line;
			br.readLine();
			
			while((line = br.readLine()) != null) {
				String[] data =line.split(",");
				bizunitVO biz = new bizunitVO();
				biz.setBizunitcd(data[0]);
				biz.setProcdeptid(data[1]);
				biz.setProcdeptname(data[2]);
				biz.setBizunitname(data[3]);
				biz.setBizunitdesc(data[4]);
				biz.setBizunitchargeid(data[5]);
				biz.setKeepperiod(data[6]);
				

				mapper.uploadCSV(biz);
			}

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	//export csv file 할 단위과제 목록
	@Override
	public List<bizunitVO> getbizList() {
		return mapper.getbizList();
	}
	//단위과제 목록
	@Override
	public List<bizunitVO> list(){
		return mapper.list();
	}
	//단위과제 작성 시 폴더테이블에 같이 인서트
	@Override
	public void write(bizunitVO biz) {
		mapper.write(biz);
		
		folderVO b_fdInfo = folderMapper.b_fdInfo();
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		String strYear = Integer.toString(year);
		
		folderVO fd = new folderVO();
		fd.setFldrname(biz.getBizunitname());
		fd.setOwnerid(biz.getProcdeptid());
		fd.setApplid(7010);
		fd.setAppltype("3");
		fd.setOwnertype("1");
		fd.setYear(strYear);
		fd.setEndyear(strYear);
		fd.setParfldrid(b_fdInfo.getFldrid());
		fd.setParfldrname(b_fdInfo.getFldrname());
		fd.setFldrdepth(b_fdInfo.getFldrdepth()+1);
		folderMapper.FolderAdd(fd);
	}
	//기록물철 작성 시 단위과제 정보 가져오기
	@Override
	public bizunitVO bInfo(String bizunitname) {
		return mapper.bInfo(bizunitname);
	}
	
}
