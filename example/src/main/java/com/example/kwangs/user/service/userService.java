package com.example.kwangs.user.service;

import com.example.kwangs.user.domain.userVO;

public interface userService {

	void write(userVO user);

	int idcheck(String id);

	userVO login(userVO user);

}
