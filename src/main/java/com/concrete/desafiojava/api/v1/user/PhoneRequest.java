package com.concrete.desafiojava.api.v1.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRequest {

	@NotBlank(message="{number.not.blank}")
	@NotNull
	@Size(min=8, max=9, message="{number.size}")
	private String number;

	@NotBlank(message="{input.ddd}")
	@NotNull
	@Size(min=2, max=3, message="{input.ddd.size}")
	private String ddd;

}
