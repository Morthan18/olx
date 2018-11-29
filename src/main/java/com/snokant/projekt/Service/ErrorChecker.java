package com.snokant.projekt.Service;

import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorChecker {
    private UserRepository userRepository;

    public ErrorChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public ErrorChecker(){}

    public List<String> checkErrors(Object object, BindingResult result) throws NullPointerException {
        if(object instanceof User) {
            chceckIfEmailExists(object, result);
            return checkIfResultHasErrors(result);
        }else if(object instanceof Product){
            return checkIfResultHasErrors(result);
        }
        return null;
    }
    private List<String> checkIfResultHasErrors(BindingResult result) {
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            for (ObjectError object : result.getAllErrors()) {
                errorMessages.add(object.getDefaultMessage());
            }
            return errorMessages;
        }
        return null;
    }

    private void chceckIfEmailExists(Object object, BindingResult result) {
        if(object instanceof User) {
            User existing = userRepository.findUserByEmail(((User) object).getEmail());
            if (existing != null) {
                result.rejectValue("email", "BLAD", "Podany email jest już zajęty");
            }
        }
    }
}
