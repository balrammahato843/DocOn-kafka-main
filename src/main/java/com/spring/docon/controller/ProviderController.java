package com.spring.docon.controller;

import com.spring.docon.model.Provider;
import com.spring.docon.response.ProviderResponse;
import com.spring.docon.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProviderController {


        private final ProviderService providerService;

        @Autowired
        public ProviderController(ProviderService providerService) {
            this.providerService = providerService;
        }


        @PostMapping(path = "/providers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ProviderResponse> addProvider(@RequestBody Provider provider) {
            ProviderResponse providerResponse = providerService.addProvider(provider);
            return new ResponseEntity<>(providerResponse, HttpStatus.OK);
        }


        @GetMapping(value = "/providers/{providerId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Provider> getById(@PathVariable Long providerId) {
            Provider provider = providerService.getById(providerId);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        }


        @GetMapping(value = "/providers", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<Provider>> getAllProvider() {
            List<Provider> provider = providerService.getAllprovider();
            return new ResponseEntity<>(provider, HttpStatus.OK);
        }


        @PutMapping(value = "/providers/{providerId}",produces =MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Provider> updateProvider(@PathVariable Long providerId, @RequestBody Provider provider){
            Provider provider1 = providerService.updateProviders(providerId,provider);
            return new ResponseEntity<>(provider1,HttpStatus.OK);


        }


        @DeleteMapping(value = "/providers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Void> deleteProviderById(@PathVariable Long providerId) {
            providerService.deleteProviderById(providerId);
            return ResponseEntity.ok().build();


        }
}

