package com.example.kwangs.apprfolder.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.kwangs.apprfolder.service.apprfolderVO;

@Repository
public class apprfolderMapper {

	@Autowired
	private SqlSession session;
	
	//기안 시 기록물철 가져와서 집어넣기
	public List<apprfolderVO> DeptApprFolderList(String procdeptid){
		return session.selectList("apprfolder.DeptApprFolderList",procdeptid);
	}
	
	//작성
	public void write(apprfolderVO af) {
		session.insert("apprfolder.write",af);
	}
}
