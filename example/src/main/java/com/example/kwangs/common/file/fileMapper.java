package com.example.kwangs.common.file;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class fileMapper {

	@Autowired
	private SqlSession session;
	
	public void DocFileIn(AttachVO attach) {
		session.insert("Attach.DocFileIn",attach);
	}
}
