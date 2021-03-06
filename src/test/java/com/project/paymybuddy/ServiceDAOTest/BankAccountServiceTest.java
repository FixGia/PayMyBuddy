package com.project.paymybuddy.ServiceDAOTest;

import com.project.paymybuddy.Entity.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.Entity.BankAccounts.BankAccountEntityRepository;
import com.project.paymybuddy.Service.Implementation.BankAccountServiceImpl;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.Entity.User.UserRepository;
import com.project.paymybuddy.Service.Implementation.UserServiceImpl;
import com.project.paymybuddy.DTO.BankAccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        user.setLastname("Test??");
        user.setEmail("JeanneTest@gmail.com");
        user.setCivility("Mme");
        user.setId(2L);
        user.setWallet(500);
        lenient().when(userService.getCurrentUser()).thenReturn(user);
        bankAccountService.LinkUserToBankAccount(bankAccountRequest);
        verify(bankAccountEntityRepository, times(1)).save(bankAccountEntityRepository.findBankAccountEntityByIban("BE123456789"));
    }
}




