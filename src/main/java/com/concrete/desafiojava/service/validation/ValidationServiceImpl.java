package com.concrete.desafiojava.service.validation;

import static com.concrete.desafiojava.api.v1.security.SecurityConstants.HEADER_STRING;
import static com.concrete.desafiojava.api.v1.security.SecurityConstants.TOKEN_PREFIX;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concrete.desafiojava.api.v1.login.LoginInput;
import com.concrete.desafiojava.api.v1.user.UserInput;
import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.domain.repository.ApplicationUserRepository;
import com.concrete.desafiojava.exception.AuthenticationException;
import com.concrete.desafiojava.exception.EmailFoundException;
import com.concrete.desafiojava.exception.InvalidPasswordEmailException;
import com.concrete.desafiojava.exception.SessionException;
import com.concrete.desafiojava.service.user.IInputService;

@Service
public class ValidationServiceImpl implements IValidationService {

	private static long SESSION_DURATION = 30;
	
	@Autowired
	private IInputService inputService;
	
	@Autowired
	private ApplicationUserRepository userRepository;
	
	@Override
	public void validateLoginInputs(LoginInput loginInput, Optional<ApplicationUser> optionalUser) throws InvalidPasswordEmailException {

		if (!optionalUser.isPresent()) {
			throw new InvalidPasswordEmailException();
		}

		Boolean isValidPassword = inputService.isValidPassword(loginInput.getPassword(), optionalUser.get().getPassword());
		if (!isValidPassword) {
			throw new InvalidPasswordEmailException();
		}
	}

	@Override
	public void validateUserInput(UserInput userInput) throws EmailFoundException {

		Optional<ApplicationUser> optionalUser = userRepository.findByEmail(userInput.getEmail());
		if (optionalUser.isPresent()) {
			throw new EmailFoundException();
		}
	}

	@Override
	public void validateHeader(HttpServletRequest request) throws AuthenticationException {

		String header = request.getHeader(HEADER_STRING);
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			throw new AuthenticationException();
		}
	}
	
	@Override
	public void validateSession(Date lastLogin) throws SessionException {

		LocalDateTime startDate = lastLogin.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDateTime();
		LocalDateTime current = LocalDateTime.now();
		long minutes = Duration.between(startDate, current).toMinutes();
		
		if(minutes > SESSION_DURATION){
			throw new SessionException();
		}
		
	}


}
