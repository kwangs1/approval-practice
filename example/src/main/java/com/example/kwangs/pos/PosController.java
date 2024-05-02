package com.example.kwangs.pos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kwangs.pos.service.posService;
import com.example.kwangs.pos.service.posVO;

@Controller
@RequestMapping("/pos")
public class PosController {

	@Autowired
	private posService service;
	
	@GetMapping("/write")
	public void write() {}
	
	@PostMapping("/write")
	public void write(posVO pos) {
		service.write(pos);
	}
	
	@GetMapping("/list")
	public void list(Model model) {
		model.addAttribute("list",service.list());
	}
	
}
