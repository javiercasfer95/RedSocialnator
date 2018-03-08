package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.PeticionAmistad;
import com.uniovi.entities.User;
import com.uniovi.repositories.PeticionAmistadRepository;

@Service
public class PeticionAmistadService {

	@Autowired
	private PeticionAmistadRepository peticionAmistadRepository;

	@PostConstruct
	public void init() {
	}

	// public Page<PeticionAmistad> getAllPeticionesFromUser(Pageable pageable, User
	// user) {
	// Page<PeticionAmistad> peticiones =
	// peticionAmistadRepository.searchAllPeticionesFromUser(pageable, user);
	// return peticiones;
	// }
	//
	// public Page<PeticionAmistad> getAllPeticionestoUser(Pageable pageable, User
	// user) {
	// Page<PeticionAmistad> peticiones =
	// peticionAmistadRepository.searchAllPeticionesToUser(pageable, user);
	// return peticiones;
	// }
}
