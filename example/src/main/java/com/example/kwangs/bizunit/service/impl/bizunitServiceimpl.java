package com.example.kwangs.bizunit.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.kwangs.bizunit.mapper.bizunitMapper;
import com.example.kwangs.bizunit.service.bizunitService;
import com.example.kwangs.bizunit.service.bizunitVO;
import com.example.kwangs.folder.mapper.folderMapper;
import com.example.kwangs.folder.service.apprfolderVO;
import com.example.kwangs.folder.service.folderService;
import com.example.kwangs.folder.service.folderVO;

@Service
public class bizunitServiceimpl implements bizunitService{
	private static Logger log = Logger.getLogger(bizunitServiceimpl.class.getName());
	
	@Autowired
	private bizunitMapper mapper;
	@Autowired
	private folderMapper folderMapper;
	@Autowired
	private folderService folderService;
	
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
	public void write(bizunitVO biz,String id, String name) {
		mapper.write(biz);
		
		folderVO b_fdInfo = folderMapper.b_fdInfo(biz.getProcdeptid());
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
		if(biz.getKeepperiod().equals("01")) {
			int t = year+1;
			String t2 = Integer.toString(t); 
			fd.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("03")) {
			int t = year+3;
			String t2 = Integer.toString(t); 
			fd.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("05")) {
			int t = year+5;
			String t2 = Integer.toString(t); 
			fd.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("10")) {
			int t = year+10;
			String t2 = Integer.toString(t); 
			fd.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("20")) {
			int t = year+20;
			String t2 = Integer.toString(t); 
			fd.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("30")) {
			int t = year+50;
			String t2 = Integer.toString(t); 
			fd.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("40")) {
			fd.setEndyear("9999");
		}
		fd.setParfldrid(b_fdInfo.getFldrid());
		fd.setParfldrname(b_fdInfo.getFldrname());
		fd.setFldrdepth(b_fdInfo.getFldrdepth()+1);
		folderMapper.FolderAdd(fd);
		
		apprfolderVO af = new apprfolderVO();
		af.setFldrid(fd.getFldrid());
		af.setFldrinfoyear(strYear);
		af.setBizunitcd(biz.getBizunitcd());
		af.setProcdeptid(biz.getProcdeptid());
		af.setFilingflag("0");
		af.setBiztranstype("0");
		af.setKeepingperiod(biz.getKeepperiod());
		af.setProcstatus("0");
		af.setProdyear(strYear);
		if(biz.getKeepperiod().equals("01")) {
			int t = year+1;
			String t2 = Integer.toString(t); 
			af.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("03")) {
			int t = year+3;
			String t2 = Integer.toString(t); 
			af.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("05")) {
			int t = year+5;
			String t2 = Integer.toString(t); 
			af.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("10")) {
			int t = year+10;
			String t2 = Integer.toString(t); 
			af.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("20")) {
			int t = year+20;
			String t2 = Integer.toString(t); 
			af.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("30")) {
			int t = year+50;
			String t2 = Integer.toString(t); 
			af.setEndyear(t2);
		}else if(biz.getKeepperiod().equals("40")) {
			af.setEndyear("9999");
		}
		af.setFldrmanagerid(id);
		af.setFldrmanagername(name);
		af.setBizunityearseq(folderService.ApprFldrBizunitYearSeq(biz.getProcdeptid()));
		folderMapper.apprFolderAdd(af);
	}
	//기록물철 작성 시 단위과제 정보 가져오기
	@Override
	public bizunitVO bInfo(Map<String,Object>res) {
		return mapper.bInfo(res);
	}
	
}
