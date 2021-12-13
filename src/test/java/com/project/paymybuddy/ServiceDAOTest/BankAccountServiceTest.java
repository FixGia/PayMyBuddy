package com.project.paymybuddy.ServiceDAOTest;

import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntityRepository;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountService;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountServiceImpl;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserRepository;
import com.project.paymybuddy.DAO.User.UserService;
import com.project.paymybuddy.DAO.User.UserServiceImpl;
import com.project.paymybuddy.Domain.DTO.BankAccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    private BankAccountEntity bankAccount;

    private UserEntity user;

    @Mock
    private BankAccountEntityRepository bankAccountEntityRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;


    @BeforeEach
    private void setUpBeforeEachTest() {
        bankAccount = new BankAccountEntity();
        bankAccount.setIdBankAccount(1);
        bankAccount.setIban("Fr123456789");
        bankAccount.setAmount(1000);

        user = new UserEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");
        user.setCivility("M");
        user.setId(1L);
        user.setWallet(50);

        bankAccount.setUser(user);

        lenient().when(bankAccountEntityRepository.save(bankAccount)).thenReturn(bankAccount);
        lenient().when(userRepository.save(user)).thenReturn(user);

    }

    @Test
    public void testFindAllBankAccountByUserEmail() {
        bankAccountService.findAllBankAccountByUserEmail(user.getEmail());
        verify(bankAccountEntityRepository, times(1)).findAllByUserEmail(user.getEmail());
    }


    @Test
    public void testFindBankAccountByUserEmail() {
        bankAccountService.findBankAccountByUserEmail(user.getEmail());
        verify(bankAccountEntityRepository, times(1)).findBankAccountEntityByUserEmail(user.getEmail());
    }

    @Test
    //TODO FIX THIS TEST
    public void testUpdateBankAccount() {

        bankAccountService.updateBankAccount(bankAccount);
        verify(bankAccountEntityRepository, times(1)).findBankAccountEntityByUserEmail(bankAccount.getUser().getEmail());
    }

    @Test
    void testDeleteByUserEmail() {
        lenient().when(bankAccountEntityRepository.findBankAccountEntityByUserEmail(user.getEmail())).thenReturn(Optional.of(bankAccount));
        bankAccountService.deleteByUserEmail(user.getEmail());
        verify(bankAccountEntityRepository, times(1)).delete(bankAccount);
    }


    @Test
    public void LinkUserToBankAccount(){
        BankAccountRequest bankAccountRequest = new BankAccountRequest();
        bankAccountRequest.setIban("BE123456789");
        bankAccountRequest.setAmount(1000);
        UserEntity user = new UserEntity();
        user.setFirstname("Jeanne");
        user.setLastname("Testé");
        user.setEmail("JeanneTest@gmail.com");
        user.setCivility("Mme");
        user.setId(2L);
        user.setWallet(500);
        lenient().when(userService.getCurrentUser()).thenReturn(user);
        bankAccountService.LinkUserToBankAccount(bankAccountRequest);
        verify(bankAccountEntityRepository, times(1)).save(bankAccountEntityRepository.findBankAccountEntityByIban("BE123456789"));
    }
}




