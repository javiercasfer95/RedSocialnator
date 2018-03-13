package com.uniovi.services;

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
	// OJO QUE ESTÁ HACIENDO UN FIND ALL

	public Page<PeticionAmistad> getAllPeticionesFromUser(Pageable pageable, User user) {
		Page<PeticionAmistad> requests = peticionAmistadRepository.searchPeticionesFromUser(pageable, user);
		return requests;
	}

	// OJO QUE ESTÁ HACIENDO UN FIND ALL

	public Page<PeticionAmistad> getAllPeticionesToUser(Pageable pageable, User user) {
		Page<PeticionAmistad> peticiones = peticionAmistadRepository.searchPeticionesToUser(pageable, user);
		return peticiones;
	}

	public Page<PeticionAmistad> getAllPeticionesDelSistema(Pageable pageable) {
		Page<PeticionAmistad> res = peticionAmistadRepository.findAll(pageable);
		return res;
	}

	public void crearPeticionAmistad(User origen, User destino) {

		/*
		 * No queremos añadir mas de una peticion de amistad entre dos usuarios
		 */
		if (peticionAmistadRepository.numeroPeticionesEntreDosUsuarios(origen, destino) > 0) {
			System.out.println("----------DEBUG--------------\n\tYA EXISTE ESA PETICION\n");
			System.out.println("Entre " + origen.getEmail() + " y " + destino.getEmail());
			return;
		} else {
			PeticionAmistad peticionNueva = new PeticionAmistad(origen, destino);
			peticionAmistadRepository.save(peticionNueva);
		}
	}

}
