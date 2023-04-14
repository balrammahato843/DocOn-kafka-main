package com.spring.docon.controller;

import com.spring.docon.entity.AccountEntity;
import com.spring.docon.model.Account;
import com.spring.docon.model.UserRegister;
import com.spring.docon.repository.UserRepository;
import com.spring.docon.response.UserResponse;
import com.spring.docon.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRegisterService userRegisterService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRegisterService userRegisterService,
                          UserRepository userRepository) {
        this.userRegisterService = userRegisterService;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRegister userRegister) {
        UserResponse userResponse = userRegisterService.addUser(userRegister);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

 /*   @GetMapping(path = "/account/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId){
        Account account=userRegisterService.getAccounts(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);

    }*/


    @GetMapping(value = "/accounts/{accountId}/users")
    public ResponseEntity<List<UserRegister>> getAllUsersByAccountId(@PathVariable Long accountId){
        List<UserRegister> userRegister = userRegisterService.getAllUsersByAccountId(accountId);
        return new ResponseEntity<>(userRegister , HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<UserRegister> getUserById(@PathVariable Long userId)
    {
        UserRegister userRegister = userRegisterService.getUserById(userId);
        return new ResponseEntity<>(userRegister , HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}")
    public void deleteUser(@PathVariable Long userId)
    {
        userRegisterService.deleteUser(userId);
    }

    @PostMapping(value = "/accounts/{accountId}/users")
    public ResponseEntity<UserResponse> addUsersByAccountId(@PathVariable Long accountId , @RequestBody UserRegister userRegister)
    {
        UserResponse userResponse = userRegisterService.addUserByAccountId(accountId, userRegister);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }



}
