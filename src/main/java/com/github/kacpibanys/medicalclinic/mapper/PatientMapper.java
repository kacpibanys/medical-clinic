package com.github.kacpibanys.medicalclinic.mapper;

import com.github.kacpibanys.medicalclinic.dto.PatientCreateCommand;
import com.github.kacpibanys.medicalclinic.dto.PatientDto;
import com.github.kacpibanys.medicalclinic.dto.PatientUpdateCommand;
import com.github.kacpibanys.medicalclinic.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDto toDto(Patient patient);
    Patient toEntity(PatientCreateCommand command);
    void update(PatientUpdateCommand command, @MappingTarget Patient patient);
}