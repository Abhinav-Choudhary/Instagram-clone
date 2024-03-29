package edu.northeastern.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.northeastern.model.SearchForm;
import edu.northeastern.pojo.User;
import edu.northeastern.util.RoleEnum;

@Component
public class SearchValidation implements Validator {

    List<Class> supportedClasses;

    public SearchValidation() {
        supportedClasses = new ArrayList<>(List.of(SearchForm.class, User.class));
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
        SearchForm form = (SearchForm) target;

        if(form.getUsername().isBlank()) {
            errors.reject("username", "Username or Email cannot be empty. Please check your inputs.");
        }

        validateSpecialCharacters(errors, form.getUsername());
    }

    public void validateSearchResults(User currentUser, User searchedUser, Errors errors, SearchForm form) {
        if(form.getUsername().equals(currentUser.getUsername()) || form.getUsername().equals(currentUser.getEmail())) {
            errors.reject("username", "You cannot search yourself");
        }
        if(searchedUser == null) {
            errors.reject("username", "User not found");
        } else if(currentUser.getRole().equals(RoleEnum.USER) && searchedUser.getRole().equals(RoleEnum.ADMIN)) {
            errors.reject("username", "User not found");
        }
    }

    public void validateSpecialCharacters(Errors errors, String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9@_\\-_.\\s]+$");
        Matcher matcher = pattern.matcher(username);

        if(!matcher.matches()) {
            errors.rejectValue("username", "Username only permits Hyphen(-), Underscore(_), At(a), and Dot(.)");
        }
    }
    
}
