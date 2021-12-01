package com.project.paymybuddy.DAO.Transfers;

import java.util.Optional;

public interface TransferService {

    Iterable<TransferEntity> findAll();

    Optional<TransferEntity> findById(Long id);

    Optional<TransferEntity> deleteTransfer(Long id);

    TransferEntity saveTransfer(TransferEntity transfer);

    Optional<TransferEntity> updateTransfer(Long id, TransferEntity transferEntity);
}
