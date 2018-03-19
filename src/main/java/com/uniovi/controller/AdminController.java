package com.uniovi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.AdminService;
import com.uniovi.services.UserService;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/debug/list")
	public String debugListUsers(Pageable pageable, Model model,
			@RequestParam(value = "", required = false) String searchText) {
		Page<User> users = userService.getUsers(pageable);
		if (searchText != null && !searchText.isEmpty()) {
			users = userService.searchUserByEmailAndName(pageable, searchText);
			model.addAttribute("usersList", users.getContent());
		} else {
			model.addAttribute("usersList", users.getContent());
		}

		model.addAttribute("page", users);
		return "debug/list";
	}

	@RequestMapping(value = { "/debug/deleteAllUsers" })
	public String deleteAllUsers(Model model) {
		adminService.deleteAllUsers();
		return "redirect:/home";
	}

	@RequestMapping(value = "/debug/deleteUser")
	public String deleteUser(Pageable pageable, Model model, @RequestParam String userBorraEmail) {
		User userborrar = userService.getUserByEmail(userBorraEmail);
		if (userborrar == null) {
			return "redirect:/debug/list";
		} else {
			adminService.deleteUser(userborrar);
			return "redirect:/debug/list";
		}
	}
	
	@RequestMapping(value = "/debug/generateDefault")
	public String generateDefault(Pageable pageable, Model model) {
		adminService.getDefaultUsers();
		return "redirect:/home";
	}

}
