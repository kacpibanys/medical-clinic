package com.github.kacpibanys.medicalclinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientCreateCommand(
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "ID card number is required")
        String idCardNo,
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @NotBlank(message = "Phone number is required")
        String phoneNumber,
        @NotNull(message = "Birthday is required")
        LocalDate birthday
) {
    @Override
    public String toString() {
        return "PatientCreateCommand[email=" + email + ", password = *****, idCardNo=*****" +
                ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber +
                ", birthday=" + birthday + "]";
    }
}