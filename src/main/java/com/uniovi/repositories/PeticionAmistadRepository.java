package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.PeticionAmistad;
import com.uniovi.entities.User;

public interface PeticionAmistadRepository extends CrudRepository<PeticionAmistad, Long> {

    Page<PeticionAmistad> findAll(Pageable pageable);

    @Query("SELECT p FROM PeticionAmistad p Where p.destino = ?1 AND p.aceptada = false")
    Page<PeticionAmistad> searchPeticionesToUser(Pageable pageable, User user);

    @Query("SELECT p FROM PeticionAmistad p Where p.origen = ?1 AND p.aceptada = false")
    Page<PeticionAmistad> searchPeticionesFromUser(Pageable pageable, User user);

    @Query("SELECT count(p) FROM PeticionAmistad p WHERE (p.origen = ?1 AND p.destino = ?2) OR (p.origen = ?2 AND p.destino = ?1)")
    int numeroPeticionesEntreDosUsuarios(User origen, User destino);

    @Query("SELECT p FROM PeticionAmistad p WHERE (p.origen = ?1 AND p.destino = ?2) OR (p.origen = ?2 AND p.destino = ?1)")
    PeticionAmistad findByUsers(User origen, User destino);

}
