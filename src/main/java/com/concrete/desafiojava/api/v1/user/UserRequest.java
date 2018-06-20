package com.concrete.desafiojava.api.v1.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	@NotBlank(message = "{name.not.blank}")
	@NotNull
	private String name;

	@NotBlank(message = "{email.not.blank}")
	@NotNull
	@Email(message = "{email.not.valid}")
	private String email;

	@NotBlank(message = "{name.not.blank}")
	@NotNull
	private String password;

	@Valid
	private List<PhoneRequest> phones;
}
