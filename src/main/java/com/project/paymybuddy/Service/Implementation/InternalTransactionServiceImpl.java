package com.project.paymybuddy.Service.Implementation;
import com.project.paymybuddy.DTO.TransferRequest;
import com.project.paymybuddy.Entity.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.Entity.Transfers.TransferEntity;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.Exception.BalanceInsufficientException;
import com.project.paymybuddy.Exception.NotConformDataException;
import com.project.paymybuddy.Service.BankAccountService;
import com.project.paymybuddy.Service.InternalTransactionService;
import com.project.paymybuddy.Service.TransferService;
import com.project.paymybuddy.Service.UserService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Slf4j
public class InternalTransactionServiceImpl implements InternalTransactionService {

    private static final double DESCRIPTION_LENGTH = 30;

    private BankAccountService bankAccountService;
    private UserService userService;
    private TransferService transferService;


    @Transactional
    public TransferEntity DebitWalletToBankAccountTransfer(TransferRequest transferRequest) {

        try {  UserEntity currentUsers = userService.getCurrentUser();

            if (currentUsers != null ) {

                prepareTransfer(transferRequest);
                CalculateQualifyDebitWalletTransfer(transferRequest);
                updateBankAccountAndUserAfterDebitWalletTransfer(transferRequest);

                userService.updateUsers(currentUsers);
                bankAccountService.updateBankAccount(bankAccountService.findBankAccountByUserEmail(currentUsers.getEmail()).get());

                //**
                /* Set parameters for transfer entity
                 */
                TransferEntity transfer = new TransferEntity();
                transfer.setDebit(transferRequest.getAmount());
                BankAccountEntity userBankAccount = bankAccountService.findBankAccountByUserEmail(currentUsers.getEmail()).get();
                transfer.setBankAccount(userBankAccount);
                transfer.setUserEntity(currentUsers);
                transfer.setDescription(transferRequest.getDescription());
                transferService.saveTransfer(transfer);

                log.info("Transfer Debit Success");
                return transfer;
            }
            } catch (NotConformDataException e) {
                e.printStackTrace();
                log.error("Transfer Credit fail");
                return null;
            }
            throw new NotConformDataException(" CurrentUser doesn't exist");
        }

    @Transactional
    public TransferEntity CreditWalletWithBankAccountTransfer(TransferRequest transferRequest) {


        try {
            UserEntity currentUsers = userService.getCurrentUser();

            if (currentUsers != null) {

                prepareTransfer(transferRequest);

                CalculateQualifyCreditWalletTransfer(transferRequest);

                updateBankAccountAndUserAfterCreditWalletTransfer(transferRequest);

                userService.updateUsers(userService.getCurrentUser());

                bankAccountService.updateBankAccount(bankAccountService.findBankAccountByUserEmail(currentUsers.getEmail()).get());


                TransferEntity transfer = new TransferEntity();
                transfer.setCredit(transferRequest.getAmount());
                BankAccountEntity userBankAccount = bankAccountService.findBankAccountByUserEmail(currentUsers.getEmail()).get();
                transfer.setBankAccount(userBankAccount);
                transfer.setUserEntity(currentUsers);
                transfer.setDescription(transferRequest.getDescription());

                transferService.saveTransfer(transfer);
                log.info("Transfer Credit Success");
                return transfer;
            }
        } catch (NotConformDataException e) {
            e.printStackTrace();
            log.error("Transfer Credit fail");
            return null;
        }
        throw new NotConformDataException(" CurrentUser doesn't exist");
    }




    public void prepareTransfer(@NotNull TransferRequest transferRequest) {

       UserEntity currentUser = userService.getCurrentUser();

       if(currentUser == null) {
            throw new NotConformDataException("Owner cannot be null");
        }
        if (!bankAccountService.findBankAccountByUserEmail(currentUser.getEmail()).isPresent()) {
            throw new NotConformDataException("BankAccount cannot be null");
        }
        if(transferRequest.getDescription().length() > DESCRIPTION_LENGTH){
            throw new NotConformDataException("description must be 30 characters maximum");
        }
        if(transferRequest.getAmount() <= 0){
            throw new BalanceInsufficientException("amount must be positive value");
        }
    }


    public boolean CalculateQualifyDebitWalletTransfer(@NotNull TransferRequest transferRequest) {

        UserEntity currentUser = userService.getCurrentUser();
        double wallet = currentUser.getWallet();
        double amount = transferRequest.getAmount();
        if (wallet - amount >= 0) {
            log.info("Wallet is sufficient to do Transfer");
            return true;
        }
        log.error("Wallet is not sufficient to do Transfer");
        throw new BalanceInsufficientException("Wallet is not sufficient to do Transfer");
    }

    public boolean CalculateQualifyCreditWalletTransfer(@NotNull TransferRequest transferRequest) {

        UserEntity currentUser = userService.getCurrentUser();
        BankAccountEntity userBankAccount = bankAccountService.findBankAccountByUserEmail(currentUser.getEmail()).get();
        double bankAccountAmount = userBankAccount.getAmount();
        double amount = transferRequest.getAmount();
        if (bankAccountAmount - amount >= 0) {
            log.info("BankAccountAmount is sufficient to do Transfer");
            return true;
        }
        log.error("BankAccountAmount is not sufficient to do Transfer");
        throw new BalanceInsufficientException("BankAccount is not sufficient to do Transfer");
    }


    public void updateBankAccountAndUserAfterDebitWalletTransfer(@NotNull TransferRequest transferRequest) {

        UserEntity currentUser = userService.getCurrentUser();
        BankAccountEntity userBankAccount = bankAccountService.findBankAccountByUserEmail(currentUser.getEmail()).get();

        try {

        double newWalletUser = (currentUser.getWallet() - (transferRequest.getAmount()));
        currentUser.setWallet(newWalletUser);

        double newAmountBankAccount = (userBankAccount.getAmount())+(transferRequest.getAmount());
        userBankAccount.setAmount(newAmountBankAccount);
        userService.updateUserWallet(currentUser);

        } catch (ArithmeticException e) {
        e.printStackTrace();
    }
}


    public void updateBankAccountAndUserAfterCreditWalletTransfer(@NotNull TransferRequest transferRequest) {
        UserEntity currentUser = userService.getCurrentUser();
        try {
        double newWalletUser = (currentUser.getWallet() + (transferRequest.getAmount()));
        currentUser.setWallet(newWalletUser);
            BankAccountEntity userBankAccount = bankAccountService.findBankAccountByUserEmail(currentUser.getEmail()).get();
        double newAmountBankAccount = (userBankAccount.getAmount()-(transferRequest.getAmount()));
        userBankAccount.setAmount(newAmountBankAccount);
        userService.updateUserWallet(currentUser);
}
    catch (ArithmeticException e) {
        e.printStackTrace();
    }
}

}


