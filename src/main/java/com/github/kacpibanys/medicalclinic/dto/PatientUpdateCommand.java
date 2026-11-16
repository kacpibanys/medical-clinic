package com.github.kacpibanys.medicalclinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientUpdateCommand(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @NotBlank(message = "Phone number is required")
        String phoneNumber,
        @NotNull(message = "Birthday is required")
        LocalDate birthday
) {}