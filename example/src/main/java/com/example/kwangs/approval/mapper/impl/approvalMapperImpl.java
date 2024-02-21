package com.example.kwangs.approval.mapper.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.approval.domain.approvalVO;
import com.example.kwangs.approval.mapper.approvalMapper;

@Repository
public class approvalMapperImpl implements approvalMapper{
	@Autowired
	private SqlSession session;
	
	//결재선 지정 후 결재 상신 시 결재 테이블에 결재 시퀀스 값 가져오기 위한 시퀀스 셀렉트
	@Override
	public String getLatestReceiptsSeq() {
		return session.selectOne("mapper.approval.getLatestReceiptsSeq");
	}
	//결재 작성
	@Override
	public void apprWrite(approvalVO approval) {
		  session.insert("mapper.approval.apprWrite",approval);
	}
	//결재 대기
	@Override
	public List<approvalVO> apprWaitList(String id) {
		return session.selectList("mapper.approval.apprWaitList",id);
	}
	//결재 상세보기
	@Override
	public approvalVO apprInfo(String appr_seq) {
		return session.selectOne("mapper.approval.apprInfo",appr_seq);
	}
}
