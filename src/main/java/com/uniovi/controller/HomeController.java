package com.uniovi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String home(Pageable pageable, Principal principal, Model model) {
//		String email = principal.getName();
//		User user = userService.getUserByEmail(email);
//		model.addAttribute("user", user);
//		Page<User> userList = userService.getNotAdminUsers(pageable);
//		model.addAttribute("usersList", userList.getContent());
//		model.addAttribute("page", userList);
//		return "user/list";
		return "redirect:/user/list";
	}
}
