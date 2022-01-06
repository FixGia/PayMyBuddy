package com.project.paymybuddy.TestController;

import com.project.paymybuddy.DAO.Transactions.TransactionEntity;
import com.project.paymybuddy.Domain.DTO.TransactionDTO;
import com.project.paymybuddy.Domain.Service.Implementation.ExternalTransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ExternalTransactionControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    ExternalTransactionServiceImpl externalTransactionService;

    @Test
    @WithMockUser()
    public void makeTransactionTest() throws Exception {

        TransactionEntity transactionEntity = new TransactionEntity();
        lenient().when(externalTransactionService.makeTransaction(any(TransactionDTO.class))).thenReturn(Optional.of(transactionEntity));
        mvc.perform(post("/api/user/transaction").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":\"10000\",\"beneficiary\":\"homme1\",\"payer\":\"homme2\",\"description\":\"testé\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void makeTransactionWithoutAuthorizationTest() throws Exception {

        TransactionEntity transactionEntity = new TransactionEntity();
        lenient().when(externalTransactionService.makeTransaction(any(TransactionDTO.class))).thenReturn(Optional.of(transactionEntity));
        mvc.perform(post("/api/user/transaction").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":\"10000\",\"beneficiary\":\"homme1\",\"payer\":\"homme2\",\"description\":\"testé\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    public void displayedTransactionByBeneficiaryTest() throws Exception {


        mvc.perform(get("/api/user/transaction/beneficiary").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void displayedTransactionByBeneficiaryWithoutAuthorizationTest() throws Exception {


        mvc.perform(get("/api/user/transaction/beneficiary").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    public void displayedTransactionByPayerTest() throws Exception {


        mvc.perform(get("/api/user/transaction/payer").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void displayedTransactionByPayerWithoutAuthorizationTest() throws Exception {


        mvc.perform(get("/api/user/transaction/payer").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
