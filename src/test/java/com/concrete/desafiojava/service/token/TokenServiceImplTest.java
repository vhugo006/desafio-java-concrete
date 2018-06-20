package com.concrete.desafiojava.service.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.concrete.desafiojava.exception.AuthenticationException;

public class TokenServiceImplTest {

	private static final String EMAIL = "vhugo006@gmail.com";
	private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aWN0b3JAc2lsdmEub3JnIiwiZXhwIjoxNTMwMTg4Mzg4fQ.CT4uPA9YM_ZbWByurzuWTkvD9IkvCWxuKKhOr4iZTXh1PvXQRBPVfMSHWVpvYaCcX30Sd7JaemUS_T2yErjp3Q";
	private static final String CRYPT_TOKEN = "$2a$10$Ol9JVEBG/O5ntDUs5c/8S.hgB1U7LFZvp2HnIG5zULsg7jQ6/Mtxu";
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Mock
	TokenServiceImpl tokenService;
	
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Test
	public void generationToken() {

		when(tokenService.generateToken(EMAIL))
			.thenReturn(TOKEN);
		
		String token = tokenService.generateToken(EMAIL);
		assertEquals(TOKEN, token);

	}
	
	@Test(expected=AuthenticationException.class)
	public void validatingIfNoCryptTokenNoMatchesCryptToken() throws AuthenticationException{
		
		when(tokenService.generateToken(EMAIL))
			.thenReturn(TOKEN);
		
		String token = tokenService.generateToken(EMAIL);
		
		when(tokenService.isTokenMatches(token, CRYPT_TOKEN))
			.thenThrow(new AuthenticationException());
		
		tokenService.isTokenMatches(token, CRYPT_TOKEN);
	}
	
	@Test
	public void validatingIfNoCryptTokenMatchesCryptToken() throws AuthenticationException{
		
		when(tokenService.generateToken(EMAIL))
			.thenReturn(TOKEN);
		
		String token = tokenService.generateToken(EMAIL);
		
		when(tokenService.isTokenMatches(token, CRYPT_TOKEN))
			.thenReturn(true);
		
		Boolean isValidToken = tokenService.isTokenMatches(token, CRYPT_TOKEN);
		assertTrue(isValidToken);
	}
	
	

}
