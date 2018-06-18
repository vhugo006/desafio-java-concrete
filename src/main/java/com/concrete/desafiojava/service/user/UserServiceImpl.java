package com.concrete.desafiojava.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concrete.desafiojava.api.v1.login.LoginInput;
import com.concrete.desafiojava.api.v1.user.PhoneResponse;
import com.concrete.desafiojava.api.v1.user.UserInput;
import com.concrete.desafiojava.api.v1.user.UserResponse;
import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.domain.orm.Phone;
import com.concrete.desafiojava.domain.repository.ApplicationUserRepository;
import com.concrete.desafiojava.domain.repository.PhoneRepository;
import com.concrete.desafiojava.exception.AuthenticationException;
import com.concrete.desafiojava.exception.EmailFoundException;
import com.concrete.desafiojava.exception.InvalidPasswordEmailException;
import com.concrete.desafiojava.exception.SessionException;
import com.concrete.desafiojava.service.phone.IPhoneService;
import com.concrete.desafiojava.service.token.ITokenService;
import com.concrete.desafiojava.service.validation.IValidationService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private ApplicationUserRepository userRepository;

	@Autowired
	private PhoneRepository phoneRepository;

	@Autowired
	private IPhoneService phoneService;

	@Autowired
	private IInputService inputService;

	@Autowired
	private IValidationService validationService;

	@Autowired
	private ITokenService tokenService;

	@Override
	public UserResponse save(UserInput userInput) throws EmailFoundException {

		validationService.validateUserInput(userInput);
		ApplicationUser user = saveUser(userInput);
		List<Phone> userPhones = saveUserPhones(userInput, user);
		return prepareUserResponse(user, Optional.of(userInput.getPassword()), Optional.of(user.getNoCryptToken()), userPhones);
	}

	private ApplicationUser saveUser(UserInput userInput) {
		ApplicationUser savedUser = userRepository.save(inputService.toDomain(userInput));
		return savedUser;
	}

	@Override
	public UserResponse login(LoginInput loginInput) throws InvalidPasswordEmailException {

		Optional<ApplicationUser> optionalUser = userRepository.findByEmail(loginInput.getEmail());
		validationService.validateLoginInputs(loginInput, optionalUser);

		ApplicationUser user = optionalUser.get().withLastLogin(new Date());
		userRepository.save(user);

		List<Phone> userPhones = phoneRepository.findByUser(user);
		return prepareUserResponse(optionalUser.get(), Optional.of(loginInput.getPassword()), Optional.empty(), userPhones);
	}

	@Override
	public UserResponse findUser(String uuid, HttpServletRequest request) throws AuthenticationException, SessionException {

		validationService.validateHeader(request);
		Optional<ApplicationUser> optionalUser = userRepository.findByUuid(uuid);
		ApplicationUser user = optionalUser.get();
		String token = tokenService.getToken(request);
		Boolean isValidToken = tokenService.isTokenMatches(token, user.getToken());

		if (isValidToken) {
			validationService.validateSession(user.getLastLogin());
		}
		List<Phone> userPhones = phoneRepository.findByUser(optionalUser.get());
		user.withNoCryptToken(token);
		return prepareUserResponse(user, Optional.empty(), Optional.of(token), userPhones);
	}

	private List<Phone> saveUserPhones(UserInput userInput, ApplicationUser savedUser) {

		List<Phone> userPhoneListToSave = new ArrayList<>();
		userInput.getPhones().forEach(phone -> userPhoneListToSave.add(new Phone(null, savedUser, phone.getNumber(), phone.getDdd())));
		return phoneService.savePhones(userPhoneListToSave);
	}

	private UserResponse prepareUserResponse(ApplicationUser user, Optional<String> noCryptPassword, Optional<String> token, List<Phone> userPhones) {

		List<PhoneResponse> responsePhones = new ArrayList<>();
		userPhones.forEach(phone -> responsePhones.add(new PhoneResponse(phone.getNumber(), phone.getDdd())));

		return UserResponse.builder()
				.created(user.getCreated())
				.email(user.getEmail())
				.lastLogin(user.getLastLogin())
				.modified(user.getModified())
				.name(user.getName())
				.password(noCryptPassword.isPresent() ? noCryptPassword.get() : null)
				.phones(responsePhones)
				.id(user.getUuid())
				.token(token.isPresent() ? token.get() : null).build();
	}

}
