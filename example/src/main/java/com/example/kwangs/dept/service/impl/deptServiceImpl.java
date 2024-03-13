package com.example.kwangs.dept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.dept.mapper.deptMapper;
import com.example.kwangs.dept.service.deptService;
import com.example.kwangs.dept.service.deptVO;
import com.example.kwangs.user.service.userVO;

@Service
public class deptServiceImpl implements deptService{

	@Autowired
	private deptMapper mapper;
	
	//목록
	@Override
	public List<deptVO> list() {
		return mapper.list();
	}
	//부서생성
	@Override
	public void write(deptVO dept) {
		mapper.write(dept);
	}
	//하위부서 생성
	@Override
	public void subDept(deptVO dept) {
		mapper.subDept(dept);
	}
	//상세보기
	@Override
	public deptVO info(String deptid) {
		return mapper.info(deptid);
	}
	//회원가입시 불러올 부서목록
	@Override
	public List<deptVO> joinUseDept(){
		return mapper.joinUseDept();
	}	
	//결재선 정보 가져올 부서 및 유저목록
	@Override
	public List<deptVO>flowUseInfo(){
		List<deptVO> flowUseInfo = mapper.flowUseInfo();
		
		for(deptVO dept : flowUseInfo) {
			List<userVO> users = mapper.userList(dept.getDeptid());
			dept.setUsers(users);
		}
		return flowUseInfo;
	}
}
