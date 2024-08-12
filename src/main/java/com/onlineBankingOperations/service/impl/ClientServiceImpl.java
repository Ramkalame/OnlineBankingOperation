package com.onlineBankingOperations.service.impl;

import com.onlineBankingOperations.config.JWTService;
import com.onlineBankingOperations.entity.*;
import com.onlineBankingOperations.entity.dtos.LoginRequest;
import com.onlineBankingOperations.entity.dtos.RegistrationRequest;
import com.onlineBankingOperations.exception.AtLeastOneEmailRequiredException;
import com.onlineBankingOperations.exception.UserAlreadyExistException;
import com.onlineBankingOperations.exception.UserNotFoundException;
import com.onlineBankingOperations.repository.*;
import com.onlineBankingOperations.service.ClientService;
import com.onlineBankingOperations.utils.PaginationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final ClientEmailRepo clientEmailRepo;
    private final ClientMobileNumberRepo clientMobileNumberRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager ;
    private final JWTService jwtService;

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
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .dateOfBirth(registrationRequest.getDateOfBirth())
                .account(newAccount)
                .build();
        clientRepo.save(newClient);
        return "Client is Registered Successfully !!";
    }

    private boolean isValidPassword(Client client, String password) {
        return passwordEncoder.matches(password, client.getPassword());
    }

    private boolean isEmailAssociated(Client client, String email) {
        return client.getEmails().stream()
                .anyMatch(e -> e.getEmail().equals(email));
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
    public String deleteMobileNumber(Long clientId, String mobileNumber) {
        MobileNumber existingMobileNumber = clientMobileNumberRepo.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new UserNotFoundException("User NOT FOUND with this id: "+ mobileNumber));
        Client existingClient = clientRepo.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException("Client NOT FOUND with this id: " + clientId));
        List<MobileNumber> listOfMobileNumber = existingClient.getMobileNumbers();
        if(listOfMobileNumber.size() >1){
            listOfMobileNumber.removeIf(e->e.getMobileNumber().equals(mobileNumber));
            clientRepo.save(existingClient);
            clientMobileNumberRepo.delete(existingMobileNumber);
            return "Mobile number deleted successfully !";

        }else {
            throw new AtLeastOneEmailRequiredException("At least one mobile number should be present for the client !");
        }

    }

    @Override
    public String deleteEmail(Long clientId, String email) {
        Email existingEmail = clientEmailRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User NOT FOUND with this id: " + email));
        Client existingClient = clientRepo.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException("Client NOT FOUND with this id: " + clientId));
        List<Email> listOfEmail = existingClient.getEmails();
        if (listOfEmail.size() > 1) {
            listOfEmail.removeIf(e -> e.getEmail().equals(email));
            clientRepo.save(existingClient);
            clientEmailRepo.delete(existingEmail);
            return "Email deleted successfully!";
        } else {
            throw new AtLeastOneEmailRequiredException("At least one email should be present for the client !");
        }
    }

    @Override
    public PaginationResponse searchClients(Optional<LocalDate> dateOfBirth, Optional<String> name, Optional<String> mobileNumber, Optional<String> email, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);


        if(dateOfBirth.isPresent()){
             Page<Client> pageClientsDateOfBirth = clientRepo.searchClientByDateOfBirth(dateOfBirth.get(), pageable);
            return PaginationResponse.builder()
                    .content(pageClientsDateOfBirth.getContent())
                    .pageNumber(pageClientsDateOfBirth.getNumber())
                    .pageSize(pageClientsDateOfBirth.getSize())
                    .totalElement(pageClientsDateOfBirth.getTotalElements())
                    .totalPage(pageClientsDateOfBirth.getTotalPages())
                    .lastPage(pageClientsDateOfBirth.isLast())
                    .build();
        } else if (name.isPresent()) {
            Page<Client> pageClientsName = clientRepo.searchClientByName("%"+name.get()+"%", pageable);
            return PaginationResponse.builder()
                    .content(pageClientsName.getContent())
                    .pageNumber(pageClientsName.getNumber())
                    .pageSize(pageClientsName.getSize())
                    .totalElement(pageClientsName.getTotalElements())
                    .totalPage(pageClientsName.getTotalPages())
                    .lastPage(pageClientsName.isLast())
                    .build();
        }else if(mobileNumber.isPresent()){
            Page<Client> pageClientsMobileNumber = clientRepo.searchClientByMobileNumber("%"+mobileNumber.get()+"%", pageable);
            return PaginationResponse.builder()
                    .content(pageClientsMobileNumber.getContent())
                    .pageNumber(pageClientsMobileNumber.getNumber())
                    .pageSize(pageClientsMobileNumber.getSize())
                    .totalElement(pageClientsMobileNumber.getTotalElements())
                    .totalPage(pageClientsMobileNumber.getTotalPages())
                    .lastPage(pageClientsMobileNumber.isLast())
                    .build();
        }else if(email.isPresent()){
            Page<Client> pageClientsEmail = clientRepo.searchClientByEmail("%"+email.get()+"%", pageable);
            return PaginationResponse.builder()
                    .content(pageClientsEmail.getContent())
                    .pageNumber(pageClientsEmail.getNumber())
                    .pageSize(pageClientsEmail.getSize())
                    .totalElement(pageClientsEmail.getTotalElements())
                    .totalPage(pageClientsEmail.getTotalPages())
                    .lastPage(pageClientsEmail.isLast())
                    .build();
        }else{
            throw new NoSuchElementException("There is no client available");
        }

    }

    @Override
    public String authenticate(LoginRequest loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginDto.getUsername());
        } else {
            return "fail";
        }
    }
}
