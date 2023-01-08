package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entity.User;
import com.smart.helper.Massage;
import com.smart.service.UserService;

@Controller
public class MyController {
	@Autowired
	private UserService service;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String home_handler(Model model) {
		model.addAttribute("title", "Home-SmartManager");
		return "home";
	}

	@GetMapping("/about")
	public String about_handler(Model model) {
		model.addAttribute("title", "About-SmartManager");
		return "about";
	}

	@GetMapping("/signup")
	public String sign_handler(Model model) {
		model.addAttribute("title", "Sign-SmartManager");
//    	User user = new User();
		model.addAttribute("user", new User());
		return "signup";
	}

	// Registration Handler
	@PostMapping("/register")
	public String register_handler(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam(value = "terms", defaultValue = "false") boolean terms, Model model, HttpSession session) {
		System.out.println("This is register handler");
		try {
			if (!terms || result.hasErrors()) {
				model.addAttribute("user", user);
				if (!terms)
					session.setAttribute("message",
							new Massage("You have not aggreed terms and conditon", "alert-danger"));
				System.out.println(result);
				return "signup";
			} else {
				user.setIsactive(true);
				user.setRole("ROLE_USER");
				user.setPassword(this.passwordEncoder.encode(user.getPassword()));
				System.out.println("User : " + user);
				User u1 = this.service.insert(user);
				System.out.println(u1);
				session.setAttribute("message", new Massage("You have Registerd Successfully", "alert-success"));
				return "signup";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Massage("Something Went Wrong!! ", "alert-danger"));
			return "signup";
		}
	}

	// Login Handler
	@GetMapping("/login")
	public String login_handler(Model model) {
		model.addAttribute("title", "Login-SmartContactManager");
		return "login";
	}
}
