package com.spring.docon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "account_id")
    @GeneratedValue
    private Long accountId;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "password")
    private String password;
}

