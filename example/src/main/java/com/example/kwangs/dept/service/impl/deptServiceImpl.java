package com.example.kwangs.dept.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kwangs.dept.mapper.deptMapper;
import com.example.kwangs.dept.service.deptService;

@Service
public class deptServiceImpl implements deptService{

	@Autowired
	private deptMapper mapper;
}
