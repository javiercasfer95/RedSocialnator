package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DatosEjemplo datosEjemplo;

	@PostConstruct
	public void init() {
	}

	public Page<User> getUsers(Pageable pageable) {
		// List<User> users = new ArrayList<User>();
		Page<User> users = usersRepository.findAll(pageable);
		// usersRepository.findAll().forEach(users::add);
		return users;
	}

	public Page<User> getNotAdminUsers(Pageable pageable) {
		Page<User> users = usersRepository.findNotAdminUsers(pageable);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findOne(id);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // Hay que cifrar
		usersRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.delete(id);
	}

	public List<String> getAllEmail() {
		List<User> usuarios = usersRepository.findAll();
		List<String> correos = new ArrayList<>();
		for (User u : usuarios) {
			correos.add(u.getEmail());
		}
		return correos;
	}

	public boolean correctPassword(String password) {
		List<User> usuarios = usersRepository.findAll();
		for (User u : usuarios) {
			if (u.getPassword().equals(password))
				return true;
		}
		return false;
	}

	/*
	 * BUSQUEDA
	 */
	public Page<User> searchUserByEmailAndName(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList());
		searchText = "%" + searchText + "%";
		// if (user.getRole().equals("ROLE_STUDENT")) {
		// marks = marksRepository.searchByDescriptionNameAndUser(searchText, user);
		// }
		// if (user.getRole().equals("ROLE_PROFESSOR")) {
		// marks = marksRepository.searchByDescriptionAndName(searchText);
		// }
		users = usersRepository.searchByEmailAndName(pageable, searchText);
		return users;
	}
	
	//Reiniciar la base de datos SOLO VISIBLE Y ACCESIBLE POR ADMINISTRADOR REVISARRRRRRRRRRRRRRRRRRRRR
	public void deleteAllUsers(){
		usersRepository.deleteAll();
		datosEjemplo.init();
	}
}
