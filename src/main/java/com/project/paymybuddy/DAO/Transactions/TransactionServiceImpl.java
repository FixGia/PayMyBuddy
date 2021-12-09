package com.project.paymybuddy.DAO.Transactions;


import com.project.paymybuddy.Exception.DataNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {


    TransactionRepository transactionRepository;

    @Override
    public Iterable<TransactionEntity> findAllTransactions(Long id) {
        try {
            log.info("All transaction was found");
            return transactionRepository.findAll();
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error("Data was not found");
        return null;
    }

    @Override
    public List<TransactionEntity> findAllTransactionsByBeneficiaryEmail(String email) {
        try {
            log.info("All transaction was found");
            return transactionRepository.findTransactionEntitiesByBeneficiary_Email(email);
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }

        log.error("Data was not found");
        return null;

    }

    @Override
    public List<TransactionEntity> findAllTransactionsByPayerEmail(String email) {
        try {
            log.info("All transaction was found");
            return transactionRepository.findTransactionEntitiesByPayer_Email(email);
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }

        log.error("Data was not found");
        return null;

    }




    @Override
    public Optional<TransactionEntity> findTransactionById(Long id) {
        try {
            log.info("transaction with id :" + id + " was found");
            return transactionRepository.findById(id);
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error(" Data was not found");
        return Optional.empty();
    }


    @Override
    public Optional<TransactionEntity> deleteTransaction(Long id) {
        try {
            log.info(" transaction id:"+ id+ "was deleted");
            Optional<TransactionEntity> transactionToDelete = transactionRepository.findById(id);
            transactionRepository.delete(transactionToDelete.get());
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error(" Data was not found");
        return Optional.empty();
    }

    @Override
    public TransactionEntity saveTransaction(TransactionEntity transactionEntity) {

        return transactionRepository.save(transactionEntity);

    }


    @Override
    public Optional<TransactionEntity> updateTransaction(TransactionEntity transactionEntity, Long id) {
        try {
            Optional<TransactionEntity> transactionToUpdate = transactionRepository.findById(id);
            if (transactionToUpdate.isPresent()) {
                TransactionEntity currentTransactionEntity = transactionToUpdate.get();

                double amount = transactionEntity.getAmount();
                if (amount != currentTransactionEntity.getAmount()) {
                    currentTransactionEntity.setAmount(amount);
                }
                double commission = transactionEntity.getCommission();
                if (commission != currentTransactionEntity.getCommission()) {
                    currentTransactionEntity.setCommission(commission);
                }
                String description = transactionEntity.getDescription();
                if (description != currentTransactionEntity.getDescription()) {
                    currentTransactionEntity.setDescription(description);
                }
                transactionRepository.save(currentTransactionEntity);
                log.info("transaction was updated !");
                return Optional.of(currentTransactionEntity);
            }

        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
        }
        log.error(" Transfer wasn't updated !");
        return Optional.of(transactionEntity);
    }
}





