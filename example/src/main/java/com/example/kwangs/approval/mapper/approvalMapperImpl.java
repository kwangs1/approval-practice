package com.example.kwangs.approval.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.approval.domain.approvalVO;

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
	public List<approvalVO> list() {
		return session.selectList("mapper.approval.list");
	}
}
