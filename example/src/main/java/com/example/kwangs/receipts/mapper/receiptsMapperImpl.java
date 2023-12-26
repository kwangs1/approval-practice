package com.example.kwangs.receipts.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.receipts.domain.receiptsVO;

@Repository
public class receiptsMapperImpl implements receiptsMapper{
	@Autowired
	private SqlSession session;
	
	@Override
	public int write(receiptsVO vo) {
		return session.insert("mapper.receipts.write",vo);
	}
	
	@Override
	public String getLatestReceiptsSeq() {
		return session.selectOne("mapper.receipts.getLatestReceiptsSeq");
	}
}
