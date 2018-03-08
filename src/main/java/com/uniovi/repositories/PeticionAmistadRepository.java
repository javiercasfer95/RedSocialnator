package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.uniovi.entities.PeticionAmistad;
import com.uniovi.entities.User;

public interface PeticionAmistadRepository extends CrudRepository<PeticionAmistad, Long> {

	// @Query("SELECT p FROM PeticionAmistad p Where p.origen.id = :user.id")
	// Page<PeticionAmistad> searchAllPeticionesFromUser(Pageable pageable,
	// @Param("user") User user);
	//
	// @Query("SELECT p FROM PeticionAmistad p Where p.destino.id = :user.id")
	// Page<PeticionAmistad> searchAllPeticionesToUser(Pageable pageable,
	// @Param("user") User user);

//	@Query("SELECT p FROM PeticionAmistad p Where p.origen.id = ?2.id")
//	Page<PeticionAmistad> searchAllPeticionesFromUser(Pageable pageable, User user);
//
//	@Query("SELECT p FROM PeticionAmistad p Where p.destino.id = ?2.id")
//	Page<PeticionAmistad> searchAllPeticionesToUser(Pageable pageable, User user);
}
