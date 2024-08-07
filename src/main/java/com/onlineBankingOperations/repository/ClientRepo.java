package com.onlineBankingOperations.repository;

import com.onlineBankingOperations.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    Client findByMobileNumber(String mobileNumber);
}
