package com.concrete.desafiojava.service.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.concrete.desafiojava.api.v1.user.UserRequest;
import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.service.token.ITokenService;
import com.concrete.desafiojava.service.uuid.UuidServiceImpl;

@Service
public class RequestServiceImpl implements IRequestService{

	@Autowired
	private UuidServiceImpl uuidService;
	
	@Autowired
	private ITokenService tokenService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public ApplicationUser toDomain(UserRequest userRequest) {

		Date currentDate = new Date();
		String token = tokenService.generateToken(userRequest.getEmail());
		
		return ApplicationUser.builder()
				.email(userRequest.getEmail())
				.name(userRequest.getName())
				.created(currentDate)
				.lastLogin(currentDate)
				.modified(currentDate)
				.uuid(uuidService.generateUuid())
				.noCryptToken(token)
				.token(tokenService.encryptToken(token))				
				.password(bCryptPasswordEncoder.encode(userRequest.getPassword()))
				.build();
	}
	
	public Boolean isValidPassword(String inputPassword, String cryptPassword){
		
		return bCryptPasswordEncoder.matches(inputPassword, cryptPassword);
	}
}
