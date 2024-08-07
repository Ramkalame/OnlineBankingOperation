package com.onlineBankingOperations.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponse<T> {

    private T data;
    private int statusCode;
    private String message;
    private LocalDateTime timeStamp;
    private boolean success;
}
