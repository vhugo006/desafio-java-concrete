package com.concrete.desafiojava.api.v1.security;

public class SecurityConstants {

	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SAVE_USER_URL = "/v1/users/saveUser";
	public static final String LOGIN_URL = "/v1/users/login";
	public static final String H2_CONSOLE = "/console/**";
}
