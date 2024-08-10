package com.onlineBankingOperations.controller;

import com.onlineBankingOperations.entity.Client;
import com.onlineBankingOperations.entity.dtos.RegistrationRequest;
import com.onlineBankingOperations.exception.UserNotFoundException;
import com.onlineBankingOperations.service.AccountService;
import com.onlineBankingOperations.service.ClientService;
import com.onlineBankingOperations.utils.ApiResponse;
import com.onlineBankingOperations.utils.PaginationResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final AccountService accountService;

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

    @DeleteMapping("/{clientId}/deleteMobileNumber")
    public ResponseEntity<ApiResponse<String>> deleteMobileNumber(
            @PathVariable Long clientId,
            @RequestParam String mobileNumber) {

        String message = clientService.deleteMobileNumber(clientId, mobileNumber);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(message)
                .statusCode(HttpStatus.OK.value())
                .message("Mobile number deleted successfully !")
                .timeStamp(LocalDateTime.now())
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{clientId}/deleteEmail")
    public ResponseEntity<ApiResponse<String>> deleteEmail(
            @PathVariable Long clientId,
            @RequestParam String email) {

        String message = clientService.deleteEmail(clientId, email);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(message)
                .statusCode(HttpStatus.OK.value())
                .message("Email deletion successfully")
                .timeStamp(LocalDateTime.now())
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PaginationResponse>> searchClients(
            @RequestParam(value = "dateOfBirth", required = false) LocalDate dateOfBirth,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "mobileNumber", required = false) String mobileNumber,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
    ){

        PaginationResponse data = clientService.searchClients(Optional.ofNullable(dateOfBirth),
                Optional.ofNullable(name),
                Optional.ofNullable(mobileNumber),
                Optional.ofNullable(email),
                pageNumber,
                pageSize);
        ApiResponse<PaginationResponse> response = ApiResponse.<PaginationResponse>builder()
                .data(data)
                .statusCode(HttpStatus.OK.value())
                .message("Here is list of client results")
                .timeStamp(LocalDateTime.now())
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/transfer-money")
    public ResponseEntity<ApiResponse<String>> transferMoney(
            @RequestParam Long senderClientId,
            @RequestParam Long receiverClientId,
            @RequestParam Double money) {


            String data = accountService.transferMoney(senderClientId, receiverClientId, money);
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .data(data)
                    .statusCode(HttpStatus.OK.value())
                    .message("Transferred successfully")
                    .timeStamp(LocalDateTime.now())
                    .success(true)
                    .build();
            return ResponseEntity.ok(response);

    }

}
