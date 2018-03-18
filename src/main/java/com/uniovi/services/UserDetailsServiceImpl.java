package com.uniovi.services;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Esta clase parece ser que va a ser la encargada de establecer los roles de
 * usuarios a los usuarios de Spring, que son distintos a los usuarios de la
 * aplicacion.
 * 
 * Ademas cambia el añadir por username(default) por email
 * 
 * Spring por defecto tiene username y password y ademas tiene roles por defecto
 * que debemos sobrescribir. Por eso hay que sobrescribir este metodo.
 * 
 * @author Javier Castro
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = usersRepository.findByEmail(email);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		/*
		 * Con la siguiente linea le estamos asignando un rol al usuario. Asi luego el
		 * usuario de spring sabra que hacer con él
		 */
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
		// grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		// El user que viene a continuacion es el user que maneja spring, no el nuestro
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				grantedAuthorities);
	}
}
