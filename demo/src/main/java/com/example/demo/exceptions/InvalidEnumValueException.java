package com.example.demo.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidEnumValueException extends RuntimeException {
    private final String field;
    private final List<String> acceptedValues;

    public InvalidEnumValueException(String field, List<String> acceptedValues) {
        super("Valor inválido para o campo: " + field);
        this.field = field;
        this.acceptedValues = acceptedValues;
    }

}
