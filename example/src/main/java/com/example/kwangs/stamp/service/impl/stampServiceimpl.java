package com.example.kwangs.stamp.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.stamp.mapper.stampMapper;
import com.example.kwangs.stamp.service.stampDTO;
import com.example.kwangs.stamp.service.stampService;
import com.example.kwangs.stamp.service.stampVO;

@Service
public class stampServiceimpl implements stampService{

	static Logger log = LoggerFactory.getLogger(stampServiceimpl.class);
	@Autowired
	private stampMapper mapper;
	
	@Override
	public List<stampVO> list(){
		return mapper.list();
	}
	
	@Override
	public void write(stampVO stamp) {
		List<stampDTO> s = stamp.getStamp();
		if(s != null && !s.isEmpty()) {
			for(stampDTO stampDTO : s) {
				mapper.write(stampDTO);
			}
		}
	}
	
	@Override
	public List<stampVO> getAttachList() {
		return mapper.getAttachList();
	}
	
	@Override
	public void StampDeleteFiles(Map<String, Object> res) {
		mapper.StampDeleteFiles(res);
	}
	
	@Override
	public List<stampVO> getFlowDeptStampList(String appr_seq){
		return mapper.getFlowDeptStampList(appr_seq);
	}
	@Override
	public stampVO getApprStampInfo(String appr_seq) {
		return mapper.getApprStampInfo(appr_seq);
	}
}
