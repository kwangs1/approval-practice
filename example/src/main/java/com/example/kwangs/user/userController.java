package com.example.kwangs.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.kwangs.user.service.userService;
import com.example.kwangs.user.service.userVO;

@Controller
@RequestMapping("/user")
public class userController {
	
	@Autowired
	private userService service;
	
	@GetMapping("/write")
	public void write() {
		
	}
	
	@PostMapping("/write")
	public String write(userVO user) {
		service.write(user);
		
		return "redirect:/";
	}
	
	@ResponseBody
	@PostMapping("/idcheck")
	public String idcheck(String id) {
		int result = service.idcheck(id);
		
		if(result != 0) {
			return "fail";
		}else {
			return "success";
		}
	}
	
	@GetMapping("/login")
	public void login() {
		
	}
	@PostMapping("/login")
	public String login(userVO user, HttpServletRequest request, RedirectAttributes rttr) {
		HttpSession session = request.getSession();
		userVO currUser = service.login(user);

		if(currUser != null) {
			session.setAttribute("user", currUser);
			session.setAttribute("userId", currUser.getId());
			return "redirect:/";
		}else {
			rttr.addFlashAttribute("result",0);
			return "redirect:/user/login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/";
				
	}
	
	@GetMapping("/list")
	public void list(Model model) {
		model.addAttribute("user",service.list());
	}
}
