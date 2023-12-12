package edu.northeastern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.UserDAO;
import edu.northeastern.pojo.User;


@Controller
public class ErrorController {
    
    @GetMapping("/error")
    public ModelAndView handleError(UserDAO userDAO) {
        User adminUser = userDAO.getAdminUser();
        String adminEmail;
        if(adminUser == null) {
            adminEmail = "ADMIN@Northeastern.edu";
        } else {
            adminEmail = adminUser.getEmail();
        }
        return new ModelAndView("error", "adminEmail", adminEmail);
    }
    
}
