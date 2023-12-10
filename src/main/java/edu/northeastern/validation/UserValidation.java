package edu.northeastern.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.model.EditUserForm;
import edu.northeastern.pojo.User;

@Component
public class UserValidation implements Validator {

    List<Class> supportedClasses;

    public UserValidation() {
        supportedClasses = new ArrayList<>(List.of(EditUserForm.class, User.class));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        boolean supports = false;

        for(Class c: supportedClasses) {
            supports = supports || c.isAssignableFrom(clazz);
        }

        return supports;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditUserForm form = (EditUserForm) target;

        if(form.getPassword().isBlank()) {
            errors.reject("password", "Password cannot be empty. Please check your inputs.");
        }

        if(form.getBio().length() > 255) {
            errors.reject("bio", "Bio cannot be more than 255 characters. Current Length: " + form.getBio().length());
        }
    }
    
}
