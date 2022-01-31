package com.project.paymybuddy.TestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.paymybuddy.Service.BankAccountService;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.DTO.BankAccountRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BankAccountControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;

    private static UserEntity user;
    private static BankAccountRequest bankAccountRequest;

    @MockBean
    private UserService userService;
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private BankAccountService bankAccountService;


    @BeforeEach
    public void contextLoads(){

        objectMapper = new ObjectMapper();
        user = new UserEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");

        bankAccountRequest = new BankAccountRequest();
        bankAccountRequest.setAmount(1000);
        bankAccountRequest.setIban("TesT123456789");



        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(userService.saveUser(user)).thenReturn(user);


    }

    @Test
    @WithMockUser()
    public void TestLinkUserToBankAccount() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.post("/addBankAccount")
                .param("amount" , String.valueOf(1000))
                .param("Iban", bankAccountRequest.getIban()))
                .andExpect(view().name("Home"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void TestLinkBankForUser() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/BankAccount"))
                .andExpect(model().attributeExists("currentUser"))
                .andExpect(view().name("FormBankAccount"))
                .andExpect(status().isOk());
    }


}
