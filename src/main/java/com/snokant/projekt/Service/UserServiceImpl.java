package com.snokant.projekt.Service;

import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<String> addNewUser(User user, BindingResult result) throws NullPointerException{
        ErrorChecker errorChecker = new ErrorChecker(userRepository);
        List<String> errors = errorChecker.checkErrors(user, result);
        if (errors == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            authenticateUser(user);
            return Arrays.asList("Zarejestrowano");
        }
        return errors;
    }
//    private List<String> checkErrors(User user, BindingResult result) {
//        chceckIfEmailExists(user, result);
//        return checkIfResultHasErrors(result);
//    }
//    private List<String> checkIfResultHasErrors(BindingResult result) {
//        List<String> errorMessages = new ArrayList<>();
//        if (result.hasErrors()) {
//            for (ObjectError object : result.getAllErrors()) {
//                errorMessages.add(object.getDefaultMessage());
//            }
//            return errorMessages;
//        }
//        return null;
//    }
//
//    private void chceckIfEmailExists(User user, BindingResult result) {
//        User existing = userRepository.findUserByEmail(user.getEmail());
//        if (existing != null) {
//            result.rejectValue("email", null, "Podany email jest już zajęty");
//        }
//    }
    private void authenticateUser(final User user) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_User"));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user.getFirst_name(),
                        user.getPassword(),
                        grantedAuthorities));
    }
}
