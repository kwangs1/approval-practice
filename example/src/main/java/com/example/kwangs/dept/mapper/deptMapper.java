package com.example.kwangs.dept.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class deptMapper {

	@Autowired
	private SqlSession session;
	
	
}
