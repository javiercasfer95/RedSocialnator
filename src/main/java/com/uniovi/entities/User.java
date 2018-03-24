package com.uniovi.entities;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String lastName;
    private String role;
    private String password;
    @Transient
    private String passwordConfirm;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL)
    private Set<PeticionAmistad> peticionesRecibidas;

    @OneToMany(mappedBy = "origen", cascade = CascadeType.ALL)
    private Set<PeticionAmistad> peticionesEnviadas;

    @ManyToMany
    @JoinTable(name = "Amigos", joinColumns = { @JoinColumn(name = "usuario_id") }, inverseJoinColumns = {
	    @JoinColumn(name = "amigo_id") })
    private Set<User> amigos;

    public User(String email, String name, String lastName) {
	super();
	this.email = email;
	this.name = name;
	this.lastName = lastName;
    }

    public User() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getRole() {
	return role;
    }

    public void setRole(String role) {
	this.role = role;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getPasswordConfirm() {
	return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
	this.passwordConfirm = passwordConfirm;
    }

    @Override
    public String toString() {
	return "User [email=" + email + ", name=" + name + ", lastName=" + lastName + ", role=" + role + ", password="
		+ password + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((role == null) ? 0 : role.hashCode());
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
	User other = (User) obj;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (role == null) {
	    if (other.role != null)
		return false;
	} else if (!role.equals(other.role))
	    return false;
	return true;
    }

    public Set<PeticionAmistad> getPeticionesRecibidas() {
	return peticionesRecibidas;
    }

    public Set<PeticionAmistad> getPeticionesEnviadas() {
	return peticionesEnviadas;
    }

    public void setPeticionesEnviadas(Set<PeticionAmistad> peticionesEnviadas) {
	this.peticionesEnviadas = peticionesEnviadas;
    }

    public void setPeticionesRecibidas(Set<PeticionAmistad> peticionesRecibidas) {
	this.peticionesRecibidas = peticionesRecibidas;
    }

    public Set<User> getAmigos() {
	return amigos;
    }

    public void setAmigos(Set<User> amigos) {
	this.amigos = amigos;
    }

}
