package com.example.kwangs.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kwangs.folder.service.IfolderS;
import com.example.kwangs.folder.service.folderVO;

@Controller
@RequestMapping("/folder")
public class folderC {

	@Autowired
	private IfolderS service;
	
	@GetMapping("/deptAllFolderAdd")
	public void deptAllFolderAdd() {}
	
	@ResponseBody
	@PostMapping("/deptAllFolderAdd")
	public void deptAllFolderAdd(folderVO fd) {
		service.deptAllFolderAdd(fd);
	}
}
