package com.project.paymybuddy.DAO.Transfers;

import com.project.paymybuddy.DAO.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

    List<TransferEntity> findAllByUserEntity(UserEntity userEntity);
}