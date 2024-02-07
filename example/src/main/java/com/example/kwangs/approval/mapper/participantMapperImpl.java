package com.example.kwangs.approval.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.approval.domain.participantVO;

@Repository
public class participantMapperImpl implements participantMapper{

	@Autowired
	private SqlSession session;
	
	@Override
	public void ParticipantWrite(participantVO vo) {
		session.insert("mapper.participant.ParticipantWrite",vo);
	}
	

	@Override
	public void participantCheck(participantVO participant) {
		session.update("mapper.participant.participantCheck",participant);
	}
}
