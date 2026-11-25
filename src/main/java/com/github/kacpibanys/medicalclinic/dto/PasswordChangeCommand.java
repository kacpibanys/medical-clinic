package com.github.kacpibanys.medicalclinic.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeCommand(@NotBlank(message = "Password is required")
                                    String password) {
    @Override
    public String toString() {
        return "PasswordChangeCommand[password=***]";
    }
}