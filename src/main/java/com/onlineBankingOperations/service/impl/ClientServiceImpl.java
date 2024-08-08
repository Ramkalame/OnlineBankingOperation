package com.onlineBankingOperations.service.impl;

import com.onlineBankingOperations.entity.Account;
import com.onlineBankingOperations.entity.Client;
import com.onlineBankingOperations.entity.Email;
import com.onlineBankingOperations.entity.MobileNumber;
import com.onlineBankingOperations.entity.dtos.LoginRequest;
import com.onlineBankingOperations.entity.dtos.RegistrationRequest;
import com.onlineBankingOperations.exception.UserAlreadyExistException;
import com.onlineBankingOperations.exception.globalException.UserNotFoundException;
import com.onlineBankingOperations.repository.AccountRepo;
import com.onlineBankingOperations.repository.ClientEmailRepo;
import com.onlineBankingOperations.repository.ClientMobileNumberRepo;
import com.onlineBankingOperations.repository.ClientRepo;
import com.onlineBankingOperations.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final AccountRepo accountRepo;
    private final ClientEmailRepo clientEmailRepo;
    private final ClientMobileNumberRepo clientMobileNumberRepo;

    @Transactional
    @Override
    public String signUpClient(RegistrationRequest registrationRequest) {

        if(clientEmailRepo.findByEmail(String.valueOf(registrationRequest.getEmail())).isPresent()){
            throw new UserAlreadyExistException("User already exist with this email: "+ registrationRequest.getEmail());
        }
        if(clientMobileNumberRepo.findByMobileNumber(String.valueOf(registrationRequest.getMobileNumber())).isPresent()){
            throw new UserAlreadyExistException("User already exist with this mobile number: " + registrationRequest.getMobileNumber());
        }

        Account newAccount = Account.builder()
                .initialBalance(registrationRequest.getInitialBalance())
                .currentBalance(registrationRequest.getInitialBalance())
                .build();

        Email newEmail = Email.builder()
                .email(registrationRequest.getEmail())
                .build();

        MobileNumber newMobileNumber = MobileNumber.builder()
                .mobileNumber(registrationRequest.getMobileNumber())
                .build();

        Client newClient = Client.builder()
                .name(registrationRequest.getName())
                .emails(List.of(newEmail))
                .mobileNumbers(List.of(newMobileNumber))
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

        Client existingClient = clientRepo.findById(clientId)
                .orElseThrow(()->new UserNotFoundException("User Not Found with this ID: "+ clientId));
        MobileNumber newMobileNumber1 = MobileNumber.builder().mobileNumber(newMobileNumber).build();
        existingClient.addMobileNumber(newMobileNumber1);
        clientRepo.save(existingClient);

        return "New mobile number added to this client: " +existingClient.getName();
    }

    @Override
    public String addNewEmail(Long clientId, String newEmail) {

        Client existingClient = clientRepo.findById(clientId)
                .orElseThrow(()-> new UserNotFoundException("User NOT FOUND with this id: "+ clientId));
        Email newEmail1 = Email.builder().email(newEmail).build();
        existingClient.addEmail(newEmail1);
        clientRepo.save(existingClient);

        return "New email added to this client: "+ existingClient.getName();
    }

    @Override
    public String editMobileNumber(String oldMobileNumber, String modifiedMobileNumber) {

        MobileNumber existingMobileNumber = clientMobileNumberRepo.findByMobileNumber(oldMobileNumber)
                .orElseThrow(()-> new UserNotFoundException("User NOT FOUND with this id: "+ oldMobileNumber));
        existingMobileNumber.setMobileNumber(modifiedMobileNumber);
        clientMobileNumberRepo.save(existingMobileNumber);

        return "mobile number updated";
    }

    @Override
    public String editEmail(String oldEmail, String modifiedEmail) {
        Email existingEmail = clientEmailRepo.findByEmail(oldEmail)
                .orElseThrow(()-> new UserNotFoundException("User NOT FOUND with this id: "+ oldEmail));
        existingEmail.setEmail(modifiedEmail);
        clientEmailRepo.save(existingEmail);
        return "email id updated";
    }

    @Override
    public String deleteMobileNumber(String mobileNumber) {
        return "";
    }

    @Override
    public String deleteEmail(String email) {
        return "";
    }
}
