package com.example.kwangs.user.mapper;

import java.util.List;

import com.example.kwangs.user.domain.userVO;

public interface userMapper {

	void write(userVO user);

	int idcheck(String id);

	userVO login(userVO user);

	List<userVO> list();

}
