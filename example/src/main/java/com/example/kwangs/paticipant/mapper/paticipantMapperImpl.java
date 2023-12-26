package com.example.kwangs.paticipant.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.paticipant.domain.paticipantVO;

@Repository
public class paticipantMapperImpl implements paticipantMapper{

	@Autowired
	private SqlSession session;
	
	@Override
	public void ParticipantWrite(paticipantVO vo) {
		session.insert("mapper.paticipant.ParticipantWrite",vo);
	}
}
