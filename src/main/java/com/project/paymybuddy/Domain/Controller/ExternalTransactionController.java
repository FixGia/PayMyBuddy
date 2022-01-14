package com.project.paymybuddy.Domain.Controller;

import com.project.paymybuddy.Domain.DTO.TransactionRequest;
import com.project.paymybuddy.Domain.Service.ExternalTransactionService;
import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.Domain.Service.UserService;
import com.project.paymybuddy.Exception.NotConformDataException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@Slf4j
public class ExternalTransactionController {

    private ExternalTransactionService externalTransactionService;
    private UserService userService;






}
