package com.example.kwangs.bizunit.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface bizunitService {

	void uploadCSV(MultipartFile file);
	
	List<bizunitVO> getbizList();
	
	List<bizunitVO> list();
	
	void write(bizunitVO biz);
}
