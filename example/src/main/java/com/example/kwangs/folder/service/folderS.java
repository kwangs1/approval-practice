package com.example.kwangs.folder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.folder.mapper.folderD;

@Service
public class folderS implements IfolderS{

	@Autowired
	private folderD mapper;
	
	
	@Override
	public void deptAllFolderAdd(folderVO fd) {
		mapper.deptAllFolderAdd(fd);
	}
}
