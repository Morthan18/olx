package com.snokant.projekt.ControllersTests;

import com.snokant.projekt.Configuration.JwtConfiguration.JwtGen;
import com.snokant.projekt.Controller.UserController;
import com.snokant.projekt.Model.User;
import com.snokant.projekt.Service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTests {
    @InjectMocks
    UserController userController;
    private final String userJson = "{ \"firstName\":\"tester\", \"lastName\":\"huj\", \"password\":\"222222\", \"email\":\"admin3@wp.pl\", \"phone_number\":987654321, \"newsletter\":false}";
    private MockMvc mockMvc;


    @Mock
    UserServiceImpl userService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

   // @Test
   // public void addUserTest() throws Exception {
//        when(userService.addNewUser(new User("kamil.joka@wp.pl", "12345678"))).thenReturn(Arrays.asList("GIT", "Dodano użytkownika"));
//
//        mockMvc.perform(post("/rest/user/addUser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(userJson))
//                .andExpect(status().isOk());
   // }

    @Test
    public void modifyProfileTest() throws Exception {
        JwtGen generator = new JwtGen();
        User u = new User("krzys@wp.pl","krzys","snopko","Augustow","16-390",123127321);

        when(userService.modifyProfile(u,"token")).thenReturn(Arrays.asList("GIT", "Zmodyfikowano profil", "Nowy token to:"+generator.generateToken(u)));

        mockMvc.perform(post("/rest/user/modifyProfile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().is(400));



    }
}
