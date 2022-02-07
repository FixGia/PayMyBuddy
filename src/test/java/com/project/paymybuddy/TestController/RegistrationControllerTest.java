package com.project.paymybuddy.TestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.DTO.RegistrationRequest;
import com.project.paymybuddy.Service.RegistrationService;
import com.project.paymybuddy.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mvc;


    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;

    private static UserEntity user;

    private static RegistrationRequest request;

    @MockBean
    private UserService userService;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        user = new UserEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");

        request = new RegistrationRequest();
        request.setCivility("Mr");
        request.setEmail("JeanTestPAs@gmail.com");
        request.setFirstname("Jeanne");
        request.setLastname("Test");

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testDisplaySignUpPerform() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/checkout"))
                .andExpect(view().name("/checkout"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegister() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .param("civility", request.getCivility())
                        .param("firstname", request.getFirstname())
                        .param("lastname", request.getLastname())
                        .param("email", request.getEmail()))
                .andExpect(view().name("/Index"))
                .andExpect(status().isOk());
    }

}
