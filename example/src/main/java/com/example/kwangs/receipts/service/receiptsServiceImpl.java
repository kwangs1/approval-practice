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
	@Autowired
	private receiptsVO rVO;
	
	@Override
	public int apprView(receiptsVO receipts) {
		log.info("Before apprView - isViewed: " + receipts.isViewed());
        if (!receipts.isViewed()) {
            int result = mapper.apprView(receipts);
            log.debug("apprView insert..{} " + receipts.getReceipts_seq());
            receipts.setViewed(false);
            log.debug("After apprView - isViewed: " + receipts.isViewed());
            return result;
        } else {
            log.debug("After apprView (BOOM) - isViewed: " + receipts.isViewed());
            return 1;
        }
	}
	
    @Override
    public void write(List<paticipantVO> paticipant) {
    	//rVO.setViewed(true);
    	log.info("Before write - isViewed: " + rVO.isViewed());
        int line_seq = 1;
        
        if (rVO.isViewed() == false) {
            String new_seq = mapper.getLatestReceiptsSeq();
            log.debug("trigger seqValue..{}" + new_seq);
	        for (paticipantVO pVO : paticipant) {
	            pVO.setReceipts_seq(new_seq);
	            log.debug("new_seq getSeqValue...{}" + pVO.getReceipts_seq());
	            pVO.setLine_seq(line_seq);// 기본값 1
	
	            // 이후 insert 된 receipts_seq 값 가져올 것.
	            paticipantMapper.ParticipantWrite(pVO);
	            line_seq++;// receitps_seq 별 사용자 번호 순차 증가
	        }
	        rVO.setViewed(false);
        }
        log.info("After write - isViewed: " + rVO.isViewed());
    }

}
