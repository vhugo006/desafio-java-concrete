package com.concrete.desafiojava.security;

import static com.concrete.desafiojava.api.v1.security.SecurityConstants.HEADER_STRING;
import static com.concrete.desafiojava.api.v1.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.concrete.desafiojava.api.v1.user.UserInput;
import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.service.token.ITokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private ITokenService tokenService;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		try {
			UserInput creds = new ObjectMapper().readValue(req.getInputStream(), UserInput.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {

		String token = tokenService.generateToken(((ApplicationUser) auth.getPrincipal()).getEmail());
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}
