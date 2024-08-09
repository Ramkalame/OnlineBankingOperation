package com.onlineBankingOperations.service;

import com.onlineBankingOperations.entity.Client;
import com.onlineBankingOperations.entity.dtos.LoginRequest;
import com.onlineBankingOperations.entity.dtos.RegistrationRequest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    String signUpClient(RegistrationRequest registrationRequest);
    String signInClient(LoginRequest loginRequest);
    String addNewMobileNumber(Long clientId, String newMobileNumber);
    String addNewEmail(Long clientId, String newEmail);
    String editMobileNumber(String oldMobileNumber, String modifiedMobileNumber);
    String editEmail(String oldEmail, String modifiedEmail);
    String deleteMobileNumber(Long clientId, String mobileNumber);
    String deleteEmail(Long clientId, String email);
    Page<Client> searchClients(Optional<LocalDate> dateOfBirth,
                               Optional<String> name,
                               Optional<String> mobileNumber,
                               Optional<String> email,
                               Integer pageNumber,
                               Integer pageSize);

}
