package com.project.paymybuddy.Domain.Util;

import com.project.paymybuddy.Domain.DTO.BankAccountDTO;
import com.project.paymybuddy.Domain.DTO.TransactionDTO;
import com.project.paymybuddy.Domain.DTO.TransferDTO;
import com.project.paymybuddy.Exception.NotConformDataException;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.DAO.Transfers.TransferEntity;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MapDAO {


    public TransferEntity TransferEntityMapper(@NotNull TransferDTO transfer) {

        TransferEntity transferEntity = new TransferEntity();

       try{ transferEntity.setId(transfer.getId());
        transferEntity.setAmount(transfer.getAmount());
        transferEntity.setCredit(transfer.getCredit());
        log.debug("Update TransferEntity is a success");
        return transferEntity;
    } catch (NotConformDataException e ){
           e.printStackTrace();
       }
       log.error("Can't update TransferEntity");
       return null;
    }

    public TransferDTO TransferMapper(@NotNull TransferEntity transferEntity) {

        TransferDTO transferDTO = new TransferDTO();

        try {
            transferDTO.setId(transferEntity.getId());
            transferDTO.setAmount(transferEntity.getAmount());
            transferDTO.setCredit(transferEntity.getCredit());
            log.debug("Update Transfer is a success");
            return transferDTO;
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
            transactionDTO.setId(transactionEntity.getId());
            transactionDTO.setCommission(transactionEntity.getCommission());

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
            transactionEntity.setId(transactionDTO.getId());
            transactionEntity.setCommission(transactionDTO.getCommission());
            log.debug("Update TransactionEntity is a success");
            return transactionEntity;
        } catch (NotConformDataException e) {
            e.printStackTrace();
        }
        log.error("Can't update TransactionEntity");
        return null;
    }
    public BankAccountDTO BankAccountMapper(@NotNull BankAccountEntity bankAccountEntity) {

        BankAccountDTO bankAccountDTO = new BankAccountDTO();

        try {
        bankAccountDTO.setIdBankAccount(bankAccountEntity.getIdBankAccount());
        bankAccountDTO.setRib(bankAccountEntity.getRib());
        bankAccountDTO.setAmount(bankAccountEntity.getAmount());
        log.debug("Update BankAccount is a success");
        return bankAccountDTO;
    } catch (NotConformDataException e) {
            e.printStackTrace();
        }
    log.error("Can't update BankAccount with BankAccountEntity");
        return null;
    }
    public BankAccountEntity BankAccountEntityMapper(@NotNull BankAccountDTO bankAccountDTO) {

        BankAccountEntity bankAccountEntity = new BankAccountEntity();

        try {
            bankAccountEntity.setIdBankAccount(bankAccountDTO.getIdBankAccount());
            bankAccountEntity.setRib(bankAccountDTO.getRib());
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
