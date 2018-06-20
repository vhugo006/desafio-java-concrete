package com.concrete.desafiojava.service.user;

import java.util.Optional;

import com.concrete.desafiojava.api.v1.login.LoginRequest;
import com.concrete.desafiojava.api.v1.user.UserRequest;
import com.concrete.desafiojava.api.v1.user.UserResponse;
import com.concrete.desafiojava.exception.AuthenticationException;
import com.concrete.desafiojava.exception.EmailFoundException;
import com.concrete.desafiojava.exception.InvalidPasswordEmailException;
import com.concrete.desafiojava.exception.SessionException;
import com.concrete.desafiojava.exception.UserNotFoundException;

public interface IUserService {

	public Optional<UserResponse> save(UserRequest userRequest) throws EmailFoundException;

	public Optional<UserResponse> login(LoginRequest loginRequest) throws InvalidPasswordEmailException;

	public Optional<UserResponse> findUser(String id, String header) throws AuthenticationException, SessionException, UserNotFoundException;

}
