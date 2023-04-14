package com.spring.docon.mapper;

import com.spring.docon.entity.AccountEntity;
import com.spring.docon.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEntity modelToEntity(Account account);
    Account entityToModel(AccountEntity accountEntity);
}
