package com.uniovi.validator;

import com.uniovi.entities.User;
import com.uniovi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

/**
 * Validacion del momento de iniciar sesion.
 * 
 * Muy importante y se debe añadir en mas sitios. Mejor una clase por cada fase
 * de validacion, por ejemplo, otra al editar notas.
 * 
 * @author javit
 *
 */
@Component
public class SignUpFromValidator implements Validator {

	@Autowired
	private UserService usersService;

	@Override
	public boolean supports(Class<?> aClass) {

		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		/*
		 * Mira a ver si el campo o los campos tienen un espacio en blanco o esta vacio
		 * y lo añade a Error.empty del propertie para devolverlo en el idoma adecuado
		 */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");

		// Hace una peticion y se obtiene la validacion de todos estos campos.
		if (user.getEmail().length() < 5 || user.getEmail().length() > 24) {
			errors.rejectValue("dni", "Error.signup.dni.length");
		}

		if (usersService.getUserByDni(user.getEmail()) != null) {
			errors.rejectValue("dni", "Error.signup.dni.duplicate");
		}
		if (user.getName().length() < 5 || user.getName().length() > 24) {
			errors.rejectValue("name", "Error.signup.name.length");
		}
		if (user.getLastName().length() < 5 || user.getLastName().length() > 24) {
			errors.rejectValue("lastName", "Error.signup.lastName.length");
		}

		if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
			errors.rejectValue("password", "Error.signup.password.length");
		}

		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
		}
	}
}