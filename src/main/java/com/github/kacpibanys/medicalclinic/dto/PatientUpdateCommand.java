package com.github.kacpibanys.medicalclinic.dto;

import java.time.LocalDate;

public record PatientUpdateCommand(
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthday
) {}