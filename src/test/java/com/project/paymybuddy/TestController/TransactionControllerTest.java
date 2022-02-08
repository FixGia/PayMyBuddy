package com.project.paymybuddy.TestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.paymybuddy.Entity.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.Entity.Transactions.TransactionEntity;
import com.project.paymybuddy.Service.TransactionService;
import com.project.paymybuddy.Service.TransferService;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.DTO.TransactionRequest;
import com.project.paymybuddy.Service.ExternalTransactionService;
import com.project.paymybuddy.Service.InternalTransactionService;
import com.project.paymybuddy.Service.RegistrationService;
import com.project.paymybuddy.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;

    private static UserEntity user;
    private static UserEntity otherUser;
    private static BankAccountEntity bankAccount;

    @MockBean
    private UserService userService;
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private TransferService transferService;
    @MockBean
    private InternalTransactionService internalTransactionService;
    @MockBean
    private ExternalTransactionService externalTransactionService;


    @BeforeEach
    public void contextLoads(){

        objectMapper = new ObjectMapper();
        user = new UserEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");

        otherUser = new UserEntity();
        otherUser.setEmail("Test@gmail.com");
        otherUser.setFirstname("other");
        otherUser.setLastname("User");


        bankAccount = new BankAccountEntity();
        bankAccount.setIban("Test1234456");
        bankAccount.setUser(user);
        bankAccount.setAmount(1000.0);

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(userService.saveUser(user)).thenReturn(user);
    }

    @Test
    public void TestDisplayTransferAndTransaction() throws Exception{
        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/Transaction"))
                .andExpect(view().name("/transactions"))
                .andExpect(status().isOk());
    }


    @Test
    public void TestDisplayDebitFormTransfer() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.get("/transfer/debit"))
                .andExpect(view().name("DebitYourWallet"))
                .andExpect(status().isOk());

    }

    @Test
    public void TestDisplayCreditFormTransfer() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/transfer/credit"))
                .andExpect(view().name("CreditYourWallet"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestCreditBankAccountTransaction() throws Exception{
        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.post("/transfer/credit"))
                .andExpect(view().name("/transactions"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestDebitBankAccountTransaction() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.post("/transfer/debit"))
                .andExpect(view().name("/transactions"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void testMakeTransaction() throws Exception {

        when(userService.getCurrentUser()).thenReturn(user);
        TransactionEntity transactionEntity = new TransactionEntity();
        lenient().when(externalTransactionService.makeTransaction(any(TransactionRequest.class))).thenReturn(Optional.of(transactionEntity));
        mvc.perform(MockMvcRequestBuilders.post("/makeTransaction")
                        .param("amount", "100")
                        .param("beneficiary", "Other")
                        .param("payer", "Test")
                        .param("description", "test"))

                .andExpect(view().name("/transactions"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDisplayTransactionForm() throws Exception {

        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/FormTransaction"))
                .andExpect(view().name("/FormTransaction"))
                .andExpect(status().isOk());
    }
    }

