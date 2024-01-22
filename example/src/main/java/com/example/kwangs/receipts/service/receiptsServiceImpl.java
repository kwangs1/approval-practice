package com.example.kwangs.receipts.service;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kwangs.paticipant.domain.paticipantVO;
import com.example.kwangs.paticipant.mapper.paticipantMapper;
import com.example.kwangs.receipts.domain.receiptsVO;
import com.example.kwangs.receipts.mapper.receiptsMapper;


@Service
public class receiptsServiceImpl implements receiptsService{
	private final Logger log = LoggerFactory.getLogger(receiptsServiceImpl.class);

	@Autowired
	private receiptsMapper mapper;
	@Autowired
	private paticipantMapper paticipantMapper;
	
	@Override
	public void apprView(receiptsVO receipts) {
		mapper.apprView(receipts);
		log.debug(receipts.getReceipts_seq());
	}
	
	@Override
	public void write(List<paticipantVO> paticipant){
		log.info("write method 진입");
		int line_seq = 1;
		
		String seqCurrval = mapper.getLatestReceiptsSeq();
		log.debug("write seqValue..{}" + seqCurrval);
		
		for (paticipantVO pVO : paticipant) {
			pVO.setReceipts_seq(seqCurrval);
			log.debug("new_seq getSeqValue...{}" + pVO.getReceipts_seq());
			pVO.setLine_seq(line_seq);// 기본값 1

			// 이후 insert 된 receipts_seq 값 가져올 것.
			paticipantMapper.ParticipantWrite(pVO);
			line_seq++;// receitps_seq 별 사용자 번호 순차 증가
		}
	}
	
	@Override
	@Transactional
	public void ApprovlTransanctional(receiptsVO receipts, List<paticipantVO> paticipant) {
	    apprView(receipts);
	    write(paticipant);
	}


}
