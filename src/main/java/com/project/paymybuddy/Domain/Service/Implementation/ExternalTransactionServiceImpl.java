package com.project.paymybuddy.Domain.Service.Implementation;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.DTO.*;
import com.project.paymybuddy.Domain.Service.ExternalTransactionService;
import com.project.paymybuddy.Exception.BalanceInsufficientException;
import com.project.paymybuddy.Exception.DataNotFoundException;
import com.project.paymybuddy.Exception.NotConformDataException;
import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.DAO.Transactions.TransactionService;
import com.project.paymybuddy.Domain.Service.UserService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class ExternalTransactionServiceImpl implements ExternalTransactionService {

    private static final double DESCRIPTION_LENGTH = 30;
    private static final double COMMISSION = 0.05;

    private final UserService userService;
    private final TransactionService transactionService;



    @Transactional
    public Optional<TransactionEntity> makeTransaction(@NotNull TransactionRequest transactionRequest) {


        try {
            UserEntity currentUser = userService.getCurrentUser();
            UserEntity payer = userService.getUser(transactionRequest.getPayer());
            UserEntity beneficiary = userService.getUser(transactionRequest.getBeneficiary());

            if (currentUser == payer) {
                prepareTransaction(transactionRequest);

                CalculateQualifyTransaction(transactionRequest);

                updatePayerAndBeneficiaryWalletAfterTransaction(transactionRequest);

                userService.updateUsers(payer);

                userService.updateUsers(beneficiary);

                TransactionEntity transactionEntity = mapEffectiveTransaction(transactionRequest);

                transactionService.saveTransaction(transactionEntity);

                log.info("Transaction Success");
                return Optional.of(transactionEntity);

            }
            log.error("Transaction fail");
            return Optional.empty();
        } catch (NotConformDataException e) {
            e.printStackTrace();
        }
        throw new NotConformDataException(
                "Current user isn't payer");
    }

    public void prepareTransaction(@NotNull TransactionRequest transactionRequest) {

        if (transactionRequest.getPayer() == null) {
            throw new NotConformDataException("Payer cannot be null");
        }

        if (transactionRequest.getBeneficiary() == null) {
            throw new NotConformDataException(
                    "Beneficiary cannot be null");
        }

        if (transactionRequest.getDescription()
                .length() > DESCRIPTION_LENGTH) {

            throw new NotConformDataException(
                    "description must be 30 characters maximum");
        }
        if (transactionRequest.getDescription().length() == 0) {
            throw new NotConformDataException("description can't be empty");
        }
        if (transactionRequest.getAmount() <= 0) {
            throw new BalanceInsufficientException("amount must be a positive value");
        }

    }

    public boolean CalculateQualifyTransaction(@NotNull TransactionRequest transactionRequest) {


        UserEntity payer = userService.getUser(transactionRequest.getPayer());
        double wallet = payer.getWallet();
        double amount = transactionRequest.getAmount();
        if (wallet - (amount + (amount * COMMISSION)) >= 0) {
            log.info("Wallet is sufficient to do Transaction");
            return true;
        }
        log.error("Wallet is not sufficient to do Transaction");
        throw new BalanceInsufficientException("Wallet is not sufficient to do Transaction");
    }

    public void updatePayerAndBeneficiaryWalletAfterTransaction(@NotNull TransactionRequest transactionRequest) {

        UserEntity payer = userService.getUser(transactionRequest.getPayer());
        UserEntity beneficiary = userService.getUser(transactionRequest.getBeneficiary());

        try {
            double newWalletPayer = (payer.getWallet() - (transactionRequest.getAmount() + (transactionRequest.getAmount() * COMMISSION)));

            payer.setWallet(newWalletPayer);
            userService.updateUserWallet(payer);

            double newWalletBeneficiary = (beneficiary.getWallet() + (transactionRequest.getAmount()));

            beneficiary.setWallet(newWalletBeneficiary);
            userService.updateUserWallet(beneficiary);
        } catch (ArithmeticException e) {

            e.printStackTrace();
        }
    }

    public TransactionEntity mapEffectiveTransaction(TransactionRequest transactionRequest) {

        TransactionEntity transactionEntity = new TransactionEntity();

        try {
            transactionEntity.setAmount(transactionRequest.getAmount());
            transactionEntity.setDescription(transactionRequest.getDescription());
            UserEntity beneficiary = userService.getUser(transactionRequest.getBeneficiary());
            UserEntity payer = userService.getUser(transactionRequest.getPayer());
            transactionEntity.setBeneficiary(beneficiary);
            transactionEntity.setPayer(payer);
            transactionEntity.setCommission(transactionRequest.getAmount()*COMMISSION);
            log.debug("Update TransactionEntity is a success");
            return transactionEntity;
        } catch (NotConformDataException e) {
            e.printStackTrace();
        }
        log.error("Can't update TransactionEntity");
        return null;
    }


    public List<TransactionEntity> displayedTransactionWhenUserIsBeneficiary() {

        UserEntity userEntity = userService.getCurrentUser();
        try {
            List<TransactionEntity> transactionEntities = transactionService.findAllTransactionsByBeneficiaryEmail(userEntity.getEmail());
            return transactionEntities;
        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
        }
        log.error("Can't find Transaction List of {}", userEntity);
        return Collections.emptyList();
    }

    public List<TransactionEntity> displayedTransactionWhenUserIsPayer() {

        UserEntity userEntity = userService.getCurrentUser();
        try {
            List<TransactionEntity> transactionEntities = transactionService.findAllTransactionsByPayerEmail(userEntity.getEmail());
            return transactionEntities;
        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
        }
        log.error("Can't find Transaction List of {}", userEntity);
        return Collections.emptyList();
    }

}


