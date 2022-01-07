package com.project.paymybuddy.TestController;

import com.project.paymybuddy.Domain.Controller.ContactController;
import com.project.paymybuddy.Domain.Service.ContactService;
import com.project.paymybuddy.Domain.Service.Implementation.ContactServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ContactServiceImpl contactService;

    @Before
    public void setup() {
    }

    @Test
    public void ContextLoads() {
    }

    @WithMockUser()
    @Test
    public void displayedContactResponseExpected200() throws Exception {
        mvc.perform(get("/api/user/contact").param("email", "Test5g@gmail.com").contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk());
    }

    @Test
    public void displayedContactWithoutAuthorizationExpected403() throws Exception{
        mvc.perform(get("/api/user/contact").param("email", "Test5g@gmail.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser()
    public void displayedContactListResponseExpected200() throws Exception{
        mvc.perform(get("/api/user/contacts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void displayedContactListWithoutAuthorizationResponseExpected403() throws Exception{
        mvc.perform(get("/api/user/contacts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    public void addContactToContactListResponseExpected200() throws Exception{
        mvc.perform(post("/api/user/contact").param("email", "Test5g@gmail.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addContactToContactListWithoutAuthorizationResponseExpected403() throws Exception{
        mvc.perform(post("/api/user/contacts").param("email", "Test5g@gmail.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser()
    public void deleteContactResponseExpected200() throws Exception{
        mvc.perform(delete("/api/user/contact").param("email", "Test5g@gmail.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteContactWithoutAuthorizationResponseExpected403() throws Exception{
        mvc.perform(delete("/api/user/contact").param("email", "Test5g@gmail.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
