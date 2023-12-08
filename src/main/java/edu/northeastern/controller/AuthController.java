package edu.northeastern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import jakarta.servlet.http.HttpSession;




@Controller
public class AuthController {
    @Autowired
    UserDAO userDAO;

    // @GetMapping("/login")
    // public ModelAndView handleLogin(User user) {
    //     List<User> users = userDAO.getAllUsers();
    //     return new ModelAndView("home", "usersList", users);
    // }
    @GetMapping("/login")
    public String handleLogin() {
        return "login-form";
    }

    @PostMapping("/login")
    public ModelAndView handlePostLogin(@ModelAttribute LoginForm form, User checkUser, HttpSession session, Errors errors) {
        //update HTML to make fields required
        if(form.getUsername().isBlank() || form.getPassword().isBlank()) {
            errors.rejectValue("username", "Username and Password cannot be empty.");
            return new ModelAndView("login-form", "authenticationErrors", errors.getFieldErrors());
        }
        checkUser.setUsername(form.getUsername());
        checkUser.setPassword(form.getPassword());
        if(userDAO.authenticateUser(checkUser, session)) {
            return new ModelAndView("redirect:/home");
        } else {
            errors.rejectValue("password", "Username or Passwords don't match. Please check your inputs.");
            return new ModelAndView("login-form", "authenticationErrors", errors.getFieldErrors());
        }
    }
    
    

    @GetMapping("/register")
    public ModelAndView handleRegister(User newUser) {
        return new ModelAndView("register-form");
    }

    @PostMapping("/register")
    public ModelAndView handlePostRegister(@ModelAttribute RegisterForm form, User newUser, HttpSession session, Errors errors) {
        //add validations
        String username = form.getUsername();
        String email = form.getEmail();
        String password = form.getPassword();

        if(username.isBlank() || email.isBlank() || password.isBlank()) {
            errors.rejectValue("password", "Username, Email, and Password cannot be empty. Please check your inputs.");
            return new ModelAndView("register-form", "registerErrors", errors.getFieldErrors());
        }
        if(username.equals(email)) {
            errors.rejectValue("email", "Username and Email cannot be the same. Please check your inputs.");
            return new ModelAndView("register-form", "registerErrors", errors.getFieldErrors());
        }
        
        newUser.setName(form.getName());
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setBio(form.getBio());
        newUser.setRole(RoleEnum.USER);
        newUser.setVisibility(VisibilityEnum.PUBLIC);

        String registerResult = userDAO.authenticateAndRegister(newUser, session);
        if(registerResult != null) {
            errors.rejectValue("username", registerResult);
            return new ModelAndView("register-form", "registerErrors", errors.getFieldErrors());
        }
        
        return new ModelAndView("register");
    }

    @GetMapping("/logout")
    public ModelAndView handleLogout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/");
    }
    
}
