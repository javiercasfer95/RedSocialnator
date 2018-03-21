package com.uniovi.services;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
		if (origen.getEmail().equals(destino.getEmail())) {
			log.debug("Se ha intentado crear una peticion de amistad entre " + origen.getEmail() + " y " + destino.getEmail()
					+ ". Fecha: " + Calendar.getInstance().getTime());
			return;
		}
		/*
		 * No queremos añadir mas de una peticion de amistad entre dos usuarios
		 */
		if (peticionAmistadRepository.numeroPeticionesEntreDosUsuarios(origen, destino) > 0) {
			return;
		} else {
			PeticionAmistad peticionNueva = new PeticionAmistad(origen, destino);
			peticionAmistadRepository.save(peticionNueva);
		}
		log.info("Se ha creado una peticion de amistad entre " + origen.getEmail() + " y " + destino.getEmail()
				+ ". Fecha: " + Calendar.getInstance().getTime());
	}

	public void aceptarPeticionAmistad(User origen, User destino) {
		if (origen.getEmail().equals(destino.getEmail())) {
			log.debug("Se ha intentado aceptar una peticion de amistad entre " + origen.getEmail() + " y " + destino.getEmail()
					+ ". Fecha: " + Calendar.getInstance().getTime());
			return;
		}
		
		PeticionAmistad pet = peticionAmistadRepository.findByUsers(origen, destino);
		pet.setAceptada(true);
		peticionAmistadRepository.save(pet);
		origen.getAmigos().add(destino);
		destino.getAmigos().add(origen);
		userRepository.save(origen);
		userRepository.save(destino);
		System.out.println(origen.getAmigos());
		log.info("Se ha aceptado una peticion de amistad entre " + origen.getEmail() + " y " + destino.getEmail()
				+ ". Fecha: " + Calendar.getInstance().getTime());
	}

	public boolean existePeticion(User origen, User destino) {
		if (peticionAmistadRepository.numeroPeticionesEntreDosUsuarios(origen, destino) > 0) {
			return true;
		}
		return false;
	}

}
