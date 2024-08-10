package com.onlineBankingOperations.entity.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class RegistrationRequest {

    private String name;
    private String email;
    private String mobileNumber;
    private String password;
    private LocalDate dateOfBirth;
    private Double initialBalance;


}
