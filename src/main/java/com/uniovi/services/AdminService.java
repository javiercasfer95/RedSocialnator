package com.uniovi.services;

import java.util.Set;

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
	// REVISARRRRRRRRRRRRRRRRRRRRR
	public void deleteAllUsers() {
		usersRepository.deleteAll();
		datosEjemplo.init();
	}
	
	public void getDefaultUsers() {
		usersRepository.deleteAll();
		datosEjemplo.genereteDefaultUsers();
	}
}
