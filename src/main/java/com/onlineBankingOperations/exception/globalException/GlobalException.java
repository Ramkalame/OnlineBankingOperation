package com.onlineBankingOperations.exception.globalException;

import com.onlineBankingOperations.exception.AtLeastOneEmailRequiredException;
import com.onlineBankingOperations.exception.UserAlreadyExistException;
import com.onlineBankingOperations.exception.UserNotFoundException;
import com.onlineBankingOperations.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException ex){

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .success(false)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(AtLeastOneEmailRequiredException.class)
    public ResponseEntity<?> atLeastOneEmailRequiredExceptionHandler(AtLeastOneEmailRequiredException ex){

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .success(false)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementExceptionHandler(NoSuchElementException ex){

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .success(false)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptionHandler(BadCredentialsException ex){

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .success(false)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }
}
