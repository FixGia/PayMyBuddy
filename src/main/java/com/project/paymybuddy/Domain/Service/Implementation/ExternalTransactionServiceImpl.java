package com.project.paymybuddy.Domain.Service.Implementation;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.DTO.*;
import com.project.paymybuddy.Domain.Service.ExternalTransactionService;
import com.project.paymybuddy.Domain.Util.MapDAO;
import com.project.paymybuddy.Exception.BalanceInsufficientException;
import com.project.paymybuddy.Exception.DataNotFoundException;
import com.project.paymybuddy.Exception.NotConformDataException;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntityRepository;
import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.DAO.Transactions.TransactionService;
import com.project.paymybuddy.DAO.User.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
    private final MapDAO mapDAO;


    @Transactional
    public Optional<TransactionEntity> makeTransaction(@NotNull TransactionDTO transactionDTO) {


        try {
            UserEntity currentUser = userService.getCurrentUser();
            UserEntity payer = userService.getUser(transactionDTO.getPayer());
            UserEntity beneficiary = userService.getUser(transactionDTO.getBeneficiary());

            if (currentUser == payer) {
                prepareTransaction(transactionDTO);

                CalculateQualifyTransaction(transactionDTO);

                updatePayerAndBeneficiaryWalletAfterTransaction(transactionDTO);

                saveEffectiveTransaction(transactionDTO);

                userService.updateUsers(payer);

                userService.updateUsers(beneficiary);

                TransactionEntity transactionEntity = mapDAO.TransactionEntityMapper(transactionDTO);

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

    public void prepareTransaction(@NotNull TransactionDTO transactionDTO) {

        if (transactionDTO.getPayer() == null) {
            throw new NotConformDataException("Payer cannot be null");
        }

        if (transactionDTO.getBeneficiary() == null) {
            throw new NotConformDataException(
                    "Beneficiary cannot be null");
        }

        if (transactionDTO.getDescription()
                .length() > DESCRIPTION_LENGTH) {

            throw new NotConformDataException(
                    "description must be 30 characters maximum");
        }
        if (transactionDTO.getDescription().length() == 0) {
            throw new NotConformDataException("description can't be empty");
        }
        if (transactionDTO.getAmount() <= 0) {
            throw new BalanceInsufficientException("amount must be a positive value");
        }
        if (transactionDTO.getPayer() == null) {

            throw new DataNotFoundException("payer not found in DB");
        }

    }

    public boolean CalculateQualifyTransaction(@NotNull TransactionDTO transactionDTO) {


        UserEntity payer = userService.getUser(transactionDTO.getPayer());
        double wallet = payer.getWallet();
        double amount = transactionDTO.getAmount();
        if (wallet - (amount + (amount * COMMISSION)) >= 0) {
            log.info("Wallet is sufficient to do Transaction");
            return true;
        }
        log.info("Wallet is not sufficient to do Transaction");
        throw new BalanceInsufficientException("Wallet is not sufficient to do Transaction");
    }

    public void updatePayerAndBeneficiaryWalletAfterTransaction(@NotNull TransactionDTO transactionDTO) {

        UserEntity payer = userService.getUser(transactionDTO.getPayer());
        UserEntity beneficiary = userService.getUser(transactionDTO.getBeneficiary());

        try {
            double newWalletPayer = (payer.getWallet() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * COMMISSION)));

            payer.setWallet(newWalletPayer);

            double newWalletBeneficiary = (beneficiary.getWallet() + (transactionDTO.getAmount()));

            beneficiary.setWallet(newWalletBeneficiary);

        } catch (ArithmeticException e) {

            e.printStackTrace();
        }
    }

    public TransactionEntity saveEffectiveTransaction(TransactionDTO transactionDTO) {

        TransactionEntity transaction = mapDAO.TransactionEntityMapper(transactionDTO);
        transactionService.saveTransaction(transaction);
        return transaction;
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


