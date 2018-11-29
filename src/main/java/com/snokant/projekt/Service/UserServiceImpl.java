package com.snokant.projekt.Service;

import com.snokant.projekt.Configuration.JwtConfiguration.JwtGen;
import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Domain.UserRequestLogin;
import com.snokant.projekt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtGen generator;


    public UserServiceImpl(UserRepository userRepository, JwtGen generator) {
        this.userRepository = userRepository;
        this.generator = generator;
    }
    @Transactional
    @Override
    public List<String> addNewUser(User user, BindingResult result) throws NullPointerException{
        ErrorChecker errorChecker = new ErrorChecker(userRepository);
        List<String> errors = errorChecker.checkErrors(user, result);
        if (errors == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            //authenticateUser(user);
            return Arrays.asList("GIT","Zarejestrowano");
        }
        return errors;
    }
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User applicationUser = userRepository.findUserByEmail(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getEmail(), applicationUser.getPassword());
    }

    @Override
    public List<String> signIn(UserRequestLogin user) {
        User userInDb = userRepository.findUserByEmail(user.getEmail());


        if(userInDb!=null&& (matchPasswords(user.getPassword(),userInDb.getPassword()))){

            return Arrays.asList("GIT",generator.generateToken(user));
        }
        return Arrays.asList("BLAD","Podaj prawidłowy email lub hasło");
    }
    private boolean matchPasswords(String password, String password2){

            return passwordEncoder.matches(password, password2);
    }
}
