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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeticionAmistad other = (PeticionAmistad) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
