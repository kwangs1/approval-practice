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
	
	//첨부파일 리스트
	@Override
	public List<AttachVO> getAttachList(String appr_seq){
		return mapper.getAttachList(appr_seq);
	}
	//첨부파일 리스트[접수]
	@Override
	public List<AttachVO> getRceptAttachList(String appr_seq){
		return mapper.getRceptAttachList(appr_seq);
	}
	//첨부파일 수정폼
	@Override
	public List<AttachVO> AttachModifyForm(String appr_seq){
		return mapper.AttachModifyForm(appr_seq);
	}
	//첨부파일 삭제
	@Override
	public void ApprDocDeleteFiles(Map<String,Object>res) {
		mapper.ApprDocDeleteFiles(res);
	}
	//첨부파일 수정 폼에서의 등록[추가]
	@Override
	public void ApprDocInsertFiles(AttachVO attach) {	
		mapper.ApprDocInsertFiles(attach);
	}
	//해당 문서의 첨부파일 갯수 카운트
	@Override
	public int getAttachCnt(String appr_seq) {
		return mapper.getAttachCnt(appr_seq);
	}

}
