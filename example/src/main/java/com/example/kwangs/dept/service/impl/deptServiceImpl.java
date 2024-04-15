package com.example.kwangs.dept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.dept.mapper.deptMapper;
import com.example.kwangs.dept.service.deptService;
import com.example.kwangs.dept.service.deptVO;
import com.example.kwangs.folder.mapper.folderMapper;
import com.example.kwangs.folder.service.folderVO;
import com.example.kwangs.user.service.userVO;

@Service
public class deptServiceImpl implements deptService{

	@Autowired
	private deptMapper mapper;
	@Autowired
	private folderMapper fdMapper;
	
	//목록
	@Override
	public List<deptVO> list() {
		return mapper.list();
	}
	//부서생성
	@Override
	public void write(deptVO dept) {
		mapper.write(dept);
		CreateDeptCommonFolder(dept);
	}
	//하위부서 생성
	@Override
	public void subDept(deptVO dept) {
		mapper.subDept(dept);
		CreateDeptCommonFolder(dept);
	}
	public void CreateDeptCommonFolder(deptVO dept) {
		folderVO fd_8000 = new folderVO();
		fd_8000.setFldrname("대장");
		fd_8000.setOwnertype("1");
		fd_8000.setAppltype("1");
		fd_8000.setOwnerid(dept.getDeptid());
		fd_8000.setApplid(8000);
		fd_8000.setYear("0000");
		fd_8000.setEndyear("9999");
		fdMapper.CreateDeptCommonFolder(fd_8000);
	
		folderVO fd_7000 = new folderVO();
		fd_7000.setFldrname("단위과제");
		fd_7000.setOwnertype("1");
		fd_7000.setAppltype("3");
		fd_7000.setOwnerid(dept.getDeptid());
		fd_7000.setApplid(7000);
		fd_7000.setYear("0000");
		fd_7000.setEndyear("9999");
		fdMapper.CreateDeptCommonFolder(fd_7000);
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
