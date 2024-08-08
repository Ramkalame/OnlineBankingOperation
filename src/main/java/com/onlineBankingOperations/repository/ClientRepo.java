package com.onlineBankingOperations.repository;

import com.onlineBankingOperations.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {


    @Query("SELECT c FROM Client c JOIN c.emails e WHERE e.email =:email")
    Client findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Client c JOIN c.mobileNumbers m WHERE m.mobileNumber =:mobileNumber")
    Client findByMobileNumber(@Param("mobileNumber") String mobileNumber);

}
