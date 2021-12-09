package com.project.paymybuddy.Domain.Service.Implementation;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.DTO.TransferRequest;
import com.project.paymybuddy.Domain.Service.InternalTransactionService;
import com.project.paymybuddy.Domain.Util.MapDAO;
import com.project.paymybuddy.Exception.BalanceInsufficientException;
import com.project.paymybuddy.Exception.NotConformDataException;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountService;
import com.project.paymybuddy.DAO.Transfers.TransferEntity;
import com.project.paymybuddy.DAO.Transfers.TransferService;
import com.project.paymybuddy.DAO.User.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Slf4j
public class InternalTransactionServiceImpl implements InternalTransactionService {

    private static final double DESCRIPTION_LENGTH = 30;

    BankAccountService bankAccountService;
    UserService userService;
    TransferService transferService;
    MapDAO mapDAO;

    @Transactional
    public TransferEntity makeWalletDebitToBankAccountTransfer(TransferRequest transferRequest) {

        UserEntity currentUsers = userService.getCurrentUser();

        try {
            prepareTransfer(transferRequest);

            CalculateQualifyDebitTransfer(transferRequest);

            updateBankAccountAndUserAfterDebitTransfer(transferRequest);

            saveEffectiveTransfer(transferRequest);

            userService.updateUsers(currentUsers);

            bankAccountService.updateBankAccount(bankAccountService.findBankAccountByUserEmail(currentUsers.getEmail()).get());

            //**
            /* Set parameters for transfer entity
             */
            TransferEntity transfer = mapDAO.TransferEntityMapper(transferRequest);
            transfer.setDebit(transferRequest.getAmount());
            BankAccountEntity userBankAccount = bankAccountService.findBankAccountByUserEmail(currentUsers.getEmail()).get();
            transfer.setBankAccount(userBankAccount);
            transfer.setUserEntity(currentUsers);
            transfer.setDescription(transferRequest.getDescription());


            transferService.saveTransfer(transfer);

            log.info("Transfer Debit Success");
            return transfer;
        } catch (NotConformDataException e) {
            e.printStackTrace();

        }
        log.error("Transfer Debit fail");
        return null;
    }

    @Transactional
    public TransferEntity makeWalletCreditToBankAccountTransfer(TransferRequest transferRequest) {

        UserEntity currentUsers = userService.getCurrentUser();

        try {

            prepareTransfer(transferRequest);

            CalculateQualifyCreditTransfer(transferRequest);

            updateBankAccountAndUserAfterCreditTransfer(transferRequest);

            saveEffectiveTransfer(transferRequest);

            userService.updateUsers(userService.getCurrentUser());

            bankAccountService.updateBankAccount(bankAccountService.findBankAccountByUserEmail(currentUsers.getEmail()).get());

            //**
            /* Set parameters for transfer entity
             */
            TransferEntity transfer = mapDAO.TransferEntityMapper(transferRequest);
            transfer.setCredit(transferRequest.getAmount());
            BankAccountEntity userBankAccount = bankAccountService.findBankAccountByUserEmail(currentUsers.getEmail()).get();
            transfer.setBankAccount(userBankAccount);
            transfer.setUserEntity(currentUsers);
            transfer.setDescription(transferRequest.getDescription());

            //**
            /* Save transfer entity
             */
            transferService.saveTransfer(transfer);

            log.info("Transfer Credit Success");
            return transfer;

        } catch (NotConformDataException e) {
            e.printStackTrace();

        }
        log.error("Transfer Credit fail");
        return null;
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

    public boolean CalculateQualifyDebitTransfer(@NotNull TransferRequest transferRequest) {

        UserEntity currentUser = userService.getCurrentUser();
        double wallet = currentUser.getWallet();
        double amount = transferRequest.getAmount();
        if (wallet - amount >= 0) {
            log.info("Wallet is sufficient to do Transfer");
            return true;
        }
        log.info("Wallet is not sufficient to do Transfer");
        return false;
    }

    public boolean CalculateQualifyCreditTransfer(@NotNull TransferRequest transferRequest) {

        UserEntity currentUser = userService.getCurrentUser();
    double wallet = currentUser.getWallet();
    double amount = transferRequest.getAmount();
        if (wallet - amount >= 0) {
            log.info("Wallet is sufficient to do Transfer");
            return true;
        }
        log.info("Wallet is not sufficient to do Transfer");
        return false;
    }

    public void updateBankAccountAndUserAfterDebitTransfer(@NotNull TransferRequest transferRequest) {
        UserEntity currentUser = userService.getCurrentUser();
        try {
        double newWalletUser = (currentUser.getWallet() - (transferRequest.getAmount()));
        currentUser.setWallet(newWalletUser);

        BankAccountEntity userBankAccount = bankAccountService.findBankAccountByUserEmail(currentUser.getEmail()).get();

        double newAmountBankAccount = (userBankAccount.getAmount()) - (transferRequest.getAmount());
        userBankAccount.setAmount(newAmountBankAccount);
    } catch (ArithmeticException e) {
        e.printStackTrace();
    }
}

    public void updateBankAccountAndUserAfterCreditTransfer(@NotNull TransferRequest transferRequest) {
        UserEntity currentUser = userService.getCurrentUser();
        try {
        double newWalletUser = (currentUser.getWallet() + ( transferRequest.getAmount()));
        currentUser.setWallet(newWalletUser);
            BankAccountEntity userBankAccount = bankAccountService.findBankAccountByUserEmail(currentUser.getEmail()).get();
        double newAmountBankAccount = (userBankAccount.getAmount() + (transferRequest.getAmount()));
        userBankAccount.setAmount(newAmountBankAccount);
}
    catch (ArithmeticException e) {
        e.printStackTrace();
    }
}

    public TransferEntity saveEffectiveTransfer(TransferRequest transferRequest) {

    TransferEntity transfer = mapDAO.TransferEntityMapper(transferRequest);
    transferService.saveTransfer(transfer);
    return transfer;
}
}


