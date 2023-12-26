package com.example.kwangs.receipts.service;

import com.example.kwangs.paticipant.domain.paticipantVO;
import com.example.kwangs.receipts.domain.receiptsVO;

public interface receiptsService {

	//void write(receiptsVO vo);

	int write(receiptsVO rvo, paticipantVO pvo);

}
