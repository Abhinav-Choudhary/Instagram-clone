package edu.northeastern.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.FollowDAO;
import edu.northeastern.dao.LikeDAO;
import edu.northeastern.dao.PostDAO;
import edu.northeastern.dao.UserDAO;
import edu.northeastern.pojo.Follow;
import edu.northeastern.pojo.User;
import edu.northeastern.pojo.UserPost;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    LikeDAO likeDAO;

    @Autowired
    FollowDAO followDAO;
    
    @GetMapping("/home")
    public ModelAndView handleHome(HttpServletRequest request, HttpSession session) {
        User currentUser = (User)session.getAttribute("currentUser");
        List<Follow> listFollow = followDAO.getFollowers(currentUser.getId());
        List<UserPost> followingPosts = postDAO.getFormattedPostFromFollowUsers(listFollow);
        List<User> publicUsers = userDAO.getPublicUsers();
        publicUsers = userDAO.removeUsersWhichUserIsFollowing(publicUsers, listFollow);
        List<UserPost> userPosts = postDAO.getAllUserPostMapping(publicUsers);
        
        request.setAttribute("postDeleteRedirectPath", "home");
        request.setAttribute("followingPosts", followingPosts);
        return new ModelAndView("home", "userPosts", userPosts);
    }
    
}
