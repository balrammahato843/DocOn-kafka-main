package com.spring.docon.repository;

import com.spring.docon.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query(value = "select emailId from AccountEntity a where a.accountId=?1 ")
    AccountEntity findByAccountId();

}
