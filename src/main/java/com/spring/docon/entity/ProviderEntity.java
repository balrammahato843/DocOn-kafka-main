package com.spring.docon.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "provider")
public class ProviderEntity {
    @Id
    @SequenceGenerator(name = "provider_seq_id", sequenceName = "provider_seq_id", initialValue = 500, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_seq_id")


    @Column(name = "provider_id")
    private Long providerId;

    @Column(name = "mci_registration_number")
    private String mciRegistrationNumber;

    @Column(name = "experience")
    private String experience;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserRegisterEntity user;
}

