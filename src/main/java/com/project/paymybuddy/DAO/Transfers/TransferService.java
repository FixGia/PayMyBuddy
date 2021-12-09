package com.project.paymybuddy.DAO.Transfers;

import com.project.paymybuddy.DAO.User.UserEntity;

import java.util.List;
import java.util.Optional;

public interface TransferService {

    List<TransferEntity> findAllByUser(UserEntity userEntity);

    Optional<TransferEntity> findById(Long id);

    Optional<TransferEntity> deleteTransfer(Long id);

    TransferEntity saveTransfer(TransferEntity transfer);

    Optional<TransferEntity> updateTransfer(Long id, TransferEntity transferEntity);
}
