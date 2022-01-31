package com.project.paymybuddy.Service.Implementation;

import com.project.paymybuddy.Entity.Transfers.TransferEntity;
import com.project.paymybuddy.Entity.Transfers.TransferRepository;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.Exception.DataNotFoundException;
import com.project.paymybuddy.Service.TransferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

    TransferRepository transferRepository;

    @Override
    public List<TransferEntity> findAllByUser(UserEntity userEntity) {

        try {
            log.info("All transfer was found");
            return transferRepository.findAllByUserEntity(userEntity);
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error("Data was not found");
        return null;
    }

    @Override
    public Optional<TransferEntity> findById(Long id) {
        try {
            log.info("transfer id :" + id + " was found");
            return transferRepository.findById(id);
        } catch (DataNotFoundException dataNotFoundException) {
            dataNotFoundException.printStackTrace();
        }
        log.error(" Data was not found");
        return Optional.empty();
    }


    @Override
    public TransferEntity saveTransfer(TransferEntity transfer) {

        return transferRepository.save(transfer);
    }

    @Override
    public Optional<TransferEntity> updateTransfer(Long id, TransferEntity transferEntity) {

        try {
            Optional<TransferEntity> transferToUpdate = transferRepository.findById(id);
            if (transferToUpdate.isPresent()) {
                TransferEntity currentTransferEntity = transferToUpdate.get();

                double amount = transferEntity.getAmount();
                if (amount != currentTransferEntity.getAmount()) {
                    currentTransferEntity.setAmount(amount);
                }
                double credit = transferEntity.getCredit();
                if (credit != currentTransferEntity.getCredit()) {
                    currentTransferEntity.setCredit(credit);
                }
                double debit = transferEntity.getDebit();
                if (debit != currentTransferEntity.getDebit()) {
                    currentTransferEntity.setDebit(debit);
                }

                transferRepository.save(currentTransferEntity);
                log.info("Transfer was updated !");
                return Optional.of(currentTransferEntity);
            }

        } catch (DataNotFoundException exception) {
            exception.printStackTrace();
        }
        log.error(" Transfer wasn't updated !");
        return Optional.of(transferEntity);
    }
}
