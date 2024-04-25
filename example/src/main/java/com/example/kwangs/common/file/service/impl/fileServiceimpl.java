package com.example.kwangs.common.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.common.file.mapper.fileMapper;
import com.example.kwangs.common.file.service.AttachVO;
import com.example.kwangs.common.file.service.fileService;

@Service
public class fileServiceimpl implements fileService{

	@Autowired
	private fileMapper mapper;
	
	@Override
	public List<AttachVO> getAttachList(String appr_seq){
		return mapper.getAttachList(appr_seq);
	}
}
