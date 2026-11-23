package com.github.kacpibanys.medicalclinic.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ClinicException.class)
    public ProblemDetail handleClinic(ClinicException exception) {
        log.warn("Domain error: {}", exception.getError());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                exception.getError().getStatus(),
                exception.getMessage()
        );
        problem.setProperty("code", exception.getError().name());
        return problem;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<FieldError> fieldErrors = exception.getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField)
                        .thenComparing(FieldError::getDefaultMessage))
                .toList();

        log.warn("Client error {}: invalid fields [{}]", status.value(), fieldErrors.stream()
                .map(FieldError::getField)
                .distinct()
                .collect(Collectors.joining(", ")));

        ProblemDetail problem = exception.getBody();
        problem.setProperty("errors", fieldErrors.stream()
                .map(error -> new FieldViolation(error.getField(), error.getDefaultMessage()))
                .toList());

        return super.handleExceptionInternal(exception, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        log.warn("Client error {}: {}", statusCode.value(), exception.getClass().getSimpleName());
        return super.handleExceptionInternal(exception, body, headers, statusCode, request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnknown(Exception exception) {
        log.error("Unknown error", exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
    }
}
