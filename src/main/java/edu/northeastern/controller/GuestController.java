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
import jakarta.servlet.http.HttpSession;

@Controller
public class GuestController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    LikeDAO likeDAO;

    @GetMapping("/")
    public ModelAndView handleGuest(HttpSession session) {
        List<User> publicUsers = userDAO.getPublicUsers();
        List<UserPost> userPosts = postDAO.getAllUserPostMapping(publicUsers);

        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser != null) return new ModelAndView("redirect:/home");
        
        return new ModelAndView("guest", "userPosts", userPosts);
    }
}
