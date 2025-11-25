package org.example.interfaces;

import org.example.data.dtos.validation.FieldError;

@FunctionalInterface
public interface ValidationRule<T> {
    FieldError validate(T object);
}
