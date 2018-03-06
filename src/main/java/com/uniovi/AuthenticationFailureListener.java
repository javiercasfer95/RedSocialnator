package com.uniovi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.uniovi.validator.LogInFormValidator;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
	private LogInFormValidator logInFormValidator;

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
		String username = e.getAuthentication().getName();
		WebAuthenticationDetails detalles = (WebAuthenticationDetails) e.getAuthentication().getDetails();
		String ip = detalles.getRemoteAddress();
		String idSession = detalles.getSessionId();
	}
}