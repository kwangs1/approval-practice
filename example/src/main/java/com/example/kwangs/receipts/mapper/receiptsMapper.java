package com.example.kwangs.receipts.mapper;

import com.example.kwangs.receipts.domain.receiptsVO;

public interface receiptsMapper {

	int write(receiptsVO vo);

	String getLatestReceiptsSeq();

}
