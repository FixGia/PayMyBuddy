package com.project.paymybuddy.TestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.paymybuddy.Entity.User.UserEntity;
import com.project.paymybuddy.DTO.UserRequest;
import com.project.paymybuddy.Service.RegistrationService;
import com.project.paymybuddy.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mvc;


    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;

    private static UserEntity user;

    private static UserRequest userRequest;

    @MockBean
    private UserService userService;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void setUp(){

        objectMapper = new ObjectMapper();
        user = new UserEntity();
        user.setFirstname("Jean");
        user.setLastname("Test");
        user.setEmail("JeanTest@gmail.com");

        userRequest = new UserRequest();
        userRequest.setEmail("JeanTestPAs@gmail.com");
        userRequest.setFirstname("Jeanne");
        userRequest.setLastname("Test");

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test

    public void GetProfileTest() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/Profile"))
                .andExpect(view().name("Profile"))
                .andExpect(model().attributeExists("userEntity"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void testUpdateProfile() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/UpdateProfile")).andExpect(view().name("/UpdateProfile")).andExpect(status().isOk());
    }

    @Test
    @DisplayName(" Url request /Profile/update-"+ " - when GET /UpdateProfile action request,"+ " then returns Profile Updated")
    @WithMockUser
    public void testUpdateProfilePost() throws Exception{
        when(userService.updateProfile(userRequest)).thenReturn(user);
        when(userService.getCurrentUser()).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.post("/Profile/update")
                .param("firstname", userRequest.getFirstname())
                .param("lastname", userRequest.getLastname())
                .param("email", userRequest.getEmail()))
                .andExpect(model().attributeExists("userEntity"))
                .andExpect(view().name("/Profile"))
                .andExpect(status().isOk());
    }
}

