package com.onlineBankingOperations.services;

import com.onlineBankingOperations.entity.Account;
import com.onlineBankingOperations.entity.Client;
import com.onlineBankingOperations.exception.UserNotFoundException;
import com.onlineBankingOperations.repository.ClientRepo;
import com.onlineBankingOperations.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ClientRepo clientRepo;

    @InjectMocks
    private AccountServiceImpl accountService; // Inject mocks into AccountServiceImpl

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTransferMoney_Success() {
        // Arrange
        Long senderClientId = 1L;
        Long receiverClientId = 2L;
        Double amountToTransfer = 100.0;

        Client sender = createClient(senderClientId, 500.0);
        Client receiver = createClient(receiverClientId, 300.0);

        when(clientRepo.findById(senderClientId)).thenReturn(java.util.Optional.of(sender));
        when(clientRepo.findById(receiverClientId)).thenReturn(java.util.Optional.of(receiver));

        // Act
        String result = accountService.transferMoney(senderClientId, receiverClientId, amountToTransfer);

        // Assert
        assertEquals(sender.getName() + " successfully sent " + amountToTransfer + " rupees to " + receiver.getName(), result);
        assertEquals(400.0, sender.getAccount().getCurrentBalance());
        assertEquals(400.0, receiver.getAccount().getCurrentBalance());
        verify(clientRepo, times(1)).save(sender);
        verify(clientRepo, times(1)).save(receiver);
    }

    @Test
    void testTransferMoney_InsufficientFunds() {
        // Arrange
        Long senderClientId = 1L;
        Long receiverClientId = 2L;
        Double amountToTransfer = 600.0;

        Client sender = createClient(senderClientId, 500.0);
        Client receiver = createClient(receiverClientId, 300.0);

        when(clientRepo.findById(senderClientId)).thenReturn(java.util.Optional.of(sender));
        when(clientRepo.findById(receiverClientId)).thenReturn(java.util.Optional.of(receiver));

        // Act
        String result = accountService.transferMoney(senderClientId, receiverClientId, amountToTransfer);

        // Assert
        assertEquals("Insufficient Fund", result);
        verify(clientRepo, never()).save(sender);
        verify(clientRepo, never()).save(receiver);
    }

    @Test
    void testTransferMoney_UserNotFound() {
        // Arrange
        Long senderClientId = 1L;
        Long receiverClientId = 2L;
        Double amountToTransfer = 100.0;

        when(clientRepo.findById(senderClientId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> accountService.transferMoney(senderClientId, receiverClientId, amountToTransfer));
        verify(clientRepo, never()).save(any(Client.class));
    }

    private Client createClient(Long clientId, double balance) {
        Account account = new Account();
        account.setCurrentBalance(balance);
        Client client = new Client();
        client.setAccount(account);
        client.setName("Client" + clientId);
        return client;
    }
}
