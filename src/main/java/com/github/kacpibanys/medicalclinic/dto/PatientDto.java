package com.github.kacpibanys.medicalclinic.dto;

import java.time.LocalDate;

public record PatientDto(String email,
                         String firstName,
                         String lastName,
                         String phoneNumber,
                         LocalDate birthday) {
}
