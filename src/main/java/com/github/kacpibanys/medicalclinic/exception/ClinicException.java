package com.github.kacpibanys.medicalclinic.exception;

import lombok.Getter;

@Getter
public class ClinicException extends RuntimeException {
    private final ClinicError error;

    public ClinicException(ClinicError error, String message) {
        super(message);
        this.error = error;
    }
}
