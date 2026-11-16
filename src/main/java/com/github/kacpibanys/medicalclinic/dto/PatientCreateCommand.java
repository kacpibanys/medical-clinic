package com.github.kacpibanys.medicalclinic.dto;

import java.time.LocalDate;

public record PatientCreateCommand(
        String email,
        String password,
        String idCardNo,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthday
) {
    @Override
    public String toString() {
        return "PatientCreateCommand[email=" + email + ", password = *****, idCardNo=*****" +
                ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber +
                ", birthday=" + birthday + "]";
    }
}