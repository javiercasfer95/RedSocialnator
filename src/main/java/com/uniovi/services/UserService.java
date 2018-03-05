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
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void init() {
	}

	public Page<User> getUsers(Pageable pageable) {
		// List<User> users = new ArrayList<User>();
		Page<User> users = usersRepository.findAll(pageable);
		// usersRepository.findAll().forEach(users::add);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findOne(id);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // Hay que cifrar
		usersRepository.save(user);
	}

	public User getUserByDni(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.delete(id);
	}

	/*
	 * BUSQUEDA
	 */
	public Page<User> searchUserByDNIAndName(Pageable pageable, String searchText) {
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
}
