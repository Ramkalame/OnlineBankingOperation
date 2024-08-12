package com.onlineBankingOperations.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "client")
@Entity
@ToString(exclude = "account")
public class Client{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)//using this property we  can not send the password from the backend to frontend side for the security purpose
    private String password;
    private LocalDate dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private List<Email> emails = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private List<MobileNumber> mobileNumbers = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;


    public void addMobileNumber(MobileNumber mobileNumber){
        this.mobileNumbers.add(mobileNumber);
    }

    public void addEmail(Email email){
        this.emails.add(email);
    }

}
