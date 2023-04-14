package com.spring.docon.repository;

import com.spring.docon.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    @Query("UPDATE PatientEntity a SET a.deleted =  false WHERE a.patientId = ?1")
    @Modifying
    void deletedById(Long patientId);

    @Query("SELECT p FROM PatientEntity p WHERE p.id = ?1 AND p.deleted = true")
    Optional<PatientEntity> findByPatientIdAndDeleteFalse(Long patientId);

}
