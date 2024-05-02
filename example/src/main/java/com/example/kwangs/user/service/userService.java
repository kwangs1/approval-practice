package com.example.kwangs.user.service;

public interface userService {

	void write(userVO user);

	int idcheck(String id);

	userVO login(userVO user);

}
