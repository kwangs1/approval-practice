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
	
	//결재 상신 전 결재선 지정
	@Override
	public void ParticipantWrite(participantVO vo) {
		session.insert("mapper.participant.ParticipantWrite",vo);
	}
	
	//일괄 결재 시 결재선 업데이트 
	@Override
	public void  participantCheck(Map<String, Object> params) {
		log.info("participant check dao ... in");
		log.info("dao{} :"+params);
		session.update("mapper.participant.participantCheck",params);
	}
	
	//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	@Override
	public List<participantVO> getParticipantInfo(String appr_seq) {
		return session.selectList("mapper.participant.getParticipantInfo",appr_seq);
	}
	
	@Override
	public void updateNextApprovalType(String appr_seq, String participant_seq) {
		Map<String, Object> params = new HashMap<>();
		params.put("appr_seq", appr_seq);
		params.put("participant_seq", participant_seq);
		
		log.info("Length value..{} :" + params);
		session.update("mapper.participant.updateNextApprovalType",params);
	}
	
	@Override
	public List<participantVO>getApprovalApprseq(String appr_seq){
		return session.selectList("mapper.participant.getApprovalApprseq",appr_seq);
	}
}
