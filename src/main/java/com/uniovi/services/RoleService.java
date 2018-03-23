package com.uniovi.services;

import org.springframework.stereotype.Service;

@Service
public class RoleService {
    String[] roles = { "ROLE_USER", "ROLE_ADMIN" };

    String defaultRole = roles[0];
    String adminRole = roles[1];

    public String[] getRoles() {
	return roles;
    }

    public String getDefaultRole() {
	return defaultRole;
    }

    public String getAdminRole() {
	return adminRole;
    }

}
