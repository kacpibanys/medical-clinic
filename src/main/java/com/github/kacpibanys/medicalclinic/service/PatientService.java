package com.github.kacpibanys.medicalclinic.service;

import com.github.kacpibanys.medicalclinic.dto.PasswordChangeCommand;
import com.github.kacpibanys.medicalclinic.dto.PatientCreateCommand;
import com.github.kacpibanys.medicalclinic.dto.PatientDto;
import com.github.kacpibanys.medicalclinic.dto.PatientUpdateCommand;
import com.github.kacpibanys.medicalclinic.exception.ClinicError;
import com.github.kacpibanys.medicalclinic.exception.ClinicException;
import com.github.kacpibanys.medicalclinic.mapper.PatientMapper;
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
    private final PatientMapper patientMapper;

    public List<PatientDto> getAll() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public PatientDto findByEmail(String email){
        return patientMapper.toDto(findPatient(email));
    }

    public PatientDto add(PatientCreateCommand patientCreateCommand) {
        if (patientRepository.findByEmail(patientCreateCommand.email()).isPresent()) {
            throw new ClinicException(ClinicError.EMAIL_TAKEN, "Patient with this email already exists");
        }
        Patient patient = patientMapper.toEntity(patientCreateCommand);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public PatientDto update(String email, PatientUpdateCommand patientUpdateCommand) {
        Patient existing = findPatient(email);
        patientMapper.update(patientUpdateCommand, existing);
        return patientMapper.toDto(patientRepository.save(existing));
    }

    public PatientDto changePassword(String email, PasswordChangeCommand passwordChangeCommand) {
        Patient existing = findPatient(email);
        existing.changePassword(passwordChangeCommand.password());
        return patientMapper.toDto(patientRepository.save(existing));
    }

    public void delete(String email) {patientRepository.deleteByEmail(email);}

    private Patient findPatient(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new ClinicException(ClinicError.PATIENT_NOT_FOUND, "Patient not found"));
    }

    private PatientDto toDto(Patient patient) {
        return new PatientDto(
                patient.getEmail(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getBirthday()
        );
    }
}
