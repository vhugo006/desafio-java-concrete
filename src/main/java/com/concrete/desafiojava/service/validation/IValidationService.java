package com.concrete.desafiojava.service.validation;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.concrete.desafiojava.api.v1.login.LoginInput;
import com.concrete.desafiojava.api.v1.user.UserInput;
import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.exception.AuthenticationException;
import com.concrete.desafiojava.exception.EmailFoundException;
import com.concrete.desafiojava.exception.InvalidPasswordEmailException;
import com.concrete.desafiojava.exception.SessionException;

public interface IValidationService {

	public void validateLoginInputs(LoginInput loginInput, Optional<ApplicationUser> optionalUser) throws InvalidPasswordEmailException;
	public void validateUserInput(UserInput userInput) throws EmailFoundException;
	public void validateHeader(HttpServletRequest request) throws AuthenticationException;
	public void validateSession(Date lastLogin) throws SessionException; 
}
