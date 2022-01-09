package com.project.paymybuddy.ServiceTest;

import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.DAO.Transactions.TransactionService;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.Service.UserService;
import com.project.paymybuddy.Domain.DTO.TransactionRequest;
import com.project.paymybuddy.Domain.Service.Implementation.ExternalTransactionServiceImpl;
import com.project.paymybuddy.Exception.BalanceInsufficientException;
import com.project.paymybuddy.Exception.NotConformDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExternalTransactionServiceTest {

    private final UserEntity currentUser = new UserEntity();
    private final UserEntity otherUser = new UserEntity();
    private final TransactionRequest transactionRequest = new TransactionRequest();
    private final TransactionEntity transactionEntity= new TransactionEntity();

    @Mock
    private UserService userService;
    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private ExternalTransactionServiceImpl externalTransactionService;



    @BeforeEach
    public void setUp(){

        currentUser.setId(1L);
        currentUser.setEmail("JeanTest@gmail.com");
        currentUser.setPassword("password");
        currentUser.setCivility("M");
        currentUser.setFirstname("Jean");
        currentUser.setLastname("Test");
        currentUser.setEnabled(true);
        currentUser.setWallet(100);
        otherUser.setId(2L);
        otherUser.setEmail("PetitTest@gmail.com");
        otherUser.setPassword("password");
        otherUser.setCivility("M");
        otherUser.setFirstname("Petit");
        otherUser.setLastname("Test");
        otherUser.setEnabled(true);
        otherUser.setWallet(100);
        transactionRequest.setBeneficiary(otherUser.getEmail());
        transactionRequest.setPayer(currentUser.getEmail());
        transactionRequest.setAmount(10);
        transactionRequest.setDescription("test");
        lenient().when(userService.getCurrentUser()).thenReturn(currentUser);
        lenient().when(userService.getUser(transactionRequest.getPayer())).thenReturn(currentUser);
        lenient().when(userService.getUser(transactionRequest.getBeneficiary())).thenReturn(otherUser);
        lenient().when(userService.saveUser(currentUser)).thenReturn(currentUser);
        lenient().when(userService.saveUser(otherUser)).thenReturn(otherUser);
    }

    @Test
    public void MakeTransactionTest(){

        externalTransactionService.makeTransaction(transactionRequest);
        assertEquals(89.5, currentUser.getWallet());
        assertEquals(110, otherUser.getWallet());

    }
    @Test
    public void prepareTransactionButPayerIsNullTest(){
        transactionRequest.setPayer(null);
        assertThrows(NotConformDataException.class, () -> externalTransactionService.prepareTransaction(transactionRequest));
    }

    @Test
    public void prepareTransactionButBeneficiaryIsNullTest(){
        transactionRequest.setBeneficiary(null);
        assertThrows(NotConformDataException.class, () -> externalTransactionService.prepareTransaction(transactionRequest));
    }
    @Test
    public void prepareTransactionButDescriptionIsTooLongTest(){
        transactionRequest.setDescription("La description dépasse largement les 30 caractères autorisés");
        assertThrows(NotConformDataException.class, () -> externalTransactionService.prepareTransaction(transactionRequest));
    }
    @Test
    public void prepareTransactionButDescriptionIsEmptyTest(){
        transactionRequest.setDescription("");
        assertThrows(NotConformDataException.class, () -> externalTransactionService.prepareTransaction(transactionRequest));
    }
    @Test
    public void prepareTransactionButBalanceIsInsufficientTest(){
        transactionRequest.setAmount(-5);
        assertThrows(BalanceInsufficientException.class, () -> externalTransactionService.prepareTransaction(transactionRequest));
    }
    @Test
    public void CalculateQualifyTransactionTest(){
         externalTransactionService.CalculateQualifyTransaction(transactionRequest);
        assertTrue(externalTransactionService.CalculateQualifyTransaction(transactionRequest));
    }
    @Test
    public void CalculateQualifyTransactionButWalletIsNotSufficientTest(){
        transactionRequest.setAmount(10000000);
        assertThrows(BalanceInsufficientException.class, () ->externalTransactionService.CalculateQualifyTransaction(transactionRequest));
    }
    @Test
    public void updatePayerAndBeneficiaryWalletAfterTransactionTest(){
        externalTransactionService.updatePayerAndBeneficiaryWalletAfterTransaction(transactionRequest);
        assertEquals(currentUser.getWallet(),89.5);
        assertEquals(otherUser.getWallet(), 110);
    }

    @Test
    public void mapEffectiveTransactionTest(){
        externalTransactionService.mapEffectiveTransaction(transactionRequest);

    }
    @Test
    public void displayedTransactionWhenUserIsPayerTest(){
        externalTransactionService.displayedTransactionWhenUserIsPayer();
        verify(transactionService, times(1)).findAllTransactionsByPayerEmail(userService.getCurrentUser().getEmail());
    }
    @Test
    public void displayedTransactionWhenUserIsBeneficiaryTest(){
        externalTransactionService.displayedTransactionWhenUserIsBeneficiary();
        verify(transactionService, times(1)).findAllTransactionsByBeneficiaryEmail(userService.getCurrentUser().getEmail());
    }

}
