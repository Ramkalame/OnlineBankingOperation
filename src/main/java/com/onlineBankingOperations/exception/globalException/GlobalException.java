package com.onlineBankingOperations.exception.globalException;

import com.onlineBankingOperations.exception.UserAlreadyExistException;
import com.onlineBankingOperations.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> userAlreadyExistExceptionHandler(UserAlreadyExistException ex){

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .success(false)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }
}
