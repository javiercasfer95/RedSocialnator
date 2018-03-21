package com.uniovi.services;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

// Si se quiere desactivar la carga automatica de datos se debe comentar esta
// linea.

@Service
public class DatosEjemplo {
	@Autowired
	private UserService usersService;

	@Autowired
	private RoleService rolesService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	String[] nombres = { "Juan", "Pepita", "Mario", "Link", "Zelda", "Pikachu", "Ash", "Samus", "Snoop" };

	String[] apellidos = { "Francisco", "Gonzalez", "Dogg", "Castro", "Valles", "Alonso", "Rodriguez", "Fernandez",
			"Álvarez" };

	String[] correos = { "gmail.com", "hotmail.es", "uniovi.es", "live.com" };

	Set<User> usuariosAñadir;

	// @PostConstruct
	public void init() {

		usuariosAñadir = new HashSet();
		System.out.println("-------------------Se van a cargar usuarios por defecto------------------------");
		User user1 = new User("javier@correo.es", "Javier", "Castro");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);

		User user2 = new User("joni@correo.es", "Joni", "Valles");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);

		User user3 = new User("admin@correo.es", "Admin", "admin");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[1]); // Este va a ser admin

		usuariosAñadir.add(user1);
		usuariosAñadir.add(user2);
		usuariosAñadir.add(user3);
		// Creamos usuarios aleatorios
		int numeroDeEllos = 0;
		numeroDeEllos = nombres.length * apellidos.length;
		//numeroDeEllos = 40;
		System.out.println("numero de usuarios a cargar " + (3 + numeroDeEllos));
		for (int i = 1; i <= numeroDeEllos; i++) {
			User user = createRandomUser();
			if (!checkIfExist(user))
				usuariosAñadir.add(user);
		}

		for (User user : usuariosAñadir) {
			usersService.addUser(user);

		}

		log.info("Se han creado " + usuariosAñadir.size() + " usuarios. Fecha: " + Calendar.getInstance().getTime());

	}

	// @PostConstruct
	public void genereteDefaultUsers() {
		usuariosAñadir = new HashSet();
		System.out.println("-------------------Se van a cargar usuarios por defecto------------------------");
		User user1 = new User("javier@correo.es", "Javier", "Castro");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);

		User user2 = new User("joni@correo.es", "Joni", "Valles");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);

		User user3 = new User("admin@correo.es", "Admin", "admin");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[1]); // Este va a ser admin

		usuariosAñadir.add(user1);
		usuariosAñadir.add(user2);
		usuariosAñadir.add(user3);

		for (int i = 0; i < nombres.length - 1; i++) {
			User rand = new User(nombres[i] + apellidos[i] + "@default.es", nombres[i], apellidos[i]);
			rand.setRole(rolesService.getDefaultRole());
			rand.setPassword("123456");
			usuariosAñadir.add(rand);
		}

		for (User u : usuariosAñadir) {
			usersService.addUser(u);
		}

		log.info("Se han creado " + usuariosAñadir.size() + " usuarios. Fecha: " + Calendar.getInstance().getTime());
	}

	private User createRandomUser() {
		User user = null;
		int number = getRandomBetween(0, nombres.length);
		String nombre = nombres[number];
		number = getRandomBetween(0, apellidos.length);
		String apellido = apellidos[number];
		number = getRandomBetween(0, correos.length);
		String correo = nombre + apellido + "@" + correos[number];

		user = new User(correo, nombre, apellido);
		user.setPassword("123456");
		user.setRole(rolesService.getRoles()[0]);

		return user;
	}

	/**
	 * Devuelve un numero aleatorio entre low incluido, high excluido Tener cuidado
	 * con las listas.size
	 * 
	 * @param low
	 * @param high
	 * @return
	 */
	private int getRandomBetween(int low, int high) {
		Random r = new Random();
		int Result = r.nextInt(high - low) + low;
		return Result;
	}

	private boolean checkIfExist(User user) {
		for (User u : usuariosAñadir) {
			if (u.getEmail().equals(user.getEmail()))
				return true;
		}
		return false;
	}
}
