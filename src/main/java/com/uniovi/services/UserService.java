package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.PeticionAmistad;
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
	Page<User> users = usersRepository.findAllUsers(pageable);
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
	Page<User> users = new PageImpl<User>(new LinkedList<User>());
	searchText = "%" + searchText + "%";
	users = usersRepository.searchByEmailAndName(pageable, searchText);
	return users;
    }

    public Page<User> getAllAmigos(Pageable pageable, User user) {
	List<User> amigos = new ArrayList<User>(user.getAmigos());
	Page<User> users = new PageImpl<User>(amigos);
	return users;
    }

    /**
     * Método que pretende buscar email usuarios con cualquier tipo de relacion
     * (peticiones/amistad) con un usuario que se pasa como praámetro
     * 
     * @param user
     * @return
     */
    public List<String> getUsuariosConRelaciones(User user) {
	List<String> listado = new ArrayList<String>();
	Set<User> colegas = user.getAmigos();
	Set<PeticionAmistad> peticionesEnviadas = user.getPeticionesEnviadas();
	Set<PeticionAmistad> peticionesRecibidas = user.getPeticionesRecibidas();
	for (User u : colegas) {
	    if (!listado.contains(u.getEmail()))
		listado.add(u.getEmail());
	}
	for (PeticionAmistad p : peticionesEnviadas) {
	    if (!listado.contains(p.getDestino().getEmail()))
		listado.add(p.getDestino().getEmail());
	}
	for (PeticionAmistad p : peticionesRecibidas) {
	    if (!listado.contains(p.getOrigen().getEmail()))
		listado.add(p.getOrigen().getEmail());
	}
	return listado;
    }

}
