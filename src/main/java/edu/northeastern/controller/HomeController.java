package edu.northeastern.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.LikeDAO;
import edu.northeastern.dao.PostDAO;
import edu.northeastern.dao.UserDAO;
import edu.northeastern.pojo.User;
import edu.northeastern.pojo.UserPost;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class HomeController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    LikeDAO likeDAO;
    
    @GetMapping("/home")
    public ModelAndView handleHome(HttpServletRequest request) {
        List<User> publicUsers = userDAO.getPublicUsers();
        List<UserPost> userPosts = postDAO.getAllUserPostMapping(publicUsers);
        request.setAttribute("postDeleteRedirectPath", "home");
        return new ModelAndView("home", "userPosts", userPosts);
    }
    
}
