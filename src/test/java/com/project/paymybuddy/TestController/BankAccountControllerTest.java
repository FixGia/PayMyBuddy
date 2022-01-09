package com.project.paymybuddy.TestController;

import com.project.paymybuddy.DAO.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.DAO.BankAccounts.BankAccountServiceImpl;
import com.project.paymybuddy.DAO.User.UserEntity;
import com.project.paymybuddy.Domain.Service.Implementation.UserServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BankAccountControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    BankAccountServiceImpl bankAccountService;
    @MockBean
    UserServiceImpl userService;


    @Test
    @WithMockUser()
    public void TestLinkUserToBankAccountResponseExpected200() throws Exception {
        mvc.perform(post("/api/user/bankaccount").contentType(MediaType.APPLICATION_JSON).content("{\"amount\":\"10000\",\"iban\":\"fr123456789\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestLinkUserToBankAccountWithoutAuthorizationResponseExpected403() throws Exception {
        mvc.perform(post("/api/user/bankaccount").param("amount", String.valueOf(100)).param("iban", "Fr123456789").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser()
    public void TestGetBankAccountByUserResponseExpected200() throws Exception {

        BankAccountEntity bankAccount= new BankAccountEntity();
        UserEntity user = new UserEntity();
        lenient().when(userService.getCurrentUser()).thenReturn(user);
        lenient().when(bankAccountService.findBankAccountByUserEmail(user.getEmail())).thenReturn(Optional.of(bankAccount));
        mvc.perform(get("/api/user/bankaccount").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void TestGetBankAccountByUserWithoutAuthorizationResponseExpected403() throws Exception {
        mvc.perform(get("/api/user/bankaccount").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


}
