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

    @RequestMapping("/peticiones/listRecibidas")
    public String getListadoPeticionesTo(Pageable pageable, Model model, Principal princiapl) {

	String email = princiapl.getName();
	User yo = userService.getUserByEmail(email);
	Page<PeticionAmistad> requests = peticionAmistadService.getAllPeticionesToUser(pageable, yo);
	model.addAttribute("peticionList", requests.getContent());
	model.addAttribute("page", requests);
	return "/peticiones/listRecibidas";
    }

    @RequestMapping("/peticiones/listEnviadas")
    public String getListadoPeticionesFrom(Pageable pageable, Model model, Principal princiapl) {

	String email = princiapl.getName();
	User yo = userService.getUserByEmail(email);
	Page<PeticionAmistad> requests = peticionAmistadService.getAllPeticionesFromUser(pageable, yo);
	model.addAttribute("peticionList", requests.getContent());
	model.addAttribute("page", requests);
	return "/peticiones/listEnviadas";
    }

    @RequestMapping("/peticion/enviarPeticion")
    public String enviarPeticion(Pageable pageabe, Model model, Principal principal, @RequestParam String emailRecibe) {

	// Para enviar peticiones me necesito a mi y necesito al que se la quiero enviar
	String email = principal.getName();
	User yo = userService.getUserByEmail(email);
	User otroTio = userService.getUserByEmail(emailRecibe);
	peticionAmistadService.crearPeticionAmistad(yo, otroTio);
	return "redirect:/user/list";
    }

    @RequestMapping("/peticion/aceptarPeticion")
    public String aceptarPeticion(Pageable pageabe, Model model, Principal principal,
	    @RequestParam String emailOrigen) {

	// Para enviar peticiones me necesito a mi y necesito al que se la quiero enviar
	String email = principal.getName();
	User yo = userService.getUserByEmail(email);
	User otroTio = userService.getUserByEmail(emailOrigen);
	peticionAmistadService.aceptarPeticionAmistad(yo, otroTio);
	return "redirect:/peticiones/listRecibidas";
    }

}
