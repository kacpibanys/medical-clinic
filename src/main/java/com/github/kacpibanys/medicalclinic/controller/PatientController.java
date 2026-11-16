package com.github.kacpibanys.medicalclinic.controller;

import com.github.kacpibanys.medicalclinic.dto.PasswordChangeCommand;
import com.github.kacpibanys.medicalclinic.dto.PatientCreateCommand;
import com.github.kacpibanys.medicalclinic.dto.PatientDto;
import com.github.kacpibanys.medicalclinic.dto.PatientUpdateCommand;
import com.github.kacpibanys.medicalclinic.model.Patient;
import com.github.kacpibanys.medicalclinic.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.getAll();
    }
    @GetMapping("/{email}")
    public ResponseEntity<PatientDto> findByEmail(@PathVariable String email) {
        return patientService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatientDto> add(@Valid @RequestBody PatientCreateCommand patientCreateCommand) {
        PatientDto saved = patientService.add(patientCreateCommand);
        return ResponseEntity.created(URI.create("/patients/" + saved.email())).body(saved);
    }

    @PutMapping("/{email}")
    public ResponseEntity<PatientDto> update(@PathVariable String email, @Valid @RequestBody PatientUpdateCommand patientUpdateCommand) {
        return patientService.update(email, patientUpdateCommand)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{email}/password")
    public ResponseEntity<PatientDto> changePassword(@PathVariable String email,
                                                     @Valid @RequestBody PasswordChangeCommand passwordChangeCommand) {
        return patientService.changePassword(email, passwordChangeCommand)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String email) {
        patientService.delete(email);
    }
}
