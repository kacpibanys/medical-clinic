package com.github.kacpibanys.medicalclinic.controller;

import com.github.kacpibanys.medicalclinic.dto.PasswordChangeCommand;
import com.github.kacpibanys.medicalclinic.dto.PatientCreateCommand;
import com.github.kacpibanys.medicalclinic.dto.PatientDto;
import com.github.kacpibanys.medicalclinic.dto.PatientUpdateCommand;
import com.github.kacpibanys.medicalclinic.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Patients", description = "Patient registry: browse, register, update and remove patients")
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @Operation(summary = "List all patients")
    @ApiResponse(responseCode = "200", description = "List of patients")
    @GetMapping
    public List<PatientDto> getAll() {
        return patientService.getAll();
    }

    @Operation(summary = "Get one patient by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "No patient with this email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{email}")
    public PatientDto findByEmail(@PathVariable String email) {
        return patientService.findByEmail(email);
    }

    @Operation(summary = "Register a new patient; the Location header points to it")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Patient registered"),
            @ApiResponse(responseCode = "400", description = "Invalid fields or body is not JSON",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "A patient with this email already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<PatientDto> add(@Valid @RequestBody PatientCreateCommand command) {
        PatientDto saved = patientService.add(command);
        return ResponseEntity.created(URI.create("/patients/" + saved.email())).body(saved);
    }

    @Operation(summary = "Replace patient data; email and ID card number never change")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient updated"),
            @ApiResponse(responseCode = "400", description = "Invalid fields or body is not JSON",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "No patient with this email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{email}")
    public PatientDto update(@PathVariable String email, @Valid @RequestBody PatientUpdateCommand command) {
        return patientService.update(email, command);
    }

    @Operation(summary = "Change the password of one patient")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed"),
            @ApiResponse(responseCode = "400", description = "Missing or blank password",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "No patient with this email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{email}/password")
    public PatientDto changePassword(@PathVariable String email, @Valid @RequestBody PasswordChangeCommand command) {
        return patientService.changePassword(email, command);
    }

    @Operation(summary = "Remove a patient")
    @ApiResponse(responseCode = "204", description = "Patient removed or never existed")
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String email) {
        patientService.delete(email);
    }
}