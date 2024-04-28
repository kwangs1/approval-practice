package com.example.kwangs.common.file.service;

import java.util.List;
import java.util.Map;

public interface fileService {

	List<AttachVO> getAttachList(String appr_seq);
	
	void ApprDocDeleteFiles(Map<String,Object>res);
	
	List<AttachVO> AttachModifyForm(String appr_seq);
}
