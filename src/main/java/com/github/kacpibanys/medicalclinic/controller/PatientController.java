package com.github.kacpibanys.medicalclinic.controller;

import com.github.kacpibanys.medicalclinic.model.Patient;
import com.github.kacpibanys.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAll();
    }
    @GetMapping("/{email}")
    public ResponseEntity<Patient> findById(@PathVariable String email) {
        return patientService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> add(@RequestBody Patient patient) {
        Patient saved = patientService.add(patient);
        return ResponseEntity.created(URI.create("/patients/" + saved.getEmail())).body(saved);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Patient> update(@PathVariable String email, @RequestBody Patient changes) {
        return patientService.update(email, changes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String email) {
        patientService.delete(email);
    }
}
