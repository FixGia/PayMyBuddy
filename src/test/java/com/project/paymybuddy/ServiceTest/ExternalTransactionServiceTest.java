package com.project.paymybuddy.ServiceTest;

import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.DAO.Transactions.TransactionService;
import com.project.paymybuddy.DAO.Transactions.TransactionServiceImpl;
import com.project.paymybuddy.DAO.Transfers.TransferServiceImpl;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserService;
import com.project.paymybuddy.Domain.DTO.TransactionDTO;
import com.project.paymybuddy.Domain.Service.ExternalTransactionService;
import com.project.paymybuddy.Domain.Service.Implementation.ExternalTransactionServiceImpl;
import com.project.paymybuddy.Domain.Util.MapDAO;
import com.project.paymybuddy.Exception.BalanceInsufficientException;
import com.project.paymybuddy.Exception.DataNotFoundException;
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

    private UserEntity currentUser = new UserEntity();
    private UserEntity otherUser = new UserEntity();
    private TransactionDTO transactionDTO = new TransactionDTO();
    private TransactionEntity transactionEntity= new TransactionEntity();
    private static final double DESCRIPTION_LENGTH = 30;
    private static final double COMMISSION = 0.05;

    @Mock
    private UserService userService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private MapDAO mapDAO;
    @InjectMocks
    private ExternalTransactionServiceImpl externalTransactionService;



    @BeforeEach
    public void setUp(){

        currentUser.setId(1L);
        currentUser.setEmail("JeanTest@gmail.com");
        currentUser.setPassword("password");
        currentUser.setCivility("M");
        currentUser.setWallet(5000);
        currentUser.setFirstname("Jean");
        currentUser.setLastname("Test");
        currentUser.setEnabled(true);
        currentUser.setWallet(100);
        otherUser.setId(2L);
        otherUser.setEmail("PetitTest@gmail.com");
        otherUser.setPassword("password");
        otherUser.setCivility("M");
        otherUser.setWallet(2000);
        otherUser.setFirstname("Petit");
        otherUser.setLastname("Test");
        otherUser.setEnabled(true);
        otherUser.setWallet(100);
        transactionDTO.setBeneficiary(otherUser.getEmail());
        transactionDTO.setPayer(currentUser.getEmail());
        transactionDTO.setAmount(10);
        transactionDTO.setDescription("test");
        lenient().when(transactionService.saveTransaction(transactionEntity)).thenReturn(transactionEntity);
        lenient().when(userService.getCurrentUser()).thenReturn(currentUser);
        lenient().when(userService.getUser(transactionDTO.getPayer())).thenReturn(currentUser);
        lenient().when(userService.getUser(transactionDTO.getBeneficiary())).thenReturn(otherUser);
        lenient().when(userService.saveUser(currentUser)).thenReturn(currentUser);
        lenient().when(userService.saveUser(otherUser)).thenReturn(otherUser);
    }

    @Test
    public void MakeTransactionTest(){
        lenient().when(mapDAO.TransactionEntityMapper(transactionDTO)).thenReturn(transactionEntity);
        externalTransactionService.makeTransaction(transactionDTO);
        verify(userService, times(1)).updateUsers(currentUser);
        verify(userService, times(1)).updateUsers(otherUser);
        verify(transactionService, times(1)).saveTransaction(transactionEntity);
    }
    @Test
    public void prepareTransactionButPayerIsNullTest(){
        transactionDTO.setPayer(null);
        assertThrows(NotConformDataException.class, () -> externalTransactionService.prepareTransaction(transactionDTO));
    }

    @Test
    public void prepareTransactionButBeneficiaryIsNullTest(){
        transactionDTO.setBeneficiary(null);
        assertThrows(NotConformDataException.class, () -> externalTransactionService.prepareTransaction(transactionDTO));
    }
    @Test
    public void prepareTransactionButDescriptionIsTooLongTest(){
        transactionDTO.setDescription("La description dépasse largement les 30 caractères autorisés");
        assertThrows(NotConformDataException.class, () -> externalTransactionService.prepareTransaction(transactionDTO));
    }
    @Test
    public void prepareTransactionButDescriptionIsEmptyTest(){
        transactionDTO.setDescription("");
        assertThrows(NotConformDataException.class, () -> externalTransactionService.prepareTransaction(transactionDTO));
    }
    @Test
    public void prepareTransactionButBalanceIsInsufficientTest(){
        transactionDTO.setAmount(-5);
        assertThrows(BalanceInsufficientException.class, () -> externalTransactionService.prepareTransaction(transactionDTO));
    }
    @Test
    public void CalculateQualifyTransactionTest(){
         externalTransactionService.CalculateQualifyTransaction(transactionDTO);
        assertEquals(externalTransactionService.CalculateQualifyTransaction(transactionDTO), true);
    }
    @Test
    public void CalculateQualifyTransactionButWalletIsNotSufficientTest(){
        transactionDTO.setAmount(10000000);
        assertThrows(BalanceInsufficientException.class, () ->externalTransactionService.CalculateQualifyTransaction(transactionDTO));

    }
}