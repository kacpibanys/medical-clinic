package com.github.kacpibanys.medicalclinic.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
public class Patient {
    private String email;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
}
