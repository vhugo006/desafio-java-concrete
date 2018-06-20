package com.concrete.desafiojava.service.validation;

import java.util.Date;
import java.util.Optional;

import com.concrete.desafiojava.api.v1.login.LoginRequest;
import com.concrete.desafiojava.api.v1.user.UserRequest;
import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.exception.AuthenticationException;
import com.concrete.desafiojava.exception.EmailFoundException;
import com.concrete.desafiojava.exception.InvalidPasswordEmailException;
import com.concrete.desafiojava.exception.SessionException;

public interface IValidationService {

	public void validateLoginInputs(LoginRequest loginInput, Optional<ApplicationUser> optionalUser) throws InvalidPasswordEmailException;
	public void validateUserRequest(UserRequest userInput) throws EmailFoundException;
	public void validateHeader(String header) throws AuthenticationException;
	public void validateSession(Date lastLogin) throws SessionException; 
}
