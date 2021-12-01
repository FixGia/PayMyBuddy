package com.project.paymybuddy.DAO.BankAccounts;

import com.project.paymybuddy.Exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@AllArgsConstructor
@Service
public class BankAccountServiceImpl implements BankAccountService {

    BankAccountEntityRepository bankAccountEntityRepository;

    @Override
    public Iterable<BankAccountEntity> findAll() {
        try {
            log.info("All BankAccount was found");
            return bankAccountEntityRepository.findAll();
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error("Data was not found");
        return null;
    }

    @Override
    public Optional<BankAccountEntity> findBankAccountById(Integer id) {
        try {
            log.info("BankAccount with id :" + id + " was found");
            return bankAccountEntityRepository.findById(id);
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error(" Data was not found");
        return Optional.empty();
    }

    @Override
    public Optional<BankAccountEntity> findBankAccountByUserId(Long id) {
        try {
            log.info("User :" + id + " Bank Account was found");
            return bankAccountEntityRepository.findBankAccountEntityByUserId(id);
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error(" Data was not found");
        return Optional.empty();
    }


    @Override
    public Optional<BankAccountEntity> updateBankAccount(Integer id, BankAccountEntity bankAccount) {
        try {
            Optional<BankAccountEntity> bankAccountToUpdate = bankAccountEntityRepository.findById(id);
           if(bankAccountToUpdate.isPresent()) {
               BankAccountEntity currentBankAccount = bankAccountToUpdate.get();

               double amount = bankAccount.getAmount();
               if (amount != currentBankAccount.getAmount()) {
                   currentBankAccount.setAmount(amount);
               }
               log.info("bankAccount was updated !");
               return Optional.of(currentBankAccount);
           }
        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
        }
        log.error(" BankAccount wasn't updated !");
        return Optional.of(bankAccount);

    }

    @Override
    public void deleteById(Integer id) {

        try {
            log.info(" BankAccount with id:"+ id+ "was deleted");
            Optional<BankAccountEntity> bankAccountToDelete = bankAccountEntityRepository.findById(id);
            bankAccountEntityRepository.delete(bankAccountToDelete.get());
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error(" Data was not found");
    }
}

