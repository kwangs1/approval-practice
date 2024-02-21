package com.example.kwangs.approval.mapper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.approval.domain.participantVO;
import com.example.kwangs.approval.mapper.participantMapper;

@Repository
public class participantMapperImpl implements participantMapper{
	private Logger log = LoggerFactory.getLogger(participantMapperImpl.class.getName());
	
	@Autowired
	private SqlSession session;
	
	@Override
	public void ParticipantWrite(participantVO vo) {
		session.insert("mapper.participant.ParticipantWrite",vo);
	}
	
	@Override
	public void  participantCheck(Map<String, Object> params) {
		log.info("participant check dao ... in");
		log.info("dao{} :"+params);
		session.update("mapper.participant.participantCheck",params);
	}
	
	@Override
	public List<participantVO> getParticipantInfo(String appr_seq) {
		return session.selectList("mapper.participant.getParticipantInfo",appr_seq);
	}
}
