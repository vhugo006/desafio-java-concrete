package com.concrete.desafiojava.api.v1.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {

	private String name;
	private String email;
	private String password;
	private List<PhoneInput> phones;
}
