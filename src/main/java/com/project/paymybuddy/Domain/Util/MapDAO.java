package com.project.paymybuddy.Domain.Util;

import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserService;
import com.project.paymybuddy.Domain.DTO.BankAccountRequest;
import com.project.paymybuddy.Domain.DTO.TransactionDTO;
import com.project.paymybuddy.Domain.DTO.TransferRequest;
import com.project.paymybuddy.Exception.NotConformDataException;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.DAO.Transfers.TransferEntity;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapDAO {

    private final UserService userService;
    private static final double COMMISSION = 0.05;

    public TransferEntity TransferEntityMapper(@NotNull TransferRequest transfer) {

        TransferEntity transferEntity = new TransferEntity();

       try{
           transferEntity.setAmount(transfer.getAmount());
           log.debug("Update TransferEntity is a success");
        return transferEntity;
    } catch (NotConformDataException e ){
           e.printStackTrace();
       }
       log.error("Can't update TransferEntity");
       return null;
    }

    public TransferRequest TransferMapper(@NotNull TransferEntity transferEntity) {

        TransferRequest transferRequest = new TransferRequest();

        try {
            transferRequest.setAmount(transferEntity.getAmount());
            log.debug("Update Transfer is a success");
            return transferRequest;
        } catch (NotConformDataException e) {
            e.printStackTrace();
        }
        log.error("Can't update Transfer");
        return null;
    }
    public TransactionDTO TransactionMapper( @NotNull TransactionEntity transactionEntity) {

        TransactionDTO transactionDTO = new TransactionDTO();
        try {
            transactionDTO.setAmount(transactionEntity.getAmount());
            transactionDTO.setDescription(transactionEntity.getDescription());


            log.debug("Update Transaction is a success");
            return transactionDTO;

        } catch (NotConformDataException e) {
            e.printStackTrace();
        }
        log.error("Can't update Transaction");
        return null;
    }
    public TransactionEntity TransactionEntityMapper( @NotNull TransactionDTO transactionDTO) {

        TransactionEntity transactionEntity = new TransactionEntity();

        try {
            transactionEntity.setAmount(transactionDTO.getAmount());
            transactionEntity.setDescription(transactionDTO.getDescription());
            UserEntity beneficiary = userService.getUser(transactionDTO.getBeneficiary());
            UserEntity payer = userService.getUser(transactionDTO.getPayer());
            transactionEntity.setBeneficiary(beneficiary);
            transactionEntity.setPayer(payer);
            transactionEntity.setCommission(transactionDTO.getAmount()*COMMISSION);
            log.debug("Update TransactionEntity is a success");
            return transactionEntity;
        } catch (NotConformDataException e) {
            e.printStackTrace();
        }
        log.error("Can't update TransactionEntity");
        return null;
    }
    public BankAccountRequest BankAccountMapper(@NotNull BankAccountEntity bankAccountEntity) {

        BankAccountRequest bankAccountRequest = new BankAccountRequest();

        try {

        bankAccountRequest.setIban(bankAccountEntity.getIban());
        bankAccountRequest.setAmount(bankAccountEntity.getAmount());
        log.debug("Update BankAccount is a success");
        return bankAccountRequest;
    } catch (NotConformDataException e) {
            e.printStackTrace();
        }
    log.error("Can't update BankAccount with BankAccountEntity");
        return null;
    }
    public BankAccountEntity BankAccountEntityMapper(@NotNull BankAccountRequest bankAccountRequest) {

        BankAccountEntity bankAccountEntity = new BankAccountEntity();

        try {
            bankAccountEntity.setIban(bankAccountRequest.getIban());
            bankAccountEntity.setAmount(bankAccountEntity.getAmount());
            log.debug("Update BankAccountEntity is a success");
            return bankAccountEntity;
        } catch (NotConformDataException exception) {
            exception.printStackTrace();
        }
        log.error("Can't update BankAccountEntity with BankAccount");
        return null;
    }
}
