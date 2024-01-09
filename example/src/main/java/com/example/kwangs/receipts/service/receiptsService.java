package com.example.kwangs.receipts.service;

import java.util.List;

import com.example.kwangs.paticipant.domain.paticipantVO;
import com.example.kwangs.receipts.domain.receiptsVO;

public interface receiptsService {

	void write(List<paticipantVO> paticipant);

	int apprView(receiptsVO receipts);

}
