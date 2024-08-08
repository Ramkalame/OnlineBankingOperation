package com.onlineBankingOperations.service;

import com.onlineBankingOperations.entity.dtos.LoginRequest;
import com.onlineBankingOperations.entity.dtos.RegistrationRequest;

public interface ClientService {

    String signUpClient(RegistrationRequest registrationRequest);
    String signInClient(LoginRequest loginRequest);
    String addNewMobileNumber(Long clientId, String newMobileNumber);
    String addNewEmail(Long clientId, String newEmail);
    String editMobileNumber(String oldMobileNumber, String modifiedMobileNumber);
    String editEmail(String oldEmail, String modifiedEmail);
    String deleteMobileNumber(String mobileNumber);
    String deleteEmail(String email);

}
