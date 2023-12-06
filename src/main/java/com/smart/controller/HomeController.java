package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.repo.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/home")
	public String Home(Model model) {
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "Home";
	}
	
	@RequestMapping("/about")
	public String About(Model model) {
		model.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}
}
