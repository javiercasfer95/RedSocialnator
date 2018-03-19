package com.uniovi.repositories;

import com.uniovi.entities.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	User findOne(Long id);

	void delete(Long id);

	Page<User> findAll(Pageable pageable);
	
	@Query("SELECT u FROM User u Where u.role LIKE 'ROLE_USER'")
	Page<User> findNotAdminUsers(Pageable pageable);

	List<User> findAll();
	
	@Query("SELECT u FROM User u ORDER BY u.name ASC")
	Page<User> findAllUsers(Pageable pageable);

	@Query("SELECT u FROM User u WHERE (LOWER(u.email) LIKE LOWER(?1) OR LOWER(u.name) LIKE LOWER(?1))")
	Page<User> searchByEmailAndName(Pageable pageable, String seachtext);
}
