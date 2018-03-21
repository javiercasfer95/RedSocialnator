package com.uniovi.services;

import java.util.Calendar;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class AdminService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private DatosEjemplo datosEjemplo;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	public void deleteUser(User user) {
		Set<User> amigos = user.getAmigos();
		if (!amigos.isEmpty())
			for (User colega : amigos) {
				colega.getAmigos().remove(user);
				amigos.remove(colega);
			}
		usersRepository.delete(user);
	}
	
	// Reiniciar la base de datos SOLO VISIBLE Y ACCESIBLE POR ADMINISTRADOR
	public void deleteAllUsers() {
		usersRepository.deleteAll();
		datosEjemplo.init();
		log.info("Se han borrado todos los usuarios y se han creado los usuarios aleatorios. Fecha: "+Calendar.getInstance().getTime());
	}
	
	public void getDefaultUsers() {
		usersRepository.deleteAll();
		datosEjemplo.genereteDefaultUsers();
		log.info("Se han borrado todos los usuarios y se han creado los usuarios por defecto. Fecha: "+Calendar.getInstance().getTime());
	}
}
