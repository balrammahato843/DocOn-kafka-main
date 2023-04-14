package com.spring.docon.mapper;

import com.spring.docon.entity.PatientEntity;
import com.spring.docon.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "user", target = "user")
//    @Mapping(source = "user.account", target = "user.account")


    PatientEntity modelToEntity(Patient patient);


    List<Patient> entityToModels(List<PatientEntity> patient);

    Patient entityToModel(PatientEntity patient);
}
