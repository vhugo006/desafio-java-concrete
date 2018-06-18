package com.concrete.desafiojava.api.v1.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.concrete.desafiojava.api.v1.login.LoginInput;
import com.concrete.desafiojava.exception.AuthenticationException;
import com.concrete.desafiojava.exception.EmailFoundException;
import com.concrete.desafiojava.exception.InvalidPasswordEmailException;
import com.concrete.desafiojava.exception.SessionException;
import com.concrete.desafiojava.service.user.IUserService;

@RestController
@RequestMapping("v1/users")
public class UsersRestService {

	@Autowired
	private IUserService userService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/saveUser")
	public UserResponse saveUser(@RequestBody UserInput userInput) throws EmailFoundException {
		return userService.save(userInput);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/login")
	public UserResponse login(@RequestBody LoginInput loginInput) throws InvalidPasswordEmailException {
		return userService.login(loginInput);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{id}")
	public UserResponse validateToken(@PathVariable(value = "id") String id, HttpServletRequest request) throws AuthenticationException, SessionException {
		return userService.findUser(id, request);
	}
}
