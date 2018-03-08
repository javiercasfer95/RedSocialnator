package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
//@Table(name = "peticionAmistad")
public class PeticionAmistad {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "origen_id")
	private User origen;

	@ManyToOne
	@JoinColumn(name = "destino_id")
	private User destino;

	boolean aceptada = false;

	public PeticionAmistad() {
		super();
	}

	public PeticionAmistad(User origen, User destino) {
		aceptada = false;
		this.origen = origen;
		this.destino = destino;
	}

	public User getOrigen() {
		return origen;
	}

	public void setOrigen(User origen) {
		this.origen = origen;
	}

	public User getDestino() {
		return destino;
	}

	public void setDestino(User destino) {
		this.destino = destino;
	}

	public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}

}
