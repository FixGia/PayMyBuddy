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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class InternalTransactionController {

private final InternalTransactionService internalTransactionService;
private final TransferService transferService;
private final UserService userService;



}
