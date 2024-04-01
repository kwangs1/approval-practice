package com.example.kwangs.bizunit.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.kwangs.bizunit.mapper.bizunitMapper;
import com.example.kwangs.bizunit.service.bizunitService;
import com.example.kwangs.bizunit.service.bizunitVO;

@Service
public class bizunitServiceimpl implements bizunitService{
	private static Logger log = Logger.getLogger(bizunitServiceimpl.class.getName());
	
	@Autowired
	private bizunitMapper mapper;
	
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
	
	@Override
	public List<bizunitVO> getbizList() {
		return mapper.getbizList();
	}
	
	@Override
	public List<bizunitVO> list(){
		return mapper.list();
	}
	
	@Override
	public void write(bizunitVO biz) {
		mapper.write(biz);
	}
}
