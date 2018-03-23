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
	return "index";
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Pageable pageable, Principal principal, Model model) {
	return "redirect:/user/list";
    }

    @RequestMapping("/notFound")
    public String notFound(Principal principal, Model model) {

	return "notFound";
    }

    @RequestMapping(value = "/accesoDenegado", method = RequestMethod.GET)
    public String errorPage(Principal principal, Model model) {
	return "accesoDenegado";
    }
}
