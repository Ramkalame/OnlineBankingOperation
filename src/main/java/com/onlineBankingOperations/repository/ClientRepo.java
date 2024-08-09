package com.onlineBankingOperations.repository;

import com.onlineBankingOperations.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {


    @Query("SELECT c FROM Client c JOIN c.emails e WHERE e.email =:email")
    Client findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Client c JOIN c.mobileNumbers m WHERE m.mobileNumber =:mobileNumber")
    Client findByMobileNumber(@Param("mobileNumber") String mobileNumber);

    @Query("SELECT c FROM Client c WHERE c.dateOfBirth =:dateOfBirth")
    Page<Client> searchClientByDateOfBirth(@Param("dateOfBirth") LocalDate dateOfBirth, Pageable pageable);

    @Query("SELECT c FROM Client c JOIN c.mobileNumbers m WHERE m.mobileNumber LIKE :mobileNumber")
    Page<Client> searchClientByMobileNumber(@Param("mobileNumber") String mobileNumber, Pageable pageable);

    @Query("SELECT c FROM Client c WHERE c.name LIKE :name")
    Page<Client> searchClientByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT c FROM Client c JOIN c.emails e WHERE e.email LIKE :email")
    Page<Client> searchClientByEmail(@Param("email") String email, Pageable pageable);

}
