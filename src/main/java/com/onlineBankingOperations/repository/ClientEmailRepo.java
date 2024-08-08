package com.onlineBankingOperations.repository;

import com.onlineBankingOperations.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientEmailRepo extends JpaRepository<Email, Long> {

    Optional<Email> findByEmail(String email);
}
