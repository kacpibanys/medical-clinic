package com.github.kacpibanys.medicalclinic.model;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Data
public class Patient {
    private String email;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String idCardNo;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String phoneNumber;
    @Setter
    private LocalDate birthday;

    @Builder
    public Patient(String email, String password, String idCardNo, String firstName, String lastName, String phoneNumber, LocalDate birthday) {
        requireValidPassword(password);
        this.email = email;
        this.password = password;
        this.idCardNo = idCardNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public void changePassword(String newPassword) {
        requireValidPassword(newPassword);
        this.password = newPassword;
    }

    private void requireValidPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Passsword cannot be empty");
        }
    }
}
