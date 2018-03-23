package com.uniovi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Sesion 3 Este fichero se va a ir modificando a medida que se van a ir
 * añadiendo paginas a la web. Es donde se dan los permisos a los usuarios.
 * Acordarse de la teoria. El orden de los permisos es importante. EXAMEN
 * 
 * @author javit
 *
 *
 *
 *
 *
 *         Nomras que se van a seguir:
 * 
 *         - Los usuarios con cualquier autoridad pueden consultar lista de
 *         notas, esto abarca list, update y details/*
 * 
 *         -Solo los usuarios con el rol de profesor pueden añadir notas, borrar
 *         las que sean y editar las que sean.
 * 
 *         En caso de que no tenga acceso, va a aparecer la ventana de error de
 *         spring con el mensaje de Acces denied
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

	http.csrf().disable().authorizeRequests()
		.antMatchers("/css/**", "/img/**", "/script/**", "/", "/signup", "/login").permitAll()
		.antMatchers("/debug/**").hasAuthority("ROLE_ADMIN").antMatchers("user/**", "peticiones/**")
		.hasAuthority("ROLE_USER").anyRequest().authenticated().and().formLogin().loginPage("/login")
		.failureUrl("/login?error=1").defaultSuccessUrl("/home").and().logout().permitAll().and()
		.exceptionHandling().accessDeniedPage("/accesoDenegado").and().logout().logoutUrl("/logout")
		.logoutSuccessUrl("/login");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
