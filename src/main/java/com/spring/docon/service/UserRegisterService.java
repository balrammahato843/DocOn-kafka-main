package com.spring.docon.service;

import com.spring.docon.entity.AccountEntity;
import com.spring.docon.entity.UserRegisterEntity;
import com.spring.docon.mapper.AccountMapper;
import com.spring.docon.mapper.UserRegisterMapper;
import com.spring.docon.model.Account;
import com.spring.docon.model.UserRegister;
import com.spring.docon.repository.AccountRepository;
import com.spring.docon.repository.UserRepository;
import com.spring.docon.response.UserResponse;
import com.spring.docon.util.PasswordGenerator;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserRegisterService {

    private final UserRegisterMapper userRegisterMapper;
    private final UserRepository userRepository;
    private final AccountRepository accountEntityRepository;
    private final AccountMapper accountMapper;
    @Autowired
    private final KafkaTemplate<String, JSONObject> kafkaTemplate;
    UserRegisterEntity userRegisterEntity;
    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    public UserRegisterService(UserRegisterMapper userRegisterMapper, UserRepository userRepository, AccountRepository accountEntityRepository, AccountMapper accountMapper, KafkaTemplate<String, JSONObject> kafkaTemplate) {
        this.userRegisterMapper = userRegisterMapper;
        this.userRepository = userRepository;
        this.accountEntityRepository = accountEntityRepository;
        this.accountMapper = accountMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public UserResponse addUser(UserRegister userRegister) {
        userRegisterEntity = userRegisterMapper.modelToEntity(userRegister);

        String randomPassword = PasswordGenerator.generateRandomPassword(8);
        log.info("Random password {}", randomPassword);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setPassword(randomPassword);
//        accountEntity.setEmailId(userRegister.getAccount().getEmailId());

        userRegisterEntity.setAccount(accountEntity);
        accountEntityRepository.save(accountEntity);

        userRepository.save(userRegisterEntity);
        log.info("user details saved successfully");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserResponseId(userRegisterEntity.getUserId());

        if (userRegisterEntity.getAccount().getAccountId() != null) {
            Long accountId = userRegisterEntity.getAccount().getAccountId();
            log.info(accountId + " account id");
            getAccounts(accountId);
        }

        return userResponse;
    }

    public Account getAccounts(Long accountId) {
        Optional<AccountEntity> accountEntityOptional = accountEntityRepository.findById(userRegisterEntity.getAccount().getAccountId());

        Account account = new Account();
        if (accountEntityOptional.isPresent()) {
            account = accountMapper.entityToModel(accountEntityOptional.get());
            log.info("Account details found");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", account);
            kafkaTemplate.send(topicName, jsonObject);
            log.info("Sending account details to kafka with topic name {}", topicName);
        }
        return account;
    }

//    public List<UserRegister> getAllUsersByAccountId(Long accountId) {
//        List<UserRegisterEntity> userRegisterEntities = userRepository.findAll();
//        List<UserRegister> userRegister = userRegisterMapper.entityToModels(userRegisterEntities);
//        return userRegister;
//    }

    public List<UserRegister> getAllUsersByAccountId(Long accountId) {
        List<UserRegisterEntity> userRegisterEntities = userRepository.findByAccountId(accountId);
        List<UserRegister> userRegisters = userRegisterMapper.entityToModels(userRegisterEntities);
        return userRegisters;
    }


    public UserRegister getUserById(Long userId) {
        UserRegisterEntity userRegisterEntity1 = userRepository.findById(userId).get();
        UserRegister userRegister = userRegisterMapper.entityToModel(userRegisterEntity1);
        return userRegister;
    }

    public void deleteUser(Long userId) {
        userRepository.findByUserIdAndDeleteFalse(userId);
    }

    public UserResponse addUserByAccountId(Long accountId, UserRegister userRegister) {

        Optional<AccountEntity> account=accountEntityRepository.findById(accountId);
        UserRegisterEntity userRegisterEntity1=new UserRegisterEntity();

        if(account.isPresent()){
            userRegisterEntity1=userRegisterMapper.modelToEntity(userRegister);
            userRegisterEntity1.setAccount(account.get());
            userRepository.save(userRegisterEntity1);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setUserResponseId(userRegisterEntity1.getUserId());
        return  userResponse;


    }
}
