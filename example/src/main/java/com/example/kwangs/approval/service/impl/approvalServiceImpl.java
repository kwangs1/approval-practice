package com.example.kwangs.approval.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.approval.mapper.approvalMapper;
import com.example.kwangs.approval.service.approvalService;
import com.example.kwangs.approval.service.approvalVO;


@Service
public class approvalServiceImpl implements approvalService{
	private final Logger log = LoggerFactory.getLogger(approvalServiceImpl.class);
	@Autowired
	private approvalMapper mapper;
	
	//문서 작성
	@Override
	public void apprWrite(approvalVO approval) {
		mapper.apprWrite(approval);
	}

	@Override
	public List<approvalVO> apprWaitList(String id) {	
		return mapper.apprWaitList(id);
	}
	
	@Override
	public approvalVO apprInfo(String appr_seq) {
		return mapper.apprInfo(appr_seq);
	}
	

}
