package edu.northeastern.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.PostDAO;
import edu.northeastern.dao.UserDAO;
import edu.northeastern.model.SearchForm;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class SearchController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;
    
    @GetMapping("/search")
    public String handleSearch() {
        return "search-form";
    }
    
    @PostMapping("/search")
    public ModelAndView postMethodName(@ModelAttribute SearchForm form, User searchedUser, HttpServletRequest request) {
        searchedUser = userDAO.findByUsernameOrEmail(form.getUsername());
        List<Post> searchedUserPosts = postDAO.findByUserId(searchedUser.getId());
        request.setAttribute("searchedUserPosts", searchedUserPosts);
        return new ModelAndView("search-user", "searchedUser", searchedUser);
    }
    
}
