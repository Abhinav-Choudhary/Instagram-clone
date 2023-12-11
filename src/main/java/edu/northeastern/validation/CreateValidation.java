package edu.northeastern.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.northeastern.model.CreateForm;

@Component
public class CreateValidation implements Validator {

    List<Class> supportedClasses;

    public CreateValidation() {
        supportedClasses = new ArrayList<>(List.of(CreateForm.class));
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
        CreateForm form = (CreateForm) target;

        if(form.getPostimage().isEmpty() || form.getDescription().isBlank()) {
            errors.reject("description", "Description or Post Image cannot be empty");
        }
    }
    
}
