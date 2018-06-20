package com.concrete.desafiojava.service.token;

import com.concrete.desafiojava.exception.AuthenticationException;

public interface ITokenService {

	String generateToken(String email);
	Boolean isTokenMatches(String uuid, String token) throws AuthenticationException;
	String getToken(String header);
	String encryptToken(String token);
}
