
package com.github.kacpibanys.medicalclinic.repository;

import com.github.kacpibanys.medicalclinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    @Transactional
    void deleteByEmail(String email);
    boolean existsByEmail(String email);
}

