package com.onlineBankingOperations.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account")
@Entity
@ToString(exclude = "client")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    private Double initialBalance;
    private Double currentBalance;

    @OneToOne(mappedBy = "account")
    @JsonIgnore
    private Client client;
}
