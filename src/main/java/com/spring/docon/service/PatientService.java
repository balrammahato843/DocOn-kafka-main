package com.spring.docon.service;

import com.spring.docon.entity.AccountEntity;
import com.spring.docon.entity.PatientEntity;
import com.spring.docon.entity.UserRegisterEntity;
import com.spring.docon.mapper.PatientMapper;
import com.spring.docon.model.Enrollment;
import com.spring.docon.model.Patient;
import com.spring.docon.repository.AccountRepository;
import com.spring.docon.repository.PatientRepository;
import com.spring.docon.repository.UserRepository;
import com.spring.docon.response.PatientResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PatientService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final EnrollmentService enrollmentService;
    private final Enrollment enrollment = new Enrollment();
    private PatientEntity patientEntity;

    @Autowired
    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper, EnrollmentService enrollmentService, AccountRepository accountRepository, UserRepository userRepository) {
        this.patientMapper = patientMapper;
        this.patientRepository = patientRepository;
        this.enrollmentService = enrollmentService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public PatientResponse addPatient(Patient patient) {
        patientEntity = patientMapper.modelToEntity(patient);
        patientRepository.save(patientEntity);
        log.info("Patient details saved successfully.");

        enrollmentService.createEnrollment(patientEntity.getPatientId(), enrollment);
        log.info("Create enrollment method has been called.");

        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setPatientId(patientEntity.getPatientId());
        log.info("Response id : {}", patientResponse.getPatientId());

        return patientResponse;
    }


    public List<Patient> getAllPatients() {
        List<PatientEntity> personEntity = patientRepository.findAll();
        List<Patient> patientRequest = patientMapper.entityToModels(personEntity);
        return patientRequest;
    }

    public Patient getPatient(Long patientId) {
        PatientEntity patientEntity1 = patientRepository.findById(patientId).get();
        Patient patient = patientMapper.entityToModel(patientEntity1);
        return patient;
    }

    public void deletePatient(Long patientId) {
        patientRepository.findByPatientIdAndDeleteFalse(patientId);
    }

    public Patient updatePatient(Long patientId, Patient patient) {

        PatientEntity oldPatientEntity = patientRepository.findById(patientId).get();

        UserRegisterEntity userRegisterEntity = oldPatientEntity.getUser();
        Long userId = userRegisterEntity.getUserId();

        AccountEntity accountEntity = oldPatientEntity.getUser().getAccount();
        Long accountId = accountEntity.getAccountId();

        PatientEntity newPatientEntity = patientMapper.modelToEntity(patient);
        newPatientEntity.setPatientId(patientId);
        newPatientEntity.getUser().setUserId(userId);
        newPatientEntity.getUser().getAccount().setAccountId(accountId);
//        accountRepository.save(newPatientEntity.getUser().getAccount());
//        userRepository.save(newPatientEntity.getUser());
        PatientEntity patientEntity1 = patientRepository.save(newPatientEntity);

        Patient patient1 = patientMapper.entityToModel(patientEntity1);
        return patient1;

    }

}
