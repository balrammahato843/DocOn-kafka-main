package com.spring.docon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "patient")
public class PatientEntity {

    @Id
    @GeneratedValue
    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    private Boolean deleted = Boolean.FALSE;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserRegisterEntity user;

}
