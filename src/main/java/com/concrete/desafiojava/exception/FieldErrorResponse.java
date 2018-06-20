package com.concrete.desafiojava.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FieldErrorResponse {

	private final String message;
	private final int code;
	private final String status;
	private final String objectName;
	private final List<ErrorObject> errors;

}
