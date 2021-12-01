package com.project.paymybuddy.Domain.Service.Implementation;
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
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class ExternalTransactionServiceImpl implements ExternalTransactionService {

    private static final double DESCRIPTION_LENGTH = 30;

    UserService userService;
    BankAccountEntityRepository bankAccountEntityRepository;
    TransactionService transactionService;


    MapDAO mapDAO;


    @Transactional
    public Optional<TransactionEntity> makeTransaction(@NotNull TransactionDTO transactionDTO) {


        try {
            prepareTransaction(transactionDTO);


            CalculateQualifyTransaction(transactionDTO);

            updatePayerAndBeneficiaryWalletAfterTransaction(transactionDTO);

            saveEffectiveTransaction(transactionDTO);

            userService.updateUsers(transactionDTO.getPayer().getId(), transactionDTO.getPayer());

            userService.updateUsers(transactionDTO.getBeneficiary().getId(), transactionDTO.getBeneficiary());

            TransactionEntity transaction = mapDAO.TransactionEntityMapper(transactionDTO);

            transactionService.saveTransaction(transaction);

            log.info("Transaction Success");
            return Optional.of(transaction);

        } catch (NotConformDataException e) {
            e.printStackTrace();
        }

        log.error("Transaction fail");
        return Optional.empty();
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
        if (!userService.findUsersById(transactionDTO
                .getPayer().getId()).isPresent()) {

            throw new DataNotFoundException("payer not found in DB");
        }
    }
    public boolean CalculateQualifyTransaction(TransactionDTO transactionDTO) {

       double commission = transactionDTO.getAmount()*(transactionDTO.getAmount()*0.05);
       transactionDTO.setCommission(commission);
        double wallet = transactionDTO.getPayer().getWallet();
        double amount = transactionDTO.getAmount();
        if (wallet - (amount+commission) >=0) {
            log.info("Wallet is sufficient to do Transaction");
           return true;
        }
        log.info("Wallet is not sufficient to do Transaction");
        return false;
    }
    public void updatePayerAndBeneficiaryWalletAfterTransaction(TransactionDTO transactionDTO) {

        try {
            double newWalletPayer = (transactionDTO.getPayer().getWallet() - (transactionDTO.getAmount() + transactionDTO.getCommission()));

            transactionDTO.getPayer().setWallet(newWalletPayer);

            double newWalletBeneficiary = (transactionDTO.getBeneficiary().getWallet() + (transactionDTO.getAmount() + transactionDTO.getCommission()));

            transactionDTO.getBeneficiary().setWallet(newWalletBeneficiary);

        } catch (ArithmeticException e) {

            e.printStackTrace();
        }
    }

    public TransactionEntity saveEffectiveTransaction (TransactionDTO transactionDTO) {
        TransactionEntity transaction = mapDAO.TransactionEntityMapper(transactionDTO);
        transactionService.saveTransaction(transaction);
        return transaction;
    }

}


