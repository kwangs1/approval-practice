package com.example.kwangs.dept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kwangs.dept.service.deptService;

@Controller
@RequestMapping("/dept")
public class deptController {

	@Autowired
	private deptService service;
}
