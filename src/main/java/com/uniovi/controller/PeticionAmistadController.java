package com.uniovi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.PeticionAmistad;
import com.uniovi.entities.User;
import com.uniovi.services.PeticionAmistadService;
import com.uniovi.services.UserService;

@Controller
public class PeticionAmistadController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PeticionAmistadService peticionAmistadService;
	
	@RequestMapping("/user/peticionesFromUserList")
	public String getListadoPeticionesFrom(Pageable pageable, Principal principal, Model model,
			@RequestParam(value = "", required = false) String searchText) {

		//Comprobar que el usuario ha añadido al otro
		
		
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		model.addAttribute("user", user);
		Page<PeticionAmistad> requests = peticionAmistadService.getAllPeticionesFromUser(pageable);

		if (searchText != null && !searchText.isEmpty()) {
			requests = peticionAmistadService.getAllPeticionesFromUser(pageable, user);
			model.addAttribute("peticionesFromUserList", requests.getContent());
			model.addAttribute("page", requests);
		} else {
			model.addAttribute("peticionesFromUserList", requests.getContent());
			model.addAttribute("page", requests);
		}

		return "user/peticionesFromUserList";
	}
	
	
	@RequestMapping("/user/peticionesToUserList")
	public String getListadoPeticionesTo(Pageable pageable, Principal principal, Model model,
			@RequestParam(value = "", required = false) String searchText) {

		//Comprobar que el usuario ha añadido al otro???
		
		
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		model.addAttribute("user", user);
		Page<PeticionAmistad> requests = peticionAmistadService.getAllPeticionesToUser(pageable);

		if (searchText != null && !searchText.isEmpty()) {
			requests = peticionAmistadService.getAllPeticionesToUser(pageable, user);
			model.addAttribute("peticionesFromUserList", requests.getContent());
			model.addAttribute("page", requests);
		} else {
			model.addAttribute("peticionesFromUserList", requests.getContent());
			model.addAttribute("page", requests);
		}

		return "user/peticionesToUserList";
	}

}
