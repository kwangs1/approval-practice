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
	
	@Override
	public String getLatestReceiptsSeq() {
		return session.selectOne("mapper.approval.getLatestReceiptsSeq");
	}
	
	@Override
	public void apprView(approvalVO approval) {
		  session.insert("mapper.approval.apprView",approval);
	}
	
	@Override
	public List<approvalVO> apprWaitList(String id) {
		return session.selectList("mapper.approval.apprWaitList",id);
	}
	
	@Override
	public approvalVO apprInfo(String appr_seq) {
		return session.selectOne("mapper.approval.apprInfo",appr_seq);
	}
}
