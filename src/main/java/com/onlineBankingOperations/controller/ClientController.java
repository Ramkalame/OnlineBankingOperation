package com.onlineBankingOperations.controller;

import com.onlineBankingOperations.entity.dtos.RegistrationRequest;
import com.onlineBankingOperations.service.ClientService;
import com.onlineBankingOperations.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody RegistrationRequest registrationRequest){
        String data = clientService.signUpClient(registrationRequest);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(data)
                .statusCode(HttpStatus.CREATED.value())
                .message("User registered successfully !!")
                .timeStamp(LocalDateTime.now())
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
