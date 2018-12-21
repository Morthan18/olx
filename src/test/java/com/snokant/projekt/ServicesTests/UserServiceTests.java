package com.snokant.projekt.ServicesTests;

import com.snokant.projekt.Configuration.JwtConfiguration.JwtGen;
import com.snokant.projekt.Model.User;
import com.snokant.projekt.Repository.UserRepository;
import com.snokant.projekt.Service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTests {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Test
    public void addUserTest() {
        User user = new User("kamil.joka@wp.pl", "12345678");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        assertEquals(Arrays.asList("BLAD","Podany email jest już zajęty"), userService.addNewUser(user));

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(null);
        assertEquals(Arrays.asList("GIT","Zarejestrowano"), userService.addNewUser(user));

         }
}
