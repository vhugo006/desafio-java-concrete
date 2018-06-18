package com.concrete.desafiojava.service.user;

import com.concrete.desafiojava.api.v1.user.UserInput;
import com.concrete.desafiojava.domain.orm.ApplicationUser;

public interface IInputService {

	public ApplicationUser toDomain(UserInput userInput);
	public Boolean isValidPassword(String inputPassword, String cryptPassword);
}
