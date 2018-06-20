package com.concrete.desafiojava.service.token;

import static com.concrete.desafiojava.api.v1.security.SecurityConstants.EXPIRATION_TIME;
import static com.concrete.desafiojava.api.v1.security.SecurityConstants.SECRET;
import static com.concrete.desafiojava.api.v1.security.SecurityConstants.TOKEN_PREFIX;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.concrete.desafiojava.exception.AuthenticationException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements ITokenService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public String generateToken(String email) {
		return Jwts.builder().setSubject(email).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();
	}

	@Override
	public Boolean isTokenMatches(String noCryptToken, String cryptToken) throws AuthenticationException {

		if (!bCryptPasswordEncoder.matches(noCryptToken, cryptToken)) {
			throw new AuthenticationException();
		}
		return true;
	}

	@Override
	public String getToken(String headerContainingTonken) {

		return headerContainingTonken.replace(TOKEN_PREFIX, "");
	}

	@Override
	public String encryptToken(String token) {
		return bCryptPasswordEncoder.encode(token);
	}

}
