package com.spring.docon.repository;

import com.spring.docon.entity.UserRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserRegisterEntity, Long> {

    @Query(value = "select * from users u where u.account_id=?1 ",nativeQuery = true)
    List<UserRegisterEntity> findByAccountId(Long accountId);

    @Query("UPDATE PatientEntity a SET a.deleted = false WHERE a.patientId = ?1")
    @Modifying
    void deleteUser(Long userId);

    @Query("SELECT u FROM UserRegisterEntity u WHERE u.id = ?1 AND u.deleted = true")
    Optional<UserRegisterEntity> findByUserIdAndDeleteFalse(Long userId);
}
