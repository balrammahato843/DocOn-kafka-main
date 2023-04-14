package com.spring.docon.service;

import com.spring.docon.entity.AccountEntity;
import com.spring.docon.mapper.AccountMapper;
import com.spring.docon.model.Account;
import com.spring.docon.repository.AccountRepository;
import com.spring.docon.response.AccountResponse;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountResponse addAccount(Account account) {
        AccountEntity accountEntity = accountMapper.modelToEntity(account);
        accountRepository.save(accountEntity);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountId(accountEntity.getAccountId());

        return accountResponse;

    }
}
