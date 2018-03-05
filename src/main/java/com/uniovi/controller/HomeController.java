package com.uniovi.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String index(Principal principal, Model model) {
//		String dni = principal.getName();
//		User user = usersService.getUserByDni(dni);
//		model.addAttribute("user", user);
		return "index";
	}
}
