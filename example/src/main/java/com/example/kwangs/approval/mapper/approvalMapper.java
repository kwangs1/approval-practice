package com.example.kwangs.approval.mapper;

import java.util.List;

import com.example.kwangs.approval.domain.approvalVO;

public interface approvalMapper {
	
	void apprView(approvalVO approval);
	
	String getLatestReceiptsSeq();

	List<approvalVO> list();

}
