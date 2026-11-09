package com.github.kacpibanys.medicalclinic.service;

import com.github.kacpibanys.medicalclinic.exception.PatientAlreadyExistsException;
import com.github.kacpibanys.medicalclinic.model.Patient;
import com.github.kacpibanys.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public void addPatient(Patient patient) {
        Optional<Patient> existingPatient = patientRepository.findByEmail(patient.getEmail());
        if (existingPatient.isPresent()) {
            throw new PatientAlreadyExistsException("Pacjent z adresem " + patient.getEmail() + " już istnieje!");
        }

        patientRepository.save(patient);
    }
}
