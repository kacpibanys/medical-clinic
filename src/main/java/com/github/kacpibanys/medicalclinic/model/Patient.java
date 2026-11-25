package com.github.kacpibanys.medicalclinic.model;

import com.github.kacpibanys.medicalclinic.exception.ClinicError;
import com.github.kacpibanys.medicalclinic.exception.ClinicException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@Table(name="patients")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String idCardNo;
    @Setter
    @Column(nullable = false)
    private String firstName;
    @Setter
    @Column(nullable = false)
    private String lastName;
    @Setter
    @Column(nullable = false)
    private String phoneNumber;
    @Setter
    @Column(nullable = false)
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
            throw new ClinicException(ClinicError.INVALID_PASSWORD, "Password must not be blank");
        }
    }
}
