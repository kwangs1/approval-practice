package com.example.kwangs.pos.service;

import java.util.List;

public interface posService {

	void write(posVO pos);
	
	List<posVO> JoinposList();
	
	List<posVO> list();
}
