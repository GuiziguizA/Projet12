
package org.sid.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sid.classe.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authentificationManager;

	public JWTAuthentificationFilter(AuthenticationManager authentificationManager) {
		super(); // TODO Auto-generated constructor stub
		this.authentificationManager = authentificationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// TODO Auto-generated method stub

		Users user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), Users.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("********");
		System.out.println("mail" + user.getMail());
		System.out.println("MPD" + user.getPassword());

		return authentificationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getMail(), user.getPassword()));

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException { // TODO
																				// Auto-generated
																				// method stub
		super.successfulAuthentication(request, response, chain, authResult);
	}

}
