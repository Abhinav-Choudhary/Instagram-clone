package edu.northeastern.controller;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.LikeDAO;
import edu.northeastern.dao.PostDAO;
import edu.northeastern.dao.UserDAO;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;
import edu.northeastern.pojo.UserPost;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class GuestController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    LikeDAO likeDAO;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/")
    public ModelAndView handleGuestWelcome(HttpSession session) {
        List<User> publicUsers = userDAO.getPublicUsers();
        List<UserPost> userPosts = postDAO.getAllUserPostMapping(publicUsers);

        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser != null) return new ModelAndView("redirect:/home");
        
        try {
            Resource resource = resourceLoader.getResource("classpath:/resources/");
            File staticFolder = resource.getFile();
            String absolutePath = staticFolder.getAbsolutePath();
            System.out.println(absolutePath);
        } catch(IOException e) {
            Logger.getLogger(GuestController.class.getName()).log(Level.SEVERE, null, e);
        }

        return new ModelAndView("guest", "userPosts", userPosts);
    }

    @GetMapping("login-form")
    public String handleLoginForm() {
        return "login-form";
    }

    @GetMapping("register-form")
    public String handleRegisterForm() {
        return "register-form";
    }
}
