package com.spring.docon.service;

import com.spring.docon.entity.EnrollmentEntity;
import com.spring.docon.entity.PatientEntity;
import com.spring.docon.mapper.EnrollmentMapper;
import com.spring.docon.model.Enrollment;
import com.spring.docon.repository.EnrollmentRepository;
import com.spring.docon.repository.PatientRepository;
import com.spring.docon.response.EnrollmentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@Log4j2
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final EnrollmentMapper enrollmentMapper;

    private final PatientRepository patientRepository;

    private final KafkaProducer kafkaProducer;

    private final EnrollmentResponse enrollmentResponse = new EnrollmentResponse();

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, EnrollmentMapper enrollmentMapper, PatientRepository patientRepository, KafkaProducer kafkaProducer) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentMapper = enrollmentMapper;
        this.patientRepository = patientRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public EnrollmentResponse createEnrollment(Long patientId, Enrollment enrollment) {

        log.info("Retrieving patient details");

        Optional<PatientEntity> optionalPatientEntity = patientRepository.findById(patientId);
        log.info("Patient details found for id {}", patientId);

        if (optionalPatientEntity.isPresent()) {
            EnrollmentEntity enrollmentEntity = enrollmentMapper.modelToEntity(enrollment);
            enrollmentEntity.setPatientEntity(optionalPatientEntity.get());

            Duration duration = Duration.ofDays(15);
            enrollmentEntity.setExpiry(duration);

            enrollmentRepository.save(enrollmentEntity);
            log.info("Enrollment details saved successfully.");

            enrollmentResponse.setEnrollmentID(enrollmentEntity.getEnrollmentId());
            log.info("Response id : {}", enrollmentResponse.getEnrollmentID());

          //  kafkaProducer.producer(enrollmentEntity);
        }
        return enrollmentResponse;
    }
}
