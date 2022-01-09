package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.DAO.Transfers.TransferEntity;
import com.project.paymybuddy.DAO.Transfers.TransferService;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.Service.UserService;
import com.project.paymybuddy.Domain.DTO.TransferRequest;
import com.project.paymybuddy.Domain.Service.InternalTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class InternalTransactionController {

private final InternalTransactionService internalTransactionService;
private final TransferService transferService;
private final UserService userService;

    @PostMapping("api/user/transfer/credit")
    public ResponseEntity<TransferEntity> CreditBankAccountTransaction(@RequestBody TransferRequest transferRequest){


        TransferEntity newTransaction = internalTransactionService.DebitWalletToBankAccountTransfer(transferRequest);

        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }

    @PostMapping("api/user/transfer/debit")
    public ResponseEntity<TransferEntity> DebitBankAccountTransaction(@RequestBody TransferRequest transferRequest){

        TransferEntity newTransaction = internalTransactionService.CreditWalletWithBankAccountTransfer(transferRequest);

        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }

    @GetMapping("api/user/transfer/all")
    public ResponseEntity<?> displayedAllTransfer() {
        UserEntity currentUser = userService.getCurrentUser();
        List<TransferEntity> transferEntities = transferService.findAllByUser(currentUser);
        return ResponseEntity.ok(transferEntities);
    }
}
