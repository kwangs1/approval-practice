package com.example.kwangs.participant.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.participant.service.participantVO;

@Repository
public class participantMapper{
	private Logger log = LoggerFactory.getLogger(participantMapper.class.getName());
	
	@Autowired
	private SqlSession session;
	
	//결재 상신 전 결재선 지정
	public void ParticipantWrite(participantVO vo) {
		session.insert("mapper.participant.ParticipantWrite",vo);
	}
	
	//일괄 결재 
	public void  BulkAppr(Map<String, Object> params) {
		log.info("dao{} :"+params);
		session.update("mapper.participant.BulkAppr",params);
	}
	
	//일괄결재 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	public List<participantVO> ApprWaitFLowInfo(String appr_seq) {
		return session.selectList("mapper.participant.ApprWaitFLowInfo",appr_seq);
	}
	
	//회수 시 결재선 정보 가져오기 위한 해당 문서의 결재선 정보 가져오는 부분
	public List<participantVO> ApprProgrsFLowInfo(String appr_seq) {
		return session.selectList("mapper.participant.ApprProgrsFLowInfo",appr_seq);
	}
	
	//결재 시 결재자들의 타입 값 변경
	public void updateNextApprovalType(Map<String, Object> params) {
		log.info("Length value..{} :" + params);
		session.update("mapper.participant.updateNextApprovalType",params);
	}
	
	//일괄 결재 시 해당 결재문서의 결재id 가져오기 위함
	public List<participantVO>getApprovalApprseq(String appr_seq){
		return session.selectList("mapper.participant.getApprovalApprseq",appr_seq);
	}
	
	//결재
	public void FlowAppr(Map<String,Object> res) {
		log.info("Mapper FlowAppr RecData {}"+res);
		session.update("mapper.participant.FlowAppr",res);
	}

	//일반 결재 시 상세보기에서의 결재선 정보 
	public participantVO pInfo(Map<String,Object> res) {
		return session.selectOne("mapper.participant.pInfo",res);
	}
	
	//기안자가 최종결재자인 경우 결재선상태 및 결재문서 상태 업데이트
	public void updateFLowType(Map<String,Object>res) {
		session.update("mapper.participant.updateFLowType",res);
	}
	
	//회수
	public void RetireAppr(Map<String,Object> res) {
		session.update("mapper.participant.RetireAppr",res);
	}
	
	//재기안 시 해당 문서에 대한 결재자 정보 가져오기
	public List<participantVO> getRe_pInfo(String appr_seq){
		return session.selectList("mapper.participant.getRe_pInfo",appr_seq);
	}
	
	//재기안 시 결재자 상태값 업데이트
	public int ResubmissionFlowStatusUpd(participantVO pp){
		return session.update("mapper.participant.ResubmissionFlowStatusUpd",pp);
	}
	
	//재기안 시 새로운 결재선 추가에 대한 line_seq 마지막 값 가져오기
	public int getLastSeq(String appr_seq) {
		return session.selectOne("mapper.participant.getLastSeq",appr_seq);
	}
	
	//재기안 시 새로운 결재선 추가
	public void ResubmissionParticipantWrite(participantVO participant) {
		session.insert("mapper.participant.ResubmissionParticipantWrite",participant);
	}
	
	//중간 결재자 결재 차례 시 중간결재자에 대한 결재대기 & 결재진행 
	public List<participantVO> ApprFlowLines(String signerid){
		return session.selectList("mapper.participant.ApprFlowLines",signerid);
	}
}
