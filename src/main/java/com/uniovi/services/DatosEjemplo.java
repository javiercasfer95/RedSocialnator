package com.uniovi.services;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

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
		// usersService.addUser(user1);
		// usersService.addUser(user2);
		// usersService.addUser(user3);

		// Creamos usuarios aleatorios
		int numeroDeEllos = 0;
		numeroDeEllos = nombres.length * apellidos.length;
		numeroDeEllos = 40;
		System.out.println("numero de usuarios a cargar " + (3 + numeroDeEllos));
		for (int i = 1; i <= numeroDeEllos; i++) {
			User user = createRandomUser();
			if (!checkIfExist(user))
				usuariosAñadir.add(user);
		}

		for (User user : usuariosAñadir) {
			// System.out.println("Usuario: " + user.toString());
			usersService.addUser(user);
			// try {
			// TimeUnit.SECONDS.sleep(1);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// System.out.println("ERROR: Ha petado el añadir usuarios automaticamente por
			// el siguiente motivo:\n"
			// + e.getMessage());
			// }
		}

		// System.out.println(user3.getRole());

		// User user4 = new User("99999993D", "Marta", "Almonte");
		// user4.setPassword("123456");
		// user4.setRole(rolesService.getRoles()[1]);
		//
		// User user5 = new User("99999977E", "Pelayo", "Valdes");
		// user5.setPassword("123456");
		// user5.setRole(rolesService.getRoles()[1]);
		//
		// User user6 = new User("99999988F", "Edward", "Núñez");
		// user6.setPassword("123456");
		// user6.setRole(rolesService.getRoles()[2]);

		// Set user1Marks = new HashSet<Mark>() {
		// {
		// add(new Mark("Nota A1", 10.0, user1));
		// add(new Mark("Nota A2", 9.0, user1));
		// add(new Mark("Nota A3", 7.0, user1));
		// add(new Mark("Nota A4", 6.5, user1));
		// }
		// };
		// user1.setMarks(user1Marks);
		// Set user2Marks = new HashSet<Mark>() {
		// {
		// add(new Mark("Nota B1", 5.0, user2));
		// add(new Mark("Nota B2", 4.3, user2));
		// add(new Mark("Nota B3", 8.0, user2));
		// add(new Mark("Nota B4", 3.5, user2));
		// }
		// };
		//
		// user2.setMarks(user2Marks);
		//
		// Set user3Marks = new HashSet<Mark>() {
		// {
		// // ;
		// add(new Mark("Nota C1", 5.5, user3));
		// add(new Mark("Nota C2", 6.6, user3));
		// add(new Mark("Nota C3", 7.0, user3));
		// }
		// };
		// user3.setMarks(user3Marks);
		//
		// Set user4Marks = new HashSet<Mark>() {
		// {
		// add(new Mark("Nota D1", 10.0, user4));
		// add(new Mark("Nota D2", 8.0, user4));
		// add(new Mark("Nota D3", 9.0, user4));
		// }
		// };
		//
		// user4.setMarks(user4Marks);

		// usersService.addUser(user4);
		// usersService.addUser(user5);

		// usersService.addUser(user6);

	}

	@PostConstruct
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
		
		for(int i = 0; i < nombres.length -1; i++) {
			User rand = new User(nombres[i] + apellidos[i] + "@default.es", nombres[i], apellidos[i]);
			rand.setRole(rolesService.getDefaultRole());
			rand.setPassword("123456");
			usuariosAñadir.add(rand);
		}
		
		for(User u : usuariosAñadir) {
			usersService.addUser(u);
		}
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
