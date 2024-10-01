package com.example.kwangs.user.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.folder.mapper.folderMapper;
import com.example.kwangs.folder.service.folderVO;
import com.example.kwangs.user.mapper.userMapper;
import com.example.kwangs.user.service.userService;
import com.example.kwangs.user.service.userVO;

@Service
public class userServiceImpl implements userService{
	private static Logger log = LoggerFactory.getLogger(userService.class);
	@Autowired
	private userMapper mapper;
	@Autowired
	private folderMapper fMapper;
	
	@Override
	public void write(userVO user) {
		mapper.write(user);
		JoinUseFolder(user);
	}
	
	@Override
	public int idcheck(String id) {
		return mapper.idcheck(id);
	}
	
	@Override
	public userVO login(userVO user) {
		log.debug("login success={}",user);
		return mapper.login(user);
	}
	
	@Override
	public List<userVO> DeptUseInfo(String deptid){
		return mapper.DeptUseInfo(deptid);
	}
	
	public void JoinUseFolder(userVO user) {
		//결재
		folderVO in1000 = new folderVO();
		in1000.setFldrname("결재");
		in1000.setOwnertype("2");
		in1000.setOwnerid(user.getId());
		in1000.setAppltype("2");
		in1000.setApplid(1000);
		in1000.setYear("0000");
		in1000.setEndyear("9999");
		fMapper.FolderAdd(in1000);
		
		folderVO in2010 = new folderVO();
		in2010.setFldrname("결재대기");
		in2010.setParfldrid(in1000.getFldrid());
		in2010.setParfldrname(in1000.getFldrname());
		in2010.setFldrdepth(in1000.getFldrdepth()+1);
		in2010.setOwnertype("2");
		in2010.setOwnerid(user.getId());
		in2010.setAppltype("2");
		in2010.setApplid(2010);
		in2010.setYear("0000");
		in2010.setEndyear("9999");
		fMapper.subFolderAdd(in2010);
		
		folderVO in2020 = new folderVO();
		in2020.setFldrname("결재진행");
		in2020.setParfldrid(in1000.getFldrid());
		in2020.setParfldrname(in1000.getFldrname());
		in2020.setFldrdepth(in1000.getFldrdepth()+1);
		in2020.setOwnertype("2");
		in2020.setOwnerid(user.getId());
		in2020.setAppltype("2");
		in2020.setApplid(2020);
		in2020.setYear("0000");
		in2020.setEndyear("9999");
		fMapper.subFolderAdd(in2020);
		
		folderVO in6021 = new folderVO();
		in6021.setFldrname("기안한문서");
		in6021.setParfldrid(in1000.getFldrid());
		in6021.setParfldrname(in1000.getFldrname());
		in6021.setFldrdepth(in1000.getFldrdepth()+1);
		in6021.setOwnertype("2");
		in6021.setOwnerid(user.getId());
		in6021.setAppltype("2");
		in6021.setApplid(6021);
		in6021.setYear("0000");
		in6021.setEndyear("9999");
		fMapper.subFolderAdd(in6021);
		
		folderVO in6022 = new folderVO();
		in6022.setFldrname("결재한문서");
		in6022.setParfldrid(in1000.getFldrid());
		in6022.setParfldrname(in1000.getFldrname());
		in6022.setFldrdepth(in1000.getFldrdepth()+1);
		in6022.setOwnertype("2");
		in6022.setOwnerid(user.getId());
		in6022.setAppltype("2");
		in6022.setApplid(6022);
		in6022.setYear("0000");
		in6022.setEndyear("9999");
		fMapper.subFolderAdd(in6022);
		
		folderVO in6050 = new folderVO();
		in6050.setFldrname("접수한문서");
		in6050.setParfldrid(in1000.getFldrid());
		in6050.setParfldrname(in1000.getFldrname());
		in6050.setFldrdepth(in1000.getFldrdepth()+1);
		in6050.setOwnertype("2");
		in6050.setOwnerid(user.getId());
		in6050.setAppltype("2");
		in6050.setApplid(6050);
		in6050.setYear("0000");
		in6050.setEndyear("9999");
		fMapper.subFolderAdd(in6050);
		
		//발송
		folderVO in4000 = new folderVO();
		in4000.setFldrname("발송");
		in4000.setOwnertype("2");
		in4000.setOwnerid(user.getId());
		in4000.setAppltype("2");
		in4000.setApplid(4000);
		in4000.setYear("0000");
		in4000.setEndyear("9999");
		fMapper.FolderAdd(in4000);
		
		folderVO in4030 = new folderVO();
		in4030.setFldrname("발송대기");
		in4030.setParfldrid(in4000.getFldrid());
		in4030.setFldrdepth(in4000.getFldrdepth()+1);
		in4030.setOwnertype("2");
		in4030.setOwnerid(user.getId());
		in4030.setAppltype("2");
		in4030.setApplid(4030);
		in4030.setYear("0000");
		in4030.setEndyear("9999");
		in4030.setParfldrname(in4000.getFldrname());
		fMapper.subFolderAdd(in4030);
		
		folderVO in4050 = new folderVO();
		in4050.setFldrname("발송현황");
		in4050.setParfldrid(in4000.getFldrid());
		in4050.setFldrdepth(in4000.getFldrdepth()+1);
		in4050.setOwnertype("2");
		in4050.setOwnerid(user.getId());
		in4050.setAppltype("2");
		in4050.setApplid(4050);
		in4050.setYear("0000");
		in4050.setEndyear("9999");
		in4050.setParfldrname(in4000.getFldrname());
		fMapper.subFolderAdd(in4050);
		
		//접수
		folderVO in7010 = new folderVO();
		in7010.setFldrname("접수");
		in7010.setOwnertype("2");
		in7010.setOwnerid(user.getId());
		in7010.setAppltype("2");
		in7010.setApplid(7010);
		in7010.setYear("0000");
		in7010.setEndyear("9999");
		fMapper.FolderAdd(in7010);
		
		folderVO in5010 = new folderVO();
		in5010.setFldrname("접수대기");
		in5010.setParfldrid(in7010.getFldrid());
		in5010.setFldrdepth(in7010.getFldrdepth()+1);
		in5010.setOwnertype("2");
		in5010.setOwnerid(user.getId());
		in5010.setAppltype("2");
		in5010.setApplid(5010);
		in5010.setYear("0000");
		in5010.setEndyear("9999");
		in5010.setParfldrname(in7010.getFldrname());
		fMapper.subFolderAdd(in5010);
		
		folderVO in5020 = new folderVO();
		in5020.setFldrname("수신반송");
		in5020.setParfldrid(in7010.getFldrid());
		in5020.setFldrdepth(in7010.getFldrdepth()+1);
		in5020.setOwnertype("2");
		in5020.setOwnerid(user.getId());
		in5020.setAppltype("2");
		in5020.setApplid(5020);
		in5020.setYear("0000");
		in5020.setEndyear("9999");
		in5020.setParfldrname(in7010.getFldrname());
		fMapper.subFolderAdd(in5020);
	}
	
}
