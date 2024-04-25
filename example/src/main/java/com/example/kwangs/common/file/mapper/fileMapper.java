package com.example.kwangs.common.file.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.common.file.service.AttachVO;

@Repository
public class fileMapper {

	@Autowired
	private SqlSession session;
	
	public void DocFileIn(AttachVO attach) {
		session.insert("Attach.DocFileIn",attach);
	}
	
	public List<AttachVO> getAttachList(String appr_seq){
		return session.selectList("Attach.getAttachList",appr_seq);
	}
}
