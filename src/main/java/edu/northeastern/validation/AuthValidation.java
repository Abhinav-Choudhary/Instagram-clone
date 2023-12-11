package edu.northeastern.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.northeastern.model.LoginForm;
import edu.northeastern.model.RegisterForm;

@Component
public class AuthValidation implements Validator {

    List<Class> supportedClasses;

    public AuthValidation() {
        supportedClasses = new ArrayList<>(List.of(RegisterForm.class, LoginForm.class));
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
        RegisterForm form = (RegisterForm) target;

        String username = form.getUsername();
        String email = form.getEmail();
        String password = form.getPassword();

        if(username.isBlank() || email.isBlank() || password.isBlank()) {
            errors.reject("password", "Username, Email, and Password cannot be empty. Please check your inputs.");
        }
        if(username.equals(email)) {
            errors.reject("email", "Username and Email cannot be the same. Please check your inputs.");
        }
        if(!email.contains("@")) {
            errors.reject("email", "Please enter a valid email");
        }
        if(password.length() < 8) {
            errors.reject("password", "Password should be greater than 8 characters.");
        }

        validateSpecialCharacters(errors, form.getUsername());
    }

    public void validateLogin(LoginForm form, Errors errors) {
        if(form.getUsername().isBlank() || form.getPassword().isBlank()) {
            errors.rejectValue("username", "Username and Password cannot be empty.");
        }
        if(form.getPassword().length() < 8) {
            errors.reject("password", "Password should be greater than 8 characters.");
        }

        validateSpecialCharacters(errors, form.getUsername());
    }

    public void validateSpecialCharacters(Errors errors, String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\-_.\\s]+$");
        Matcher matcher = pattern.matcher(username);

        if(!matcher.matches()) {
            errors.reject("username", "Username only permits Hyphen(-), Underscore(_), and Dot(.)");
        }
    }
    
}
