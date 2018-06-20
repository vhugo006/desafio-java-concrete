package com.concrete.desafiojava.service.user;

import com.concrete.desafiojava.api.v1.user.UserRequest;
import com.concrete.desafiojava.domain.orm.ApplicationUser;

public interface IRequestService {

	public ApplicationUser toDomain(UserRequest userRequest);
	public Boolean isValidPassword(String inputPassword, String cryptPassword);
}
