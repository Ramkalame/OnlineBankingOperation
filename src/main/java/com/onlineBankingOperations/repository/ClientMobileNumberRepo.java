package com.onlineBankingOperations.repository;

import com.onlineBankingOperations.entity.MobileNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientMobileNumberRepo extends JpaRepository<MobileNumber, Long> {

    Optional<MobileNumber> findByMobileNumber(String mobileNumber);
//
//    @Query("SELECT m FROM MobileNumber m WHERE m.clientId =:clientId" )
//    Optional<MobileNumber> findByClientId(@Param("clientId") Long clientId);
}
