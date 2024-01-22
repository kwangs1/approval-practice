package com.example.kwangs.approval.mapper;

import com.example.kwangs.approval.domain.approvalVO;

public interface approvalMapper {
	
	void apprView(approvalVO approval);
	
	String getLatestReceiptsSeq();

}
