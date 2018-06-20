package com.concrete.desafiojava.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concrete.desafiojava.api.v1.login.LoginRequest;
import com.concrete.desafiojava.api.v1.user.PhoneResponse;
import com.concrete.desafiojava.api.v1.user.UserRequest;
import com.concrete.desafiojava.api.v1.user.UserResponse;
import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.domain.orm.Phone;
import com.concrete.desafiojava.domain.repository.ApplicationUserRepository;
import com.concrete.desafiojava.domain.repository.PhoneRepository;
import com.concrete.desafiojava.exception.AuthenticationException;
import com.concrete.desafiojava.exception.EmailFoundException;
import com.concrete.desafiojava.exception.InvalidPasswordEmailException;
import com.concrete.desafiojava.exception.SessionException;
import com.concrete.desafiojava.exception.UserNotFoundException;
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
	private IRequestService requestService;

	@Autowired
	private IValidationService validationService;

	@Autowired
	private ITokenService tokenService;

	@Override
	public Optional<UserResponse> save(UserRequest userRequest) throws EmailFoundException {

		validationService.validateUserRequest(userRequest);
		ApplicationUser user = saveUser(userRequest);
		List<Phone> userPhones = saveUserPhones(userRequest, user);
		return prepareUserResponse(user, Optional.of(userRequest.getPassword()), Optional.of(user.getNoCryptToken()), userPhones);
	}

	private ApplicationUser saveUser(UserRequest userRequest) {
		ApplicationUser savedUser = userRepository.save(requestService.toDomain(userRequest));
		return savedUser;
	}

	@Override
	public Optional<UserResponse> login(LoginRequest loginRequest) throws InvalidPasswordEmailException {

		Optional<ApplicationUser> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
		validationService.validateLoginInputs(loginRequest, optionalUser);

		ApplicationUser user = optionalUser.get().withLastLogin(new Date());
		userRepository.save(user);

		List<Phone> userPhones = phoneRepository.findByUser(user);
		return prepareUserResponse(optionalUser.get(), Optional.of(loginRequest.getPassword()), Optional.empty(), userPhones);
	}

	@Override
	public Optional<UserResponse> findUser(String uuid, String headerContainingToken) throws AuthenticationException, SessionException, UserNotFoundException {

		validationService.validateHeader(headerContainingToken);
		Optional<ApplicationUser> optionalUser = userRepository.findByUuid(uuid);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException();
		} else {
			ApplicationUser user = optionalUser.get();
			String token = tokenService.getToken(headerContainingToken);
			Boolean isValidToken = tokenService.isTokenMatches(token, user.getToken());

			if (isValidToken) {
				validationService.validateSession(user.getLastLogin());
			}
			List<Phone> userPhones = phoneRepository.findByUser(optionalUser.get());
			user.withNoCryptToken(token);
			return prepareUserResponse(user, Optional.empty(), Optional.of(token), userPhones);
		}

	}

	private List<Phone> saveUserPhones(UserRequest userRequest, ApplicationUser savedUser) {

		List<Phone> userPhoneListToSave = new ArrayList<>();
		userRequest.getPhones().forEach(phone -> userPhoneListToSave.add(new Phone(null, savedUser, phone.getNumber(), phone.getDdd())));
		return phoneService.savePhones(userPhoneListToSave);
	}

	private Optional<UserResponse> prepareUserResponse(ApplicationUser user, Optional<String> noCryptPassword, Optional<String> token, List<Phone> userPhones) {

		List<PhoneResponse> responsePhones = new ArrayList<>();
		userPhones.forEach(phone -> responsePhones.add(new PhoneResponse(phone.getNumber(), phone.getDdd())));

		return Optional.of(UserResponse.builder()
				.created(user.getCreated())
				.email(user.getEmail())
				.lastLogin(user.getLastLogin())
				.modified(user.getModified())
				.name(user.getName())
				.password(noCryptPassword.isPresent() ? noCryptPassword.get() : null)
				.phones(responsePhones)
				.id(user.getUuid())
				.token(token.isPresent() ? token.get() : null).build());
	}

}
