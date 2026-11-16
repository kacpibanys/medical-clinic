package com.github.kacpibanys.medicalclinic.service;

import com.github.kacpibanys.medicalclinic.dto.PasswordChangeCommand;
import com.github.kacpibanys.medicalclinic.dto.PatientCreateCommand;
import com.github.kacpibanys.medicalclinic.dto.PatientDto;
import com.github.kacpibanys.medicalclinic.dto.PatientUpdateCommand;
import com.github.kacpibanys.medicalclinic.exception.PatientAlreadyExistsException;
import com.github.kacpibanys.medicalclinic.mapper.PatientMapper;
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
    private final PatientMapper patientMapper;

    public List<PatientDto> getAll() {
        return patientRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public Optional<PatientDto> findByEmail(String email){
        return patientRepository.findByEmail(email).map(this::toDto);
    }

    public PatientDto add(PatientCreateCommand patientCreateCommand) {
        Patient patient = patientMapper.toEntity(patientCreateCommand);
        return toDto(patientRepository.save(patient));
    }

    public Optional<PatientDto> update(String email, PatientUpdateCommand patientUpdateCommand) {
        return patientRepository.findByEmail(email).map(existing -> {
            patientMapper.update(patientUpdateCommand, existing);
            return toDto(patientRepository.save(existing));
        });
    }

    public Optional<PatientDto> changePassword(String email, PasswordChangeCommand passwordChangeCommand) {
        return patientRepository.findByEmail(email).map(existing -> {
            existing.changePassword(passwordChangeCommand.password()); // Walidacja siedzi w modelu
            return toDto(patientRepository.save(existing));
        });
    }

    public void delete(String email) {patientRepository.deleteByEmail(email);}

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
