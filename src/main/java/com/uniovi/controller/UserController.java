package com.uniovi.controller;

import java.security.Principal;
import java.util.List;

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

    @Autowired
    UserService userService;

    @Autowired
    private SignUpFromValidator signUpFormValidator;

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
	if (user.getRole().equals(roleService.getAdminRole())) {
	    return "redirect:/debug/list";
	}
	model.addAttribute("user", user);
	Page<User> users = userService.getUsers(pageable);
	List<String> emailUsuarioConRelaciones = userService.getUsuariosConRelaciones(user);

	if (searchText != null && !searchText.isEmpty()) {

	    users = userService.searchUserByEmailAndName(pageable, searchText);
	    model.addAttribute("usersList", users.getContent());
	    model.addAttribute("page", users);

	} else {

	    model.addAttribute("usersList", users.getContent());
	    model.addAttribute("page", users);
	}

	model.addAttribute("usuariosConRelaciones", emailUsuarioConRelaciones);

	return "user/list";
    }

    @RequestMapping("/user/myFriends")
    public String getListadoAmigos(Pageable pageable, Principal principal, Model model) {

	String email = principal.getName();
	User user = userService.getUserByEmail(email);
	if (user.getRole().equals(roleService.getAdminRole())) {
	    return "redirect:/debug/list";
	}
	Page<User> users = userService.getAllAmigos(pageable, user);
	model.addAttribute("amigosList", users.getContent());
	model.addAttribute("page", users);
	return "user/myFriends";
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
    public String login(Model model, @RequestParam(value = "error", required = false) Long error) {
	if (error != null)
	    model.addAttribute("error", error);
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
	return "home";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
	userService.deleteUser(id);
	return "redirect:/debug/list";
    }

}
