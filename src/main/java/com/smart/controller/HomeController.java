package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.repo.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String Home(Model model) {
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "Home";
	}

	@RequestMapping("/about")
	public String About(Model model) {
		model.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}

	@RequestMapping("/signup")
	public String SignUp(Model model) {
		model.addAttribute("title", "SignUp - Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}

	// method for handling user registration
	@PostMapping("/do_register")
	public String registerUser(User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			HttpSession session, Model model) {
		try {
			if (!agreement) {
				System.out.println("You have not agreed terms and condition");
				throw new Exception("You have not agreed terms and condition");
			}
			System.out.println(agreement);
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
			userRepository.save(user);
			return "signup";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong" + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}
}
