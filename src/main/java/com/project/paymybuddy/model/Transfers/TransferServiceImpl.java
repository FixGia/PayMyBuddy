package com.project.paymybuddy.model.Transfers;

import com.project.paymybuddy.Exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Slf4j
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

    TransferRepository transferRepository;

    @Override
    public Iterable<TransferEntity> findAll() {

        try {
            log.info("All transfer was found");
            return transferRepository.findAll();
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
    public Optional<TransferEntity> deleteTransfer(Long id) {

        try {
            log.info(" transfer id:" + id + "was deleted");
            Optional<TransferEntity> transferToDelete = transferRepository.findById(id);
            transferRepository.delete(transferToDelete.get());
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
