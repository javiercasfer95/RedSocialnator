package com.uniovi.services;

import org.springframework.stereotype.Service;

@Service
public class RoleService {
	String[] roles = { "ROLE_SIMPLEUSER", "ROLE_PROFESSOR", "ROLE_ADMIN" };

	public String[] getRoles() {
		return roles;
	}
}
