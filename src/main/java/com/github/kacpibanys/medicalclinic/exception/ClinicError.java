package com.github.kacpibanys.medicalclinic.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ClinicError {
    PATIENT_NOT_FOUND(HttpStatus.NOT_FOUND),
    EMAIL_TAKEN(HttpStatus.CONFLICT),
    INVALID_PASSWORD(HttpStatus.UNPROCESSABLE_CONTENT);

    private final HttpStatus status;
}
