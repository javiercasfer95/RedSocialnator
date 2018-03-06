package com.uniovi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.User;
import com.uniovi.services.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/")
	public String index(Principal principal, Model model) {
		// String dni = principal.getName();
		// User user = usersService.getUserByDni(dni);
		// model.addAttribute("user", user);
		return "index";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Principal principal, Model model) {
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		model.addAttribute("user", user);
		return "home";
	}
}
