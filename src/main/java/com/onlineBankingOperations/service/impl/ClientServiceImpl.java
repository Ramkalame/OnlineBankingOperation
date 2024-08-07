package com.onlineBankingOperations.service.impl;

import com.onlineBankingOperations.entity.Account;
import com.onlineBankingOperations.entity.Client;
import com.onlineBankingOperations.entity.dtos.LoginRequest;
import com.onlineBankingOperations.entity.dtos.RegistrationRequest;
import com.onlineBankingOperations.exception.UserAlreadyExistException;
import com.onlineBankingOperations.repository.AccountRepo;
import com.onlineBankingOperations.repository.ClientRepo;
import com.onlineBankingOperations.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final AccountRepo accountRepo;

    @Override
    public String signUpClient(RegistrationRequest registrationRequest) {

        if(clientRepo.findByEmail(String.valueOf(registrationRequest.getEmail())) != null){
            throw new UserAlreadyExistException("User already exist with this email: "+ registrationRequest.getEmail());
        }
        if(clientRepo.findByMobileNumber(String.valueOf(registrationRequest.getMobileNumber())) != null){
            throw new UserAlreadyExistException("User already exist with this mobile number: " + registrationRequest.getMobileNumber());
        }

        Account newAccount = Account.builder()
                .initialBalance(registrationRequest.getInitialBalance())
                .currentBalance(registrationRequest.getInitialBalance())
                .build();

        Client newClient = Client.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .mobileNumber(registrationRequest.getMobileNumber())
                .password(registrationRequest.getPassword())
                .dateOfBirth(registrationRequest.getDateOfBirth())
                .account(newAccount)
                .build();
        clientRepo.save(newClient);
        return "Client is Registered Successfully !!";
    }

    @Override
    public String signInClient(LoginRequest loginRequest) {
        return "";
    }

    @Override
    public String addNewMobileNumber(Long clientId, String newMobileNumber) {
        return "";
    }

    @Override
    public String addNewEmail(Long clientId, String newEmail) {
        return "";
    }

    @Override
    public String editMobileNumber(Long clientId, String modifiedMobileNumber) {
        return "";
    }

    @Override
    public String editEmail(Long clientId, String modifiedEmail) {
        return "";
    }
}
