package com.project.paymybuddy.TestController;

import com.project.paymybuddy.Domain.Service.Implementation.InternalTransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InternalTransactionControllerTest {


    @Autowired
    private MockMvc mvc;
    @MockBean
    InternalTransactionServiceImpl internalTransactionService;


    @Test
    @WithMockUser()
    public void CreditBankAccountTransactionTest() throws Exception {

        mvc.perform(post("/api/user/transfer/credit").contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }
    @Test
    public void CreditBankAccountTransactionWithoutAuthorizationTest() throws Exception {

        mvc.perform(post("/api/user/transfer/credit").contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser()
    public void DebitBankAccountTransactionTest() throws Exception {

        mvc.perform(post("/api/user/transfer/debit").contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void DebitBankAccountTransactionWithoutAuthorizationTest() throws Exception {

        mvc.perform(post("/api/user/transfer/debit").contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    public void DisplayAllTransferTest() throws Exception {

        mvc.perform(get("/api/user/transfer/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test

    public void DisplayAllTransferWithoutAuthorizationTest() throws Exception {

        mvc.perform(get("/api/user/transfer/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}
