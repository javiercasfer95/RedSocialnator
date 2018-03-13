package com.uniovi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.repositories.PeticionAmistadRepository;
import com.uniovi.services.RoleService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UserService;
import com.uniovi.validator.SignUpFromValidator;

@Controller
public class UserController {

	/*
	 * 
	 * COSAS QUE FALTAN
	 * 
	 * Que en el login se diga porque ha fallado cuando no se ha podido iniciar
	 * sesion. Puede ser por que el correo no exista o porque la contraseña este
	 * mal. En la teoria tema 3 y pag 48 hay una posible solucion
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	@Autowired
	UserService userService;

	@Autowired
	private SignUpFromValidator signUpFormValidator;

//	@Autowired
//	private LogInFormValidator logInFormValidator;

	@Autowired
	SecurityService securityService;

	@Autowired
	PeticionAmistadRepository peticionAmistadRepository;
	
	@Autowired
	RoleService roleService;

	@RequestMapping("/user/list")
	public String getListadoUsuarios(Pageable pageable, Principal principal, Model model,
			@RequestParam(value = "", required = false) String searchText) {

		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		model.addAttribute("user", user);
		Page<User> users = userService.getUsers(pageable);

		if (searchText != null && !searchText.isEmpty()) {

			users = userService.searchUserByEmailAndName(pageable, searchText);
			model.addAttribute("usersList", users.getContent());
			model.addAttribute("page", users);
		} else {

			model.addAttribute("usersList", users.getContent());
			model.addAttribute("page", users);
		}

		return "user/list";
	}

	/**
	 * Es importante enviar obtener un user vacio de tipo get para que el validador
	 * no pete la primera vez
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	// LOG IN
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result, Model model) {

		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(roleService.getRoles()[0]);

		userService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm()); // Aqui es donde hago el autologin
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	/**
	 * Log in con la comprobacion de lo que esta bien y de lo que esta mal
	 * 
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Validated User user, BindingResult result, Model model) {
		// logInFormValidator.validate(user, result);
		// if (result.hasErrors()) {
		// return "login";
		// }
		//
		// securityService.autoLogin(user.getEmail(), user.getPassword());
		return "home";
	}

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		userService.deleteUser(id);
		return "redirect:/debug/list";
	}

	// @RequestMapping(value = "/debug/list")
	// public String debugListUsers(Model model, Pageable pageable) {
	// Page<User> users = userService.getUsers(pageable);
	// model.addAttribute("usersList", users.getContent());
	// model.addAttribute("page", users);
	// return "login";
	// }

	// DEBUG

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

	@RequestMapping(value = { "/deleteAllUsers" })
	public String deleteAllUsers(Model model) {
		//peticionAmistadRepository.deleteAll();
		userService.deleteAllUsers();
		return "redirect:home";
	}

}
