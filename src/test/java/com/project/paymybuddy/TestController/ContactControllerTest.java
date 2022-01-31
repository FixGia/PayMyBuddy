package com.project.paymybuddy.TestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.Service.ContactService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;

    private static UserEntity user;

    @MockBean
    private UserService userService;

    @MockBean
    private ContactService contactService;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private UserDetailsService userDetailsService;



    @BeforeEach
    public void contextLoads(){

        objectMapper = new ObjectMapper();
        user = new UserEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    @WithMockUser()
    public void displayedContactListResponseExpected200() throws Exception{

        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/Contacts")).andExpect(view().name("Contacts")).andExpect(status().isOk());

    }


    @Test
    @WithMockUser()
    public void addContactToContactListResponseExpected200() throws Exception{
        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/addContact")
                        .param("email", "test@gmail.com"))
                .andExpect(view().name("Contacts"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void deleteContactResponseExpected200() throws Exception{

        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/deleteContact")
                        .param("email", "test@gmail.com"))
                .andExpect(view().name("Contacts"))
                .andExpect(status().isOk());

    }

}
