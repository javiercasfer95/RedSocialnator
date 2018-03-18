package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.PeticionAmistad;
import com.uniovi.entities.User;
import com.uniovi.repositories.PeticionAmistadRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class PeticionAmistadService {

	@Autowired
	private PeticionAmistadRepository peticionAmistadRepository;
	// OJO QUE ESTÁ HACIENDO UN FIND ALL

	@Autowired
	private UsersRepository userRepository;

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

	public void aceptarPeticionAmistad(User origen, User destino) {
		PeticionAmistad pet = peticionAmistadRepository.findByUsers(origen, destino);
		pet.setAceptada(true);
		peticionAmistadRepository.save(pet);
		System.out.println("Aceptando solicitud de amistad");
		System.out.println(pet.toString());
		origen.getAmigos().add(destino);
		destino.getAmigos().add(origen);
		userRepository.save(origen);
		userRepository.save(destino);
		System.out.println(origen.getAmigos());
	}
	
	public boolean existePeticion(User origen, User destino) {
		if(peticionAmistadRepository.numeroPeticionesEntreDosUsuarios(origen, destino) > 0) {
			return true;
		}
		return false;
	}

}
