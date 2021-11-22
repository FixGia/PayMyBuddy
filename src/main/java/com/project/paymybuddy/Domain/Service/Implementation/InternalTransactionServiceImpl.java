package com.project.paymybuddy.Domain.Service.Implementation;
import com.project.paymybuddy.Domain.DTO.TransferDTO;
import com.project.paymybuddy.Domain.Util.MapDAO;
import com.project.paymybuddy.Exception.BalanceInsufficientException;
import com.project.paymybuddy.Exception.NotConformDataException;
import com.project.paymybuddy.model.BankAccounts.BankAccountService;
import com.project.paymybuddy.model.Transfers.TransferEntity;
import com.project.paymybuddy.model.Transfers.TransferService;
import com.project.paymybuddy.model.User.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class InternalTransactionServiceImpl {

    private static final double DESCRIPTION_LENGTH = 30;

    BankAccountService bankAccountService;
    UserService userService;
    TransferService transferService;
    MapDAO mapDAO;

    public TransferEntity makeWalletDebitToBankAccountTransfer(TransferDTO transferDTO) {

        try {
            prepareTransfer(transferDTO);

            CalculateQualifyDebitTransfer(transferDTO);

            updateBankAccountAndUserAfterDebitTransfer(transferDTO);

            saveEffectiveTransfer(transferDTO);

            userService.updateUsers(transferDTO.getUser().getId(), transferDTO.getUser());

            bankAccountService.updateBankAccount(transferDTO.getBankAccount().getIdBankAccount(), transferDTO.getBankAccount());

            TransferEntity transfer = mapDAO.TransferEntityMapper(transferDTO);

            transferService.saveTransfer(transfer);

            log.info("Transfer Debit Success");
            return transfer;
        } catch (NotConformDataException e) {
            e.printStackTrace();

        }
        log.error("Transfer Debit fail");
        return null;
    }

    public TransferEntity makeWalletCreditToBankAccountTransfer(TransferDTO transferDTO) {

        try {

            prepareTransfer(transferDTO);

            CalculateQualifyCreditTransfer(transferDTO);

            updateBankAccountAndUserAfterCreditTransfer(transferDTO);

            saveEffectiveTransfer(transferDTO);

            userService.updateUsers(transferDTO.getUser().getId(), transferDTO.getUser());

            bankAccountService.updateBankAccount(transferDTO.getBankAccount().getIdBankAccount(), transferDTO.getBankAccount());

            TransferEntity transfer = mapDAO.TransferEntityMapper(transferDTO);

            transferService.saveTransfer(transfer);

            log.info("Transfer Credit Success");
            return transfer;
        } catch (NotConformDataException e) {
            e.printStackTrace();

        }
        log.error("Transfer Credit fail");
        return null;
    }


    public void prepareTransfer(TransferDTO transferDTO) {

        if(!userService.findUsersById(transferDTO
                .getUser().getId()).isPresent()) {
            throw new NotConformDataException("Owner cannot be null");
        }
        if (!bankAccountService.findBankAccountById(transferDTO.getBankAccount()
                .getIdBankAccount()).isPresent()) {
            throw new NotConformDataException("BankAccount cannot be null");
        }
        if(transferDTO.getDescription().length() > DESCRIPTION_LENGTH){
            throw new NotConformDataException("description must be 30 characters maximum");
        }
        if(transferDTO.getAmount() <= 0){
            throw new BalanceInsufficientException("amount must be positive value");
        }
    }

    public boolean CalculateQualifyDebitTransfer(TransferDTO transferDTO) {

        double wallet = transferDTO.getUser().getWallet();
        double amount = transferDTO.getAmount();
        if (wallet - amount >= 0) {
            log.info("Wallet is sufficient to do Transfer");
            return true;
        }
        log.info("Wallet is not sufficient to do Transfer");
        return false;
    }

    public boolean CalculateQualifyCreditTransfer(TransferDTO transferDTO) {

    double wallet = transferDTO.getBankAccount().getAmount();
    double amount = transferDTO.getAmount();
        if (wallet - amount >= 0) {
            log.info("Wallet is sufficient to do Transfer");
            return true;
        }
        log.info("Wallet is not sufficient to do Transfer");
        return false;
    }
    public void updateBankAccountAndUserAfterDebitTransfer(TransferDTO transferDTO) {
    try {
        double newWalletUser = (transferDTO.getUser().getWallet() - (transferDTO.getAmount()));
        transferDTO.getUser().setWallet(newWalletUser);
        double newAmountBankAccount = (transferDTO.getBankAccount().getAmount()) - (transferDTO.getAmount());
        transferDTO.getBankAccount().setAmount(newAmountBankAccount);
    } catch (ArithmeticException e) {
        e.printStackTrace();
    }
}

    public void updateBankAccountAndUserAfterCreditTransfer(TransferDTO transferDTO) {
    try {
        double newWalletUser = (transferDTO.getUser().getWallet() + ( transferDTO.getAmount()));
        transferDTO.getUser().setWallet(newWalletUser);
        double newAmountBankAccount = (transferDTO.getBankAccount().getAmount() + (transferDTO.getAmount()));
        transferDTO.getBankAccount().setAmount(newAmountBankAccount);
}
    catch (ArithmeticException e) {
        e.printStackTrace();
    }
}
    public TransferEntity saveEffectiveTransfer(TransferDTO transferDTO) {

    TransferEntity transfer = mapDAO.TransferEntityMapper(transferDTO);
    transferService.saveTransfer(transfer);
    return transfer;
}
}


