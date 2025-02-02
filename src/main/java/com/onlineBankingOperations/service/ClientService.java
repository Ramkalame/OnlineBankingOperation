package com.onlineBankingOperations.service;

import com.onlineBankingOperations.entity.Client;
import com.onlineBankingOperations.entity.dtos.LoginRequest;
import com.onlineBankingOperations.entity.dtos.RegistrationRequest;
import com.onlineBankingOperations.utils.PaginationResponse;

import java.time.LocalDate;
import java.util.Optional;

public interface ClientService {

    String signUpClient(RegistrationRequest registrationRequest);
    String addNewMobileNumber(Long clientId, String newMobileNumber);
    String addNewEmail(Long clientId, String newEmail);
    String editMobileNumber(String oldMobileNumber, String modifiedMobileNumber);
    String editEmail(String oldEmail, String modifiedEmail);
    String deleteMobileNumber(Long clientId, String mobileNumber);
    String deleteEmail(Long clientId, String email);
    String authenticate(LoginRequest loginDto);
    PaginationResponse searchClients(String searchKeywords, Integer pageNumber, Integer pageSize);

}
