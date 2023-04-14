package com.spring.docon.mapper;

import com.spring.docon.entity.UserRegisterEntity;
import com.spring.docon.model.UserRegister;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper {

//    @Mapping(source = "account", target = "account")
    UserRegisterEntity modelToEntity(UserRegister userRegister);

    List<UserRegister> entityToModels(List<UserRegisterEntity> userRegister);

    UserRegister entityToModel (UserRegisterEntity userRegister);



}
