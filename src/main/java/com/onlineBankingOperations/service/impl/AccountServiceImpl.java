package com.onlineBankingOperations.service.impl;

import com.onlineBankingOperations.entity.Client;
import com.onlineBankingOperations.exception.UserNotFoundException;
import com.onlineBankingOperations.repository.ClientRepo;
import com.onlineBankingOperations.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final ClientRepo clientRepo;
    @Scheduled(cron = "0 * * * * ?")
    @Override
    public void updateClientBalance() {
        log.info("Scheduled task 'updateClientBalance' started.");

        List<Client> listOfClient = clientRepo.findAll();
        log.info("Fetched {} clients from the database.", listOfClient.size());

        List<Client> updatedClientList = listOfClient.stream().map(client -> {
            double initialBalance = client.getAccount().getInitialBalance();
            double currentBalance = client.getAccount().getCurrentBalance();
            log.info("Processing client with ID {}: initial balance = {}, current balance = {}.",
                    client.getClientId(), initialBalance, currentBalance);

            double increasesBy = 1 + ((double) 5 / 100);
            double newClientBalance = currentBalance * increasesBy;
            log.info("New calculated balance for client ID {}: {}", client.getClientId(), newClientBalance);

            if(newClientBalance > initialBalance * 2.07) {
                newClientBalance = initialBalance * 2.07;
                log.warn("Capping balance for client ID {} at {}", client.getClientId(), newClientBalance);
            }

            client.getAccount().setCurrentBalance(newClientBalance);
            Client updatedClient = clientRepo.save(client);
            log.info("Updated client with ID {}: new balance = {}", updatedClient.getClientId(), newClientBalance);

            return updatedClient;
        }).toList();

        log.info("Scheduled task 'updateClientBalance' completed. {} clients updated.", updatedClientList.size());
    }

    @Transactional
    @Override
    public String transferMoney(Long senderClientId, Long receiverClientId, Double money) {
        Client sender = clientRepo.findById(senderClientId).orElseThrow(()-> new UserNotFoundException("User with this is NOT FOUND: "+senderClientId));
        Client receiver = clientRepo.findById(receiverClientId).orElseThrow(()-> new UserNotFoundException("User with this is NOT FOUND: "+receiverClientId));
        double senderCurrentBalance = sender.getAccount().getCurrentBalance();
        double receiverCurrentBalance = receiver.getAccount().getCurrentBalance();
        if(senderCurrentBalance < money){
            return "Insufficient Fund";
        }
        sender.getAccount().setCurrentBalance(senderCurrentBalance - money);
        receiver.getAccount().setCurrentBalance(receiverCurrentBalance + money);
        clientRepo.save(sender);
        clientRepo.save(receiver);
        return sender.getName()+" successfully sent "+ money +" rupees to "+receiver.getName();
    }
}
