package com.uniovi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;;

/**
 * Esta clase va a ser la encargada del login de cuentas
 * 
 * @author Javier Castro
 *
 */
@Service
public class SecurityService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	public String findLoggedInEmail() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();//Username en verdad es el Email
		}

		return null;
	}

	/**
	 * Este metodo es para que cuando se registre un usuario se auto inicie su
	 * sesion, no es obligatorio.
	 * 
	 * @param dni
	 * @param password
	 */
	public void autoLogin(String dni, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(dni);
		UsernamePasswordAuthenticationToken aToken = new UsernamePasswordAuthenticationToken(userDetails, password,
				userDetails.getAuthorities());

		authenticationManager.authenticate(aToken);

		if (aToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(aToken);
			logger.debug(String.format("Auto login %s successfully!", dni));
		}
	}
}