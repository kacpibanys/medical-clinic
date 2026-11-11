package com.github.kacpibanys.medicalclinic.repository;

import com.github.kacpibanys.medicalclinic.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryPatientRepository implements PatientRepository {
    private final Map<String, Patient> patients = new LinkedHashMap<>();

    @Override
    public List<Patient> findAll() {
        return List.copyOf(patients.values());
    }

    @Override
    public Optional<Patient> findByEmail(String email){
        return Optional.ofNullable(patients.get(email));
    }

    @Override
    public Patient save(Patient patient) {
        patients.put(patient.getEmail(), patient);
        return patient;
    }

    @Override
    public void deleteByEmail(String email) {
        patients.remove(email);
    }
}
