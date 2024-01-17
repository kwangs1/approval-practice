package com.example.kwangs.receipts.mapper;

import com.example.kwangs.receipts.domain.receiptsVO;

public interface receiptsMapper {
	
	void apprView(receiptsVO receipts);
	
	String getLatestReceiptsSeq();

}
