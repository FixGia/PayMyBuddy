package com.project.paymybuddy.ServiceTest;

import com.project.paymybuddy.Entity.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.Service.BankAccountService;
import com.project.paymybuddy.Entity.Transfers.TransferEntity;
import com.project.paymybuddy.Service.TransferService;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.Service.UserService;
import com.project.paymybuddy.DTO.TransferRequest;
import com.project.paymybuddy.Service.Implementation.InternalTransactionServiceImpl;
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
public class InternalTransactionServiceTest {

    private final UserEntity currentUser = new UserEntity();
    private final UserEntity otherUser = new UserEntity();
    private final BankAccountEntity bankAccount= new BankAccountEntity();
    private final TransferRequest transferRequest = new TransferRequest();
    private final TransferEntity transfer = new TransferEntity();

    @Mock
    UserService userService;
    @Mock
    TransferService transferService;
    @Mock
    BankAccountService bankAccountService;
    @InjectMocks
    InternalTransactionServiceImpl internalTransactionService;


    @BeforeEach
    public void setUp(){
        currentUser.setId(1L);
        currentUser.setEmail("JeanTest@gmail.com");
        currentUser.setPassword("password");
        currentUser.setCivility("M");
        currentUser.setFirstname("Jean");
        currentUser.setLastname("Test");
        currentUser.setEnabled(true);
        currentUser.setWallet(100.0);
        otherUser.setId(2L);
        otherUser.setEmail("PetitTest@gmail.com");
        otherUser.setPassword("password");
        otherUser.setCivility("M");
        otherUser.setFirstname("Petit");
        otherUser.setLastname("Test");
        otherUser.setEnabled(true);
        otherUser.setWallet(100.0);
        bankAccount.setUser(currentUser);
        bankAccount.setIban("IBAN123456789");
        bankAccount.setAmount(1000.0);
        bankAccount.setIdBankAccount(1);
        transferRequest.setAmount(100.0);
        transferRequest.setDescription("Test");
        lenient().when(userService.getCurrentUser()).thenReturn(currentUser);
        lenient().when(bankAccountService.findBankAccountByUserEmail(currentUser.getEmail())).thenReturn(java.util.Optional.of(bankAccount));

    }

    @Test
    public void DebitWalletToBankAccountTransferTest(){

        internalTransactionService.DebitWalletToBankAccountTransfer(transferRequest);
        assertEquals(1100,bankAccount.getAmount());
        assertEquals(0, currentUser.getWallet());

    }
    @Test
    public void makeWalletCreditToBankAccountTransferTest(){

        internalTransactionService.CreditWalletWithBankAccountTransfer(transferRequest);
        assertEquals(900, bankAccount.getAmount());
        assertEquals(200, currentUser.getWallet());
    }
    @Test
    public void prepareTransferButCurrentUserIsNullTest(){
        lenient().when(userService.getCurrentUser()).thenReturn(null);
        assertThrows(NotConformDataException.class, () -> internalTransactionService.prepareTransfer(transferRequest));
    }
    @Test
    public void prepareTransferButBankAccountIsNullTest(){
        lenient().when(bankAccountService.findBankAccountByUserEmail(currentUser.getEmail())).thenReturn(java.util.Optional.empty());
        assertThrows(NotConformDataException.class, () -> internalTransactionService.prepareTransfer(transferRequest));
    }
    @Test
    public void prepareTransferButDescriptionIsTooLongTest(){
        transferRequest.setDescription("La description dépasse largement les 30 caractères autorisés");
        assertThrows(NotConformDataException.class, () -> internalTransactionService.prepareTransfer(transferRequest));
    }
    @Test
    public void prepareTransferButAmountLessThanZeroTest(){
        transferRequest.setAmount(-5.0);
        assertThrows(BalanceInsufficientException.class, () -> internalTransactionService.prepareTransfer(transferRequest));
    }
    @Test
    public void CalculateQualifyDebitWalletTransferTest() {
        assertTrue(internalTransactionService.CalculateQualifyDebitWalletTransfer(transferRequest));
    }
    @Test
    public void CalculateQualifyDebitWalletTransferButWalletInsufficientTest() {
        currentUser.setWallet(0.0);
        assertThrows(BalanceInsufficientException.class, () -> internalTransactionService.CalculateQualifyDebitWalletTransfer(transferRequest));
    }
    @Test
    public void CalculateQualifyCreditWalletTransferTest(){
        assertTrue(internalTransactionService.CalculateQualifyCreditWalletTransfer(transferRequest));
    }

    @Test
    public void CalculateQualifyCreditWalletTransferButBankAccountAmountInsufficientTest(){
        bankAccount.setAmount(20.0);
        assertThrows(BalanceInsufficientException.class, () -> internalTransactionService.CalculateQualifyCreditWalletTransfer(transferRequest));
    }

    @Test
    public void updateBankAccountAndUserAfterDebitWalletTransferTest(){
        internalTransactionService.updateBankAccountAndUserAfterDebitWalletTransfer(transferRequest);
        assertEquals(1100,bankAccount.getAmount());
        assertEquals(0, currentUser.getWallet());
    }
    @Test
    public void updateBankAccountAndUserAfterCreditWalletTransferTest(){
        internalTransactionService.updateBankAccountAndUserAfterCreditWalletTransfer(transferRequest);
        assertEquals(900, bankAccount.getAmount());
        assertEquals(200, currentUser.getWallet());
    }

}
