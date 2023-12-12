package edu.northeastern.controller;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.UserDAO;
import edu.northeastern.model.LoginForm;
import edu.northeastern.model.RegisterForm;
import edu.northeastern.pojo.User;
import edu.northeastern.util.RoleEnum;
import edu.northeastern.util.VisibilityEnum;
import edu.northeastern.validation.AuthValidation;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthValidation validation;

    @GetMapping("/login")
    public String handleLogin() {
        return "login-form";
    }

    @PostMapping("/login")
    public ModelAndView handlePostLogin(@ModelAttribute LoginForm form, User checkUser, HttpSession session, BindingResult result, Errors errors, StandardPBEStringEncryptor standardPBEStringEncryptor) {
        
        validation.validateLogin(form, result);

        if(result.hasErrors()) {
            return new ModelAndView("login-form", "authenticationErrors", result.getAllErrors());
        }

        checkUser.setUsername(form.getUsername());
        checkUser.setPassword(form.getPassword());
        if(userDAO.authenticateUser(checkUser, session, standardPBEStringEncryptor)) {
            return new ModelAndView("redirect:/home");
        } else {
            errors.reject("password", "Username or Passwords don't match. Please check your inputs.");
            return new ModelAndView("login-form", "authenticationErrors", errors.getAllErrors());
        }
    }
    
    @GetMapping("/register")
    public ModelAndView handleRegister(User newUser) {
        return new ModelAndView("register-form");
    }

    @PostMapping("/register")
    public ModelAndView handlePostRegister(@ModelAttribute RegisterForm form, User newUser, HttpSession session, BindingResult result, Errors errors, StandardPBEStringEncryptor standardPBEStringEncryptor) {

        validation.validate(form, result);

        if(result.hasErrors()) {
            return new ModelAndView("register-form", "registerErrors", result.getAllErrors());
        }

        String encryptedPassword = standardPBEStringEncryptor.encrypt(form.getPassword());

        newUser.setName(form.getName());
        newUser.setEmail(form.getEmail());
        newUser.setUsername(form.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setBio(form.getBio());
        newUser.setRole(RoleEnum.USER);
        newUser.setVisibility(VisibilityEnum.PUBLIC);

        String registerResult = userDAO.authenticateAndRegister(newUser, session);
        if(registerResult != null) {
            errors.reject("username", registerResult);
            return new ModelAndView("register-form", "registerErrors", errors.getAllErrors());
        }
        
        return new ModelAndView("register");
    }

    @GetMapping("/logout")
    public ModelAndView handleLogout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/");
    }
    
}
