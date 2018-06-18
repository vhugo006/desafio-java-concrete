package com.concrete.desafiojava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { EmailFoundException.class })
	@ResponseBody
	protected ResponseEntity<MessageErrorResponse> handleEmailFoundException(EmailFoundException e) {
		
		MessageErrorResponse response = MessageErrorResponse.builder()
			.message("E-mail j� existente")
			.build();
		return new ResponseEntity<MessageErrorResponse>(response, HttpStatus.OK);

	}
	
	@ExceptionHandler(value = { InvalidPasswordEmailException.class })
	@ResponseBody
	protected ResponseEntity<MessageErrorResponse> handleInvalidPasswordOrUserException(InvalidPasswordEmailException e) {
		
		MessageErrorResponse response = MessageErrorResponse.builder()
			.message("Usu�rio e/ou senha inv�lidos")
			.build();
		return new ResponseEntity<MessageErrorResponse>(response, HttpStatus.UNAUTHORIZED);

	}
	
	@ExceptionHandler(value = { AuthenticationException.class })
	@ResponseBody
	protected ResponseEntity<MessageErrorResponse> handleAuthenticationException(AuthenticationException e) {
		
		MessageErrorResponse response = MessageErrorResponse.builder()
			.message("N�o Autorizado")
			.build();
		return new ResponseEntity<MessageErrorResponse>(response, HttpStatus.UNAUTHORIZED);

	}
	
	@ExceptionHandler(value = { SessionException.class })
	@ResponseBody
	protected ResponseEntity<MessageErrorResponse> handleSessionException(SessionException e) {
		
		MessageErrorResponse response = MessageErrorResponse.builder()
			.message("Sess�o Inv�lida")
			.build();
		return new ResponseEntity<MessageErrorResponse>(response, HttpStatus.UNAUTHORIZED);

	}
}
