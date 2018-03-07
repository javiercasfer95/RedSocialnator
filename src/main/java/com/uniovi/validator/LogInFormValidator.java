package com.uniovi.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;
import com.uniovi.entities.User;
import com.uniovi.services.UserService;

@Component
public class LogInFormValidator implements Validator {

	@Autowired
	private UserService usersService;

	@Override
	public boolean supports(Class<?> aClass) {

		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");

		// Hace una peticion y se obtiene la validacion de todos estos campos.
		if (!existeMail(user.getEmail())) {
			errors.rejectValue("email", "Error.login.email.notExists");
		}

		if (contraseñaCorrecta(user.getPassword())) {
			errors.rejectValue("password", "Error.login.password.notExists");
		}
	}

	private boolean existeMail(String email) {
		List<String> correos = usersService.getAllEmail();
		if (!correos.contains(email))
			return false;
		return true;
	}

	private boolean contraseñaCorrecta(String pass) {
		return usersService.correctPassword(pass);
	}
}
