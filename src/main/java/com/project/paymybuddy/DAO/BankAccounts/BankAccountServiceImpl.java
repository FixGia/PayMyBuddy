package com.project.paymybuddy.DAO.BankAccounts;

import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.DAO.User.UserService;
import com.project.paymybuddy.Domain.DTO.BankAccountRequest;
import com.project.paymybuddy.Exception.BalanceInsufficientException;
import com.project.paymybuddy.Exception.DataAlreadyExistException;
import com.project.paymybuddy.Exception.DataNotFoundException;
import com.project.paymybuddy.Exception.NotConformDataException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@AllArgsConstructor
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountEntityRepository bankAccountEntityRepository;
    private final UserService userService;


    @Override
    public Iterable<BankAccountEntity> findAllBankAccountByUserEmail(@NotNull String email) {

        try {
            List<BankAccountEntity> bankAccountEntities = bankAccountEntityRepository.findAllByUserEmail(email);
            log.info("All BankAccount was found");
            return bankAccountEntities;

        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error("Data was not found");
        return null;
    }


    @Override
    public Optional<BankAccountEntity> findBankAccountByUserEmail(@NotNull String email) {
        try {
            log.info(" BankAccount Belong to: {} was found ", email);
            return bankAccountEntityRepository.findBankAccountEntityByUserEmail(email);
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error(" BankAccount was not found");
        return Optional.empty();
    }


    @Override
    public Optional<BankAccountEntity> updateBankAccount(BankAccountEntity bankAccount) {

        try {
            Optional<BankAccountEntity> bankAccountToUpdate = bankAccountEntityRepository.findBankAccountEntityByUserEmail(bankAccount.getUser().getEmail());
            if (bankAccountToUpdate.isPresent()) {
                BankAccountEntity currentBankAccount = bankAccountToUpdate.get();

                double amount = bankAccount.getAmount();
                if (amount != currentBankAccount.getAmount()) {
                    currentBankAccount.setAmount(amount);
                }
                log.info("bankAccount was updated !");
                bankAccountEntityRepository.save(currentBankAccount);
                return Optional.of(currentBankAccount);
            }
        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
        }
        log.error(" BankAccount wasn't updated !");
        return Optional.of(bankAccount);
    }

    @Override
    public void deleteByUserEmail(String email) {
        try {
            Optional<BankAccountEntity> bankAccountToDelete = bankAccountEntityRepository.findBankAccountEntityByUserEmail(email);
            bankAccountEntityRepository.delete(bankAccountToDelete.get());
            log.info(" BankAccount Belong to: {} was deleted", email);
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
            log.error(" Data was not found");
        }
    }

    @Transactional
    public BankAccountEntity LinkUserToBankAccount(@NotNull BankAccountRequest bankAccountRequest) {

        UserEntity currentUser = userService.getCurrentUser();

        prepareLinkBankAccountToUser(bankAccountRequest);

        if (currentUser != null) {
            BankAccountEntity newBankAccount = new BankAccountEntity();
            newBankAccount.setIban(bankAccountRequest.getIban());
            newBankAccount.setAmount(bankAccountRequest.getAmount());
            newBankAccount.setUser(currentUser);

            bankAccountEntityRepository.save(newBankAccount);
            log.info("BankAccount was link with User : {}", currentUser);
            return newBankAccount;
        }
        log.error("BankAccount wasn't link with User");
        return null;
    }

    public void prepareLinkBankAccountToUser(BankAccountRequest bankAccountRequest) {

        UserEntity currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            throw new DataNotFoundException("Current user isn't present");
        }
        if (bankAccountRequest.getAmount() < 0) {
            throw new BalanceInsufficientException("Amount must be positive");
        }

        BankAccountEntity bankAccountEntity = bankAccountEntityRepository.findBankAccountEntityByIban(bankAccountRequest.getIban());
        if (bankAccountEntity != null) {
            throw new DataAlreadyExistException(
                    "BankAccount already exist");
        }

    }
}



