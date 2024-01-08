package com.example.kwangs.receipts.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kwangs.paticipant.domain.paticipantVO;
import com.example.kwangs.paticipant.mapper.paticipantMapper;
import com.example.kwangs.receipts.domain.receiptsVO;
import com.example.kwangs.receipts.mapper.receiptsMapper;

@Service
public class receiptsServiceImpl implements receiptsService{
	private static Logger log = Logger.getLogger(receiptsService.class.getName());
	@Autowired
	private receiptsMapper mapper;
	@Autowired
	private paticipantMapper paticipantMapper;

	@Override
	@Transactional
	public int apprView(receiptsVO receipts) {
		int result = mapper.apprView(receipts);
		log.info("결재 시퀀스 먼저 따지나?? "+receipts.getReceipts_seq());
		return result;
		
	}
	/*
	 * 현재  번호만 생성되는 시퀀스를 트리거를 통해 문자열을 앞에 붙여 id값을 생성
	 * 그렇기에 그냥 시퀀스를 가져오면 번호만 가져오기때문에 먼저 번호를 받고, 이후
	 * 쿼리를 통해 시퀀스 번호 앞 문자열+날짜+상신 시 따지는 결재시퀀스[receipts_seq] 번호를 가져옴
	 * 이후 결재선 insert 시 receipts_seq값을 상신 시 따지는 번호를 결재선 테이블에 넣어줌
	 */
	@Override
	@Transactional
	public int write(receiptsVO receipts, List<paticipantVO> paticipant) {
		//int result =  mapper.write(receipts);
		int result = apprView(receipts);
		//receipts_seq값에 따라 결재선에 들어간 각 사용자의 순서 초기값 변수
		int line_seq = 1; 
		
		if(result == 1) {
			String origin_seq = receipts.getReceipts_seq();
			log.info("결재 시퀀스 트리거 이전...{}"+origin_seq);

			String new_seq = mapper.getLatestReceiptsSeq();
			log.info("트리거 이후 결재 시퀀스..{}"+new_seq);
						
			for(paticipantVO pVO : paticipant) {	
				pVO.setReceipts_seq(new_seq);
				log.info("결재선 결재시퀀스...{}"+pVO.getReceipts_seq());
				pVO.setLine_seq(line_seq);//기본값 1
				
				//이후 insert 된 receipts_seq 값 가져올 것.
				paticipantMapper.ParticipantWrite(pVO);
				line_seq++;//receitps_seq 별 사용자 번호 순차 증가
			}
		}
		return result;
	}

}
