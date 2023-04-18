package com.spring.docon.mapper;

import com.spring.docon.entity.PatientEntity;
import com.spring.docon.entity.ProviderEntity;
import com.spring.docon.model.Patient;
import com.spring.docon.model.Provider;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ProviderMapper {

    ProviderEntity modelToEntity(Provider provider);

    List<Provider> entityToModels(List<ProviderEntity> provider);

    Provider entityToModel(ProviderEntity provider);



}
