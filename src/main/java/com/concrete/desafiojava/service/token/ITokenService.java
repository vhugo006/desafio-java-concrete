package com.concrete.desafiojava.service.token;

import javax.servlet.http.HttpServletRequest;

import com.concrete.desafiojava.exception.AuthenticationException;

public interface ITokenService {

	String generateToken(String email);
	Boolean isTokenMatches(String uuid, String token) throws AuthenticationException;
	String getToken(HttpServletRequest request);
	String encryptToken(String token);
}
