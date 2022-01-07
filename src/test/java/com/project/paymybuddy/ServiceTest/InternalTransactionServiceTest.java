package com.project.paymybuddy.ServiceTest;

import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountService;
import com.project.paymybuddy.DAO.Transfers.TransferEntity;
import com.project.paymybuddy.DAO.Transfers.TransferService;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserService;
import com.project.paymybuddy.Domain.DTO.TransferRequest;
import com.project.paymybuddy.Domain.Service.Implementation.InternalTransactionServiceImpl;
import com.project.paymybuddy.Domain.Util.MapDAO;
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
    private MapDAO mapDAO;
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
        currentUser.setWallet(100);
        otherUser.setId(2L);
        otherUser.setEmail("PetitTest@gmail.com");
        otherUser.setPassword("password");
        otherUser.setCivility("M");
        otherUser.setFirstname("Petit");
        otherUser.setLastname("Test");
        otherUser.setEnabled(true);
        otherUser.setWallet(100);
        bankAccount.setUser(currentUser);
        bankAccount.setIban("IBAN123456789");
        bankAccount.setAmount(1000);
        bankAccount.setIdBankAccount(1);
        transferRequest.setAmount(100);
        transferRequest.setDescription("Test");
        lenient().when(userService.getCurrentUser()).thenReturn(currentUser);
        lenient().when(bankAccountService.findBankAccountByUserEmail(currentUser.getEmail())).thenReturn(java.util.Optional.of(bankAccount));

    }

    @Test
    public void DebitWalletToBankAccountTransferTest(){
        lenient().when(mapDAO.TransferEntityMapper(transferRequest)).thenReturn(transfer);
        internalTransactionService.DebitWalletToBankAccountTransfer(transferRequest);
        assertEquals(1100,bankAccount.getAmount());
        assertEquals(0, currentUser.getWallet());

    }
    @Test
    public void makeWalletCreditToBankAccountTransferTest(){
        lenient().when(mapDAO.TransferEntityMapper(transferRequest)).thenReturn(transfer);
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
        transferRequest.setAmount(-5);
        assertThrows(BalanceInsufficientException.class, () -> internalTransactionService.prepareTransfer(transferRequest));
    }
    @Test
    public void CalculateQualifyDebitWalletTransferTest() {
        assertTrue(internalTransactionService.CalculateQualifyDebitWalletTransfer(transferRequest));
    }
    @Test
    public void CalculateQualifyDebitWalletTransferButWalletInsufficientTest() {
        currentUser.setWallet(0);
        assertFalse(internalTransactionService.CalculateQualifyDebitWalletTransfer(transferRequest));
    }
    @Test
    public void CalculateQualifyCreditWalletTransferTest(){
        assertTrue(internalTransactionService.CalculateQualifyCreditWalletTransfer(transferRequest));
    }

    @Test
    public void CalculateQualifyCreditWalletTransferButBankAccountAmountInsufficientTest(){
        bankAccount.setAmount(20);
        assertFalse(internalTransactionService.CalculateQualifyCreditWalletTransfer(transferRequest));
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
    @Test
    public void saveEffectiveTransferTest(){
        internalTransactionService.mapEffectiveTransfer(transferRequest);
        verify(mapDAO, times(1)).TransferEntityMapper(transferRequest);
    }


}
