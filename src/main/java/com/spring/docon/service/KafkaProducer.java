package com.spring.docon.service;

import com.spring.docon.entity.AccountEntity;
import com.spring.docon.entity.EnrollmentEntity;
import com.spring.docon.entity.PatientEntity;
import com.spring.docon.entity.UserRegisterEntity;
import com.spring.docon.repository.PatientRepository;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@ToString
public class KafkaProducer {

    private final KafkaTemplate<String, UserRegisterEntity> userRegisterEntityKafkaTemplate;

    private final KafkaTemplate<String, AccountEntity> accountEntityKafkaTemplate;

    private final KafkaTemplate<String, EnrollmentEntity> enrollmentEntityKafkaTemplate;

    private UserRegisterEntity userRegisterEntity = new UserRegisterEntity();

    private AccountEntity accountEntity = new AccountEntity();

    private PatientEntity patientEntity = new PatientEntity();

    private PatientRepository patientRepository;

    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, UserRegisterEntity> userRegisterEntityKafkaTemplate, KafkaTemplate<String, AccountEntity> accountEntityKafkaTemplate, KafkaTemplate<String, EnrollmentEntity> enrollmentEntityKafkaTemplate, PatientRepository patientRepository) {
        this.userRegisterEntityKafkaTemplate = userRegisterEntityKafkaTemplate;
        this.accountEntityKafkaTemplate = accountEntityKafkaTemplate;
        this.enrollmentEntityKafkaTemplate = enrollmentEntityKafkaTemplate;
        this.patientRepository = patientRepository;
    }

    public void producer(EnrollmentEntity enrollmentEntity) {

        log.info("Retrieving entity details to send to the kafka.");

        PatientEntity patientEntity1 = patientRepository.findById(enrollmentEntity.getPatientEntity().getPatientId()).get();
        log.info("Patient details found of id {}", patientEntity1.getPatientId());

        userRegisterEntity = enrollmentEntity.getPatientEntity().getUser();
        accountEntity=enrollmentEntity.getPatientEntity().getUser().getAccount();

        log.info("Sending details to kafka topic {}.", topicName);
        enrollmentEntityKafkaTemplate.send(topicName, "enrollment", enrollmentEntity);
    }
}
