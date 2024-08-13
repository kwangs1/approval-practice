package com.example.kwangs.stamp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.stamp.service.stampDTO;
import com.example.kwangs.stamp.service.stampVO;

@Repository
public class stampMapper {

	static Logger log = LoggerFactory.getLogger(stampMapper.class);
	@Autowired
	private SqlSession session;
	
	public List<stampVO> list(){
		return session.selectList("stamp.list");
	}
	
	public void write(stampDTO stampDTO) {
		session.insert("stamp.write",stampDTO);
	}
	
	public List<stampVO> getAttachList() {
		return session.selectList("stamp.getAttachList");
	}
	
	public void StampDeleteFiles(Map<String,Object>res) {
		session.delete("stamp.StampDeleteFiles",res);
	}
	
	public List<stampVO> getFlowDeptStampList(String appr_seq){
		return session.selectList("stamp.getFlowDeptStampList",appr_seq);
	}
}
