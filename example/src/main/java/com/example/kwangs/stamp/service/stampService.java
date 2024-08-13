package com.example.kwangs.stamp.service;

import java.util.List;
import java.util.Map;

public interface stampService{

	public List<stampVO> list();
	
	public void write(stampVO stamp);
	
	public List<stampVO> getAttachList();
	
	public void StampDeleteFiles(Map<String, Object> res);
	
	public List<stampVO> getFlowDeptStampList(String appr_seq);
}
