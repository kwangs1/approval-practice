package com.example.kwangs.dept.service;

import java.util.List;

public interface deptService {

	//목록
	List<deptVO> list();
	//부서생성
	void write(deptVO dept);
	//하위부서 생성
	void subDept(deptVO dept);
	//상세보기
	deptVO info(String deptid);
	//회원가입시 불러올 부서목록
	List<deptVO>joinUseDept();
	//결재선 정보 가져올 부서 및 유저목록
	List<deptVO>flowUseInfo();
	//해당 기안자의 부서 발신명의 가져오기
	List<deptVO> getSender(String userid);
}
