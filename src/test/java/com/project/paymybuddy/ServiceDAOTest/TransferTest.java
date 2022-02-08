package com.project.paymybuddy.ServiceDAOTest;

import com.project.paymybuddy.Entity.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.Entity.Transfers.TransferEntity;
import com.project.paymybuddy.Entity.Transfers.TransferRepository;
import com.project.paymybuddy.Service.Implementation.TransferServiceImpl;
import com.project.paymybuddy.Entity.User.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferTest {

    TransferEntity transfer;
    UserEntity user;
    BankAccountEntity bankAccount;

    @Mock
    private TransferRepository transferRepository;
    @InjectMocks
    private TransferServiceImpl transferService;

    @BeforeEach
    public void SetUp(){
        transfer = new TransferEntity();
        user = new UserEntity();
        bankAccount = new BankAccountEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");
        user.setCivility("M");
        user.setId(1L);
        user.setWallet(50.0);

        bankAccount.setIdBankAccount(1);
        bankAccount.setIban("Fr123456789");
        bankAccount.setAmount(1000.0);
        transfer.setDescription("blblalblaTest");
        transfer.setUserEntity(user);
        transfer.setBankAccount(bankAccount);
        transfer.setCredit(50.0);
        transfer.setDebit(50.0);
        transfer.setAmount(1200.0);
        transfer.setId(1L);


    }

    @Test
    public void findAllByUsersTest(){
        transferService.findAllByUser(user);
        verify(transferRepository,times(1)).findAllByUserEntity(user);
    }
    @Test
    public void findByIdTest(){
        transferService.findById(1L);
        verify(transferRepository, times(1)).findById(1L);
    }

    @Test
    public void saveTransferTest(){
        transferService.saveTransfer(transfer);
        verify(transferRepository,times(1)).save(transfer);
    }

    @Test
    public void updateTransferTest(){

        lenient().when(transferRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(transfer));
        if ( java.util.Optional.ofNullable(transfer)!= null) {
            transferService.updateTransfer(1L, transfer);
            verify(transferRepository, times(1)).save(transfer);
        }
        else {
            verify(transferRepository, times(0)).save(transfer);
        }
    }

}
