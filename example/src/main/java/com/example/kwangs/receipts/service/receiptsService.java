package com.example.kwangs.receipts.service;

import java.util.List;

import com.example.kwangs.paticipant.domain.paticipantVO;
import com.example.kwangs.receipts.domain.receiptsVO;

public interface receiptsService {
	//결재화면
	void apprView(receiptsVO receipts);
	//결재선
	void write(List<paticipantVO> paticipant);
	
	void ApprovlTransanctional(receiptsVO receipts, List<paticipantVO> paticipant);

}
