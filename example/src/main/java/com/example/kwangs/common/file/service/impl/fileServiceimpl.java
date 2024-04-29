package com.example.kwangs.common.file.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.common.file.mapper.fileMapper;
import com.example.kwangs.common.file.service.AttachVO;
import com.example.kwangs.common.file.service.fileService;

@Service
public class fileServiceimpl implements fileService{

	@Autowired
	private fileMapper mapper;
	@Autowired
	private approvalMapper approvalMapper;
	
	private Logger log = LoggerFactory.getLogger(fileServiceimpl.class.getName());
	
	//첨부파일 리스트
	@Override
	public List<AttachVO> getAttachList(String appr_seq){
		return mapper.getAttachList(appr_seq);
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
		String appr_seq = (String)res.get("appr_seq");
		int cnt = mapper.AttachCnt(appr_seq);
		UpdateDocAttachCnt(appr_seq, cnt);
	}
	//해당 문서 결재테이블의 첨부파일 카운트 업데이트
	public void UpdateDocAttachCnt(String appr_seq,int attachcnt) {
		log.info("..cnt "+attachcnt);
		
		Map<String,Object> res = new HashMap<>();
		res.put("appr_seq", appr_seq);
		res.put("attachcnt", attachcnt);
		
		approvalMapper.UpdateDocAttachCnt(res);
	}
	//첨부파일 수정 폼에서의 등록[추가]
	@Override
	public void ApprDocInsertFiles(AttachVO attach) {	
		mapper.ApprDocInsertFiles(attach);
	}

}
