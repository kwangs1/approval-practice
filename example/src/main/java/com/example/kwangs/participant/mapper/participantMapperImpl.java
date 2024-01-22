package com.example.kwangs.participant.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.participant.domain.participantVO;

@Repository
public class participantMapperImpl implements participantMapper{

	@Autowired
	private SqlSession session;
	
	@Override
	public void ParticipantWrite(participantVO vo) {
		session.insert("mapper.participant.ParticipantWrite",vo);
	}
}
