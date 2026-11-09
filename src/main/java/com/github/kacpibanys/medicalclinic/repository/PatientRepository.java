package com.github.kacpibanys.medicalclinic.repository;

import com.github.kacpibanys.medicalclinic.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    List<Patient> findAll();
    Optional<Patient> findByEmail(String email);
    void save(Patient patient);
}
