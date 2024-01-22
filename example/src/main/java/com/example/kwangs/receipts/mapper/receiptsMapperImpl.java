package com.example.kwangs.receipts.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.receipts.domain.receiptsVO;

@Repository
public class receiptsMapperImpl implements receiptsMapper{
	@Autowired
	private SqlSession session;
	
	@Override
	public String getLatestReceiptsSeq() {
		return session.selectOne("mapper.receipts.getLatestReceiptsSeq");
	}
	
	@Override
	public void apprView(receiptsVO receipts) {
		  session.insert("mapper.receipts.apprView",receipts);
	}
}
