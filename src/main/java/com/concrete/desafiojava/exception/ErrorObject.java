package com.concrete.desafiojava.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ErrorObject {

    private final String message;
    private final String field;
    private final Object parameter;
}
