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
	//상신 시 첨부파일 insert
	public void DocFileIn(AttachVO attach) {
		session.insert("Attach.DocFileIn",attach);
	}
	//첨부파일 리스트
	public List<AttachVO> getAttachList(String appr_seq){
		return session.selectList("Attach.getAttachList",appr_seq);
	}
	//첨부파일 리스트[접수]
	public List<AttachVO> getRceptAttachList(String appr_seq){
		return session.selectList("Attach.getRceptAttachList",appr_seq);
	}
	//첨부파일 수정폼
	public List<AttachVO> AttachModifyForm(String appr_seq){
		return session.selectList("Attach.AttachModifyForm",appr_seq);
	}
	//첨부파일 삭제
	public void ApprDocDeleteFiles(Map<String,Object>res) {
		session.delete("Attach.ApprDocDeleteFiles",res);
	}
	//첨부파일 수정 폼에서의 등록[추가]
	public void ApprDocInsertFiles(AttachVO attach) {
		session.insert("Attach.ApprDocInsertFiles",attach);
	}
	//해당 문서의 첨부파일 갯수 카운트
	public int getAttachCnt(String appr_seq) {
		return session.selectOne("Attach.getAttachCnt",appr_seq);
	}
	//문서 삭제 시 첨부파일 삭제
	public void deleteDocAttach(String appr_seq) {
		session.delete("Attach.deleteDocAttach",appr_seq);
	}
}
