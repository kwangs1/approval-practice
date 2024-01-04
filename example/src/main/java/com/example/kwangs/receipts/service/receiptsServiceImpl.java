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
	public int write(receiptsVO receipts, List<paticipantVO> paticipant) {
		int result = mapper.write(receipts);
		int line_seq = 1;
		
		if(result == 1) {
			String origin_seq = receipts.getReceipts_seq();
			log.info("결재 시퀀스 트리거 이전...{}"+origin_seq);
			String new_seq = mapper.getLatestReceiptsSeq();
			log.info("트리거 이후 결재 시퀀스..{}"+new_seq);
			
			for(paticipantVO pVO : paticipant) {	
				pVO.setReceipts_seq(new_seq);
				log.info("결재선 결재시퀀스...{}"+pVO.getReceipts_seq());
				pVO.setLine_seq(line_seq);
				//이후 insert 된 receipts_seq 값 가져올 것.
				paticipantMapper.ParticipantWrite(pVO);
				line_seq++;
			}
		}
		return result;
	}

}
