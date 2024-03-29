package com.example.kwangs.pos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.pos.mapper.posMapper;
import com.example.kwangs.pos.service.posService;
import com.example.kwangs.pos.service.posVO;

@Service
public class posServiceimpl implements posService{

	@Autowired
	private posMapper mapper;
	
	@Override
	public void write(posVO pos) {
		mapper.write(pos);
	}
	
	@Override
	public List<posVO> JoinposList() {
		return mapper.JoinposList();
	}
	
	@Override
	public List<posVO> list(){
		return mapper.list();
	}
}
