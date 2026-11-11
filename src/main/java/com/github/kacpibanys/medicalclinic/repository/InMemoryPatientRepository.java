package com.github.kacpibanys.medicalclinic.repository;

import com.github.kacpibanys.medicalclinic.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryPatientRepository implements PatientRepository {
    private List<Patient> patients = new ArrayList<>();

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }

    @Override
    public Optional<Patient> findByEmail(String email){
        return patients.stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void save(Patient patient) {
        patients.add(patient);
    }
}
