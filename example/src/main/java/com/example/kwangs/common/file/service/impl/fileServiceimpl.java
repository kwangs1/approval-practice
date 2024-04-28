package com.example.kwangs.common.file.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.common.file.mapper.fileMapper;
import com.example.kwangs.common.file.service.AttachVO;
import com.example.kwangs.common.file.service.fileService;

@Service
public class fileServiceimpl implements fileService{

	@Autowired
	private fileMapper mapper;
	
	private Logger log = LoggerFactory.getLogger(fileServiceimpl.class.getName());
	
	@Override
	public List<AttachVO> getAttachList(String appr_seq){
		return mapper.getAttachList(appr_seq);
	}
	
	@Override
	public void ApprDocDeleteFiles(Map<String,Object>res) {
		mapper.ApprDocDeleteFiles(res);
	}
	
	@Override
	public List<AttachVO> AttachModifyForm(String appr_seq){
		return mapper.AttachModifyForm(appr_seq);
	}

}
