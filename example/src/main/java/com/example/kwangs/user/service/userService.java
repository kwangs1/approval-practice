package com.example.kwangs.user.service;

import java.util.List;

public interface userService {

	void write(userVO user);

	int idcheck(String id);

	userVO login(userVO user);

	List<userVO> DeptUseInfo(String deptid);
}
