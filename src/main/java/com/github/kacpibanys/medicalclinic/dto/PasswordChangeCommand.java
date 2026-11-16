package com.github.kacpibanys.medicalclinic.dto;

public record PasswordChangeCommand(String password) {
    @Override
    public String toString() {
        return "PasswordChangeCommand[password=***]";
    }
}