package com.example.kwangs.common.file.mapper;

import java.util.List;
import java.util.Map;

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
	
	public void ApprDocDeleteFiles(Map<String,Object>res) {
		session.delete("Attach.ApprDocDeleteFiles",res);
	}
	
	public List<AttachVO> AttachModifyForm(String appr_seq){
		return session.selectList("Attach.AttachModifyForm",appr_seq);
	}
	
	public int AttachCnt(String appr_seq) {
		return session.selectOne("Attach.AttachCnt",appr_seq);
	}
}
