package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.repo.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String Home(Model model) {
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "Home";
	}

	@RequestMapping("git@github.com:Bhupesh96/smartContactManagerUpdated.git/about")
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
	 @RequestMapping(value = "/do_register", method = RequestMethod.POST)
	    public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult,
	                               @RequestParam(value = "agreement", defaultValue = "false") Boolean agreement,
	                               Model model,
	                               HttpSession session) {

	        try {

	            if (!agreement) {
	                System.out.println("You have not agreed the terms & conditions");
	                throw new Exception("You have not agreed the terms & conditions");
	            }
	            if (bindingResult.hasErrors()){
	                model.addAttribute("user", user);
	                return "signUp";
	            }
	            user.setRole("ROLE_USER");
	            user.setEnabled(true);
	            //user.setImageUrl("default.png");
	           // user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	            System.out.println("USER: " + user.toString());
	            System.out.println("AGREEMENT: " + agreement);

	            User savedUser = this.userRepository.save(user);

	            // Show empty user in frontend
	            model.addAttribute("user", new User());

	            session.setAttribute("message",new Message("Successfully Registered!! ", "alert-success"));

	            return "signUp";
	        } catch (Exception e) {
	            e.printStackTrace();
	            model.addAttribute("user", user);
	            session.setAttribute(
	                    "message",
	                    new Message("Something went wrong !! " + e.getMessage(), "alert-danger")
	            );
	            return "signUp";
	        }
	}
}
