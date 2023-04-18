package com.spring.docon.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Provider {

    private String mciRegistrationNumber;
    private String experience;
    private UserRegister user;
}
