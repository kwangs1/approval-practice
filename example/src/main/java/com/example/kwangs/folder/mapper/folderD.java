package com.example.kwangs.folder.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.folder.service.folderVO;

@Repository
public class folderD {

	@Autowired
	private SqlSession session;
	
	public void deptAllFolderAdd(folderVO fd) {
		session.insert("folder.deptAllFolderAdd",fd);
	}
	
	public void CreateDeptCommonFolder(folderVO fd) {
		session.insert("folder.CreateDeptCommonFolder",fd);
	}
}
