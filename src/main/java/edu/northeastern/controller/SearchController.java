package edu.northeastern.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.FollowDAO;
import edu.northeastern.dao.PostDAO;
import edu.northeastern.dao.UserDAO;
import edu.northeastern.model.SearchForm;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class SearchController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    FollowDAO followDAO;
    
    @GetMapping("/search")
    public ModelAndView handleSearch(HttpSession session, HttpServletRequest request) {
        String userAlreadyFound = (String) session.getAttribute("UserAlreadyFound");
        if(userAlreadyFound != null && userAlreadyFound.equals("TRUE")) {
            User searchedUser = (User)session.getAttribute("searchedUser");
            User currentUser = (User)session.getAttribute("currentUser");
            List<Post> searchedUserPosts = postDAO.findByUserId(searchedUser.getId());
            int searchedUserPostCount = searchedUserPosts.size();
            int searchedUserFollowerCount = followDAO.getFollowingCount(searchedUser.getId());
            int searchedUserFollowingCount = followDAO.getFollowersCount(searchedUser.getId());
            request.setAttribute("searchedUserPostCount", searchedUserPostCount);
            request.setAttribute("searchedUserFollowerCount", searchedUserFollowerCount);
            request.setAttribute("searchedUserFollowingCount", searchedUserFollowingCount);
            session.setAttribute("currentUserFollowingUser", followDAO.currentUserFollowingUser(currentUser.getId(), searchedUser.getId()));
            session.removeAttribute("UserAlreadyFound");
            return new ModelAndView("search-user", "searchedUserPosts", searchedUserPosts);
        }
        return new ModelAndView("search-form");
    }
    
    @PostMapping("/search")
    public ModelAndView postMethodName(@ModelAttribute SearchForm form, User searchedUser, HttpServletRequest request, HttpSession session) {
        searchedUser = userDAO.findByUsernameOrEmail(form.getUsername());
        User currentUser = (User)session.getAttribute("currentUser");
        List<Post> searchedUserPosts = postDAO.findByUserId(searchedUser.getId());
        int searchedUserPostCount = searchedUserPosts.size();
        int searchedUserFollowerCount = followDAO.getFollowingCount(searchedUser.getId());
        int searchedUserFollowingCount = followDAO.getFollowersCount(searchedUser.getId());

        session.setAttribute("searchedUser", searchedUser);
        request.setAttribute("searchedUserPostCount", searchedUserPostCount);
        request.setAttribute("searchedUserFollowerCount", searchedUserFollowerCount);
        request.setAttribute("searchedUserFollowingCount", searchedUserFollowingCount);
        session.setAttribute("currentUserFollowingUser", followDAO.currentUserFollowingUser(currentUser.getId(), searchedUser.getId()));

        return new ModelAndView("search-user", "searchedUserPosts", searchedUserPosts);
    }
    
}
