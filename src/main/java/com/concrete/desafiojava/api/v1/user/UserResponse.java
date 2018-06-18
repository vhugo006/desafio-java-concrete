package com.concrete.desafiojava.api.v1.user;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private String id;
	private String name;
	private String email;
	private String password;
	private Date created;
	private Date modified;
	private Date lastLogin;
	private String token;
	private List<PhoneResponse> phones;

}
