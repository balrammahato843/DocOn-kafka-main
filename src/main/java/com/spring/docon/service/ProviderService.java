package com.spring.docon.service;


import com.spring.docon.entity.AccountEntity;
import com.spring.docon.entity.ProviderEntity;
import com.spring.docon.entity.UserRegisterEntity;
import com.spring.docon.mapper.ProviderMapper;
import com.spring.docon.model.Provider;
import com.spring.docon.repository.ProviderRepository;
import com.spring.docon.response.ProviderResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Log4j2
@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    private final ProviderMapper providerMapper;

    @Autowired
    public ProviderService(ProviderRepository providerRepository, ProviderMapper providerMapper) {

        this.providerRepository = providerRepository;

        this.providerMapper = providerMapper;
    }

    public ProviderResponse addProvider(Provider provider) {
        ProviderEntity providerEntity = providerMapper.modelToEntity(provider);
        providerRepository.save(providerEntity);
        log.info("Provider details saved successfully.");

        ProviderResponse providerResponse = new ProviderResponse();
        providerResponse.setProviderId(providerEntity.getProviderId());
        log.info("Response id : {}", providerResponse.getProviderId());
        return providerResponse;
    }


    public Provider getById(Long providerId) {
        Provider provider = new Provider();
        Optional<ProviderEntity> providerEntity = providerRepository.findById(providerId);
        if (providerEntity.isPresent()) {
            provider = providerMapper.entityToModel(providerEntity.get());
            log.info("person" + providerId + "successfull get");
        } else {
            log.info("person" + providerId + "not found");

        }
        return provider;

    }

    public List<Provider> getAllprovider() {
        log.info("Fetching all Providers");
        List<ProviderEntity> providerEntity = providerRepository.findAll();
        return providerMapper.entityToModels(providerEntity);
    }

    public void deleteProviderById(Long providerId) {
        Optional<ProviderEntity> providerEntity = providerRepository.findById(providerId);
        if (providerEntity.isPresent()) {
            providerRepository.deleteById(providerId);
            log.info("deleted successfully");
        } else {
            log.info("person" + providerId + "not found");
        }


    }


    public Provider updateProviders(Long providerId, Provider provider) {
        ProviderEntity oldProviderEntity = providerRepository.findById(providerId).get();

        UserRegisterEntity userRegisterEntity = oldProviderEntity.getUser();
        Long userId = userRegisterEntity.getUserId();

        AccountEntity accountEntity = oldProviderEntity.getUser().getAccount();
        Long accountId = accountEntity.getAccountId();

        ProviderEntity newProviderEntity = providerMapper.modelToEntity(provider);
        newProviderEntity.setProviderId(providerId);
        newProviderEntity.getUser().setUserId(userId);
        newProviderEntity.getUser().getAccount().setAccountId(accountId);
        ;
        ProviderEntity providerEntity1 = providerRepository.save(newProviderEntity);

        Provider provider1 = providerMapper.entityToModel(providerEntity1);

        return provider1;
    }
}

