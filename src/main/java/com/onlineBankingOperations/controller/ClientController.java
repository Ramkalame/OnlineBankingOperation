package com.onlineBankingOperations.controller;

import com.onlineBankingOperations.entity.dtos.RegistrationRequest;
import com.onlineBankingOperations.service.ClientService;
import com.onlineBankingOperations.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{clientId}/addMobileNumber")
    public ResponseEntity<ApiResponse<String>> addNewMobileNumber(
            @PathVariable Long clientId,
            @RequestParam String newMobileNumber) {

        String message = clientService.addNewMobileNumber(clientId, newMobileNumber);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(message)
                .statusCode(HttpStatus.OK.value())
                .message("New mobile number added successfully!")
                .timeStamp(LocalDateTime.now())
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{clientId}/addEmail")
    public ResponseEntity<ApiResponse<String>> addNewEmail(
            @PathVariable Long clientId,
            @RequestParam String newEmail) {

        String message = clientService.addNewEmail(clientId, newEmail);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(message)
                .statusCode(HttpStatus.OK.value())
                .message("New email added successfully!")
                .timeStamp(LocalDateTime.now())
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }


    @PutMapping("/editMobileNumber")
    public ResponseEntity<ApiResponse<String>> editMobileNumber(
            @RequestParam String oldMobileNumber,
            @RequestParam String modifiedMobileNumber) {

        String message = clientService.editMobileNumber(oldMobileNumber, modifiedMobileNumber);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(message)
                .statusCode(HttpStatus.OK.value())
                .message("Mobile number updated successfully!")
                .timeStamp(LocalDateTime.now())
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/editEmail")
    public ResponseEntity<ApiResponse<String>> editEmail(
            @RequestParam String oldEmail,
            @RequestParam String modifiedEmail) {

        String message = clientService.editEmail(oldEmail, modifiedEmail);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(message)
                .statusCode(HttpStatus.OK.value())
                .message("Email updated successfully!")
                .timeStamp(LocalDateTime.now())
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

}
