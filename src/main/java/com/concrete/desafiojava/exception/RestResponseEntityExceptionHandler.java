package com.concrete.desafiojava.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.concrete.desafiojava.components.Messages;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private Messages messages;

	@ExceptionHandler(value = { EmailFoundException.class })
	@ResponseBody
	protected ResponseEntity<MessageErrorResponse> handleEmailFoundException(EmailFoundException e) {

		MessageErrorResponse response = MessageErrorResponse.builder().message(messages.get("email.exists")).build();
		return new ResponseEntity<MessageErrorResponse>(response, HttpStatus.OK);

	}

	@ExceptionHandler(value = { InvalidPasswordEmailException.class })
	@ResponseBody
	protected ResponseEntity<MessageErrorResponse> handleInvalidPasswordOrUserException(InvalidPasswordEmailException e) {

		MessageErrorResponse response = MessageErrorResponse.builder().message(messages.get("invalid.username.password")).build();
		return new ResponseEntity<MessageErrorResponse>(response, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(value = { AuthenticationException.class })
	@ResponseBody
	protected ResponseEntity<MessageErrorResponse> handleAuthenticationException(AuthenticationException e) {

		MessageErrorResponse response = MessageErrorResponse.builder().message(messages.get("not.authorized")).build();
		return new ResponseEntity<MessageErrorResponse>(response, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(value = { SessionException.class })
	@ResponseBody
	protected ResponseEntity<MessageErrorResponse> handleSessionException(SessionException e) {

		MessageErrorResponse response = MessageErrorResponse.builder().message(messages.get("invalid.session")).build();
		return new ResponseEntity<MessageErrorResponse>(response, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(value = { UserNotFoundException.class })
	@ResponseBody
	protected ResponseEntity<MessageErrorResponse> handleUserNotFoundException(UserNotFoundException e) {

		MessageErrorResponse response = MessageErrorResponse.builder().message(messages.get("user.not.found")).build();
		return new ResponseEntity<MessageErrorResponse>(response, HttpStatus.UNAUTHORIZED);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ErrorObject> errors = getErrors(ex);
		FieldErrorResponse errorResponse = getFieldErrorResponse(ex, status, errors);

		return new ResponseEntity<>(errorResponse, status);
	}

	private FieldErrorResponse getFieldErrorResponse(MethodArgumentNotValidException ex, HttpStatus status, List<ErrorObject> errors) {

		return new FieldErrorResponse(messages.get("invalid.fields"), status.value(), status.getReasonPhrase(), ex.getBindingResult().getObjectName(), errors);
	}

	private List<ErrorObject> getErrors(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getFieldErrors().stream().map(error -> new ErrorObject(error.getDefaultMessage(), error.getField(), error.getRejectedValue())).collect(Collectors.toList());
	}
}
