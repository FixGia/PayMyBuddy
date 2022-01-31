package com.project.paymybuddy.Service;

import com.project.paymybuddy.Entity.Transfers.TransferEntity;
import com.project.paymybuddy.Entity.User.UserEntity;

import java.util.List;
import java.util.Optional;

public interface TransferService {

    List<TransferEntity> findAllByUser(UserEntity userEntity);

    Optional<TransferEntity> findById(Long id);


    TransferEntity saveTransfer(TransferEntity transfer);

    Optional<TransferEntity> updateTransfer(Long id, TransferEntity transferEntity);
}
