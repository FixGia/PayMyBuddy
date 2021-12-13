package com.project.paymybuddy.ServiceDAOTest;

import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.DAO.Transactions.TransactionRepository;
import com.project.paymybuddy.DAO.Transactions.TransactionServiceImpl;
import com.project.paymybuddy.DAO.User.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    private TransactionEntity transactionEntity;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUpBeforeEachTest(){
        transactionEntity = new TransactionEntity();
        transactionEntity.setId(1L);
        transactionEntity.setCommission(0.5);
        UserEntity payer = new UserEntity();
        payer.setEmail("Testpayer@gmail.com");
        transactionEntity.setPayer(payer);
        UserEntity beneficiary = new UserEntity();
        beneficiary.setEmail("TestBenefiary@gmail.com");
        transactionEntity.setBeneficiary(beneficiary);
        transactionEntity.setDescription("BlaBlaBlaTest");
        transactionEntity.setAmount(501);
        transactionEntity.setCreationDate(LocalDate.now());
        lenient().when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
    }

    @Test
    public void findAllTransactionsTest() {
        transactionService.findAllTransactions(1L);
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    public void findAllTransactionsByBeneficiaryEmailTest(){
        transactionService.findAllTransactionsByBeneficiaryEmail("TestBenefiary@gmail.com");
        verify(transactionRepository, times(1)).findTransactionEntitiesByBeneficiary_Email("TestBenefiary@gmail.com");
    }
    @Test
    public void  findAllTransactionsByPayerEmail(){
        transactionService.findAllTransactionsByPayerEmail("Testpayer@gmail.com");
        verify(transactionRepository, times(1)).findTransactionEntitiesByPayer_Email("Testpayer@gmail.com");
    }
    @Test
    public void findTransactionByIdTest() {
        transactionService.findTransactionById(1L);
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    public void saveTransactionTest(){
        transactionService.saveTransaction(transactionEntity);
        verify(transactionRepository, times(1)).save(transactionEntity);

    }
    @Test
    public void saveNewTransactionTest(){
        lenient().when(transactionRepository.findById(1L)).thenReturn(Optional.of(transactionEntity));
        transactionService.saveNewTransaction(1L, transactionEntity);
        verify(transactionRepository, times(1)).save(transactionEntity);
    }
    @Test
    public void saveNewTransactionButNotExistTest() {
        lenient().when(transactionRepository.findById(1L)).thenReturn(Optional.of(transactionEntity));
        transactionService.saveNewTransaction(2L, transactionEntity);
        verify(transactionRepository, times(0)).save(transactionEntity);
    }
}
