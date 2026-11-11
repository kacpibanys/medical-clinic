package com.github.kacpibanys.medicalclinic.service;

import com.github.kacpibanys.medicalclinic.exception.PatientAlreadyExistsException;
import com.github.kacpibanys.medicalclinic.model.Patient;
import com.github.kacpibanys.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findByEmail(String email){
        return patientRepository.findByEmail(email);
    }

    public Patient add(Patient patient) {
        if (patientRepository.findByEmail(patient.getEmail()).isPresent()) {
            throw new PatientAlreadyExistsException("Patient with adress: " + patient.getEmail() + " already exists!");
        }
        return patientRepository.save(patient);
    }

    public Optional<Patient> update(String email, Patient changes) {
        return patientRepository.findByEmail(email).map(existing -> {
            existing.setFirstName(changes.getFirstName());
            existing.setLastName(changes.getLastName());
            existing.setPhoneNumber(changes.getPhoneNumber());
            existing.setBirthday(changes.getBirthday());
            return patientRepository.save(existing);
        });
    }

    public Optional<Patient> changePassword(String email, String password) {
        return patientRepository.findByEmail(email).map(existing -> {
            existing.setPassword(password);
            return patientRepository.save(existing);
        });
    }

    public void delete(String email) {patientRepository.deleteByEmail(email);}
}
