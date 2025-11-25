package org.example.interfaces;

import org.example.data.dtos.validation.FieldError;

import java.util.List;

public interface Validator<T> {
    List<FieldError> validate(T object);
}
