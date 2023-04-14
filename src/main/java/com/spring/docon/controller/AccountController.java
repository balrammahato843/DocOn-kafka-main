package com.spring.docon.controller;


import com.spring.docon.model.Account;
import com.spring.docon.response.AccountResponse;
import com.spring.docon.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "*")
public class AccountController {


    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountResponse> addAccount(@RequestBody Account account)
    {
        AccountResponse accountResponse = accountService.addAccount(account);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

}
