package com.concrete.desafiojava.service.user;

import javax.servlet.http.HttpServletRequest;

import com.concrete.desafiojava.api.v1.login.LoginInput;
import com.concrete.desafiojava.api.v1.user.UserInput;
import com.concrete.desafiojava.api.v1.user.UserResponse;
import com.concrete.desafiojava.exception.AuthenticationException;
import com.concrete.desafiojava.exception.EmailFoundException;
import com.concrete.desafiojava.exception.InvalidPasswordEmailException;
import com.concrete.desafiojava.exception.SessionException;

public interface IUserService {

	public UserResponse save(UserInput userResource) throws EmailFoundException;

	public UserResponse login(LoginInput loginInput) throws InvalidPasswordEmailException;

	public UserResponse findUser(String id, HttpServletRequest request) throws AuthenticationException, SessionException;

}
