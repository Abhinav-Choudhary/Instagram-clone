package edu.northeastern.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.FollowDAO;
import edu.northeastern.dao.PostDAO;
import edu.northeastern.dao.UserDAO;
import edu.northeastern.model.EditUserForm;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;
import edu.northeastern.util.VisibilityEnum;
import edu.northeastern.validation.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    FollowDAO followDAO;

    @Autowired
    UserValidation userValidation;
    
    @GetMapping("/profile")
    public ModelAndView handleProfile(HttpSession session, HttpServletRequest request) {
        User currentUser = (User) session.getAttribute("currentUser");
        userDAO.checkAndSetBase64String(currentUser);
        List<Post> profilePosts = postDAO.getProfileUserPost(currentUser);
        request.setAttribute("postDeleteRedirectPath", "home");

        int UserPostCount = profilePosts.size();
        int UserFollowerCount = followDAO.getFollowingCount(currentUser.getId());
        int UserFollowingCount = followDAO.getFollowersCount(currentUser.getId());
        request.setAttribute("UserPostCount", UserPostCount);
        request.setAttribute("UserFollowerCount", UserFollowerCount);
        request.setAttribute("UserFollowingCount", UserFollowingCount);

        return new ModelAndView("profile", "profilePosts", profilePosts);
    }

    @GetMapping("/editProfile")
    public String handleEditProfile() {
        return "edit-form";
    }

    @GetMapping("/user_{userid}")
    public ModelAndView handleShowUser(@PathVariable String userid, HttpServletRequest request, HttpSession session) {
        User user = userDAO.findById(Integer.parseInt(userid));
        userDAO.checkAndSetBase64String(user);
        List<Post> searchedUserPosts = postDAO.findByUserId(user.getId());
        int searchedUserPostCount = searchedUserPosts.size();
        int searchedUserFollowerCount = followDAO.getFollowingCount(user.getId());
        int searchedUserFollowingCount = followDAO.getFollowersCount(user.getId());
        User currentUser = (User)session.getAttribute("currentUser");

        session.setAttribute("searchedUser", user);
        request.setAttribute("searchedUserPostCount", searchedUserPostCount);
        request.setAttribute("searchedUserFollowerCount", searchedUserFollowerCount);
        request.setAttribute("searchedUserFollowingCount", searchedUserFollowingCount);
        session.setAttribute("currentUserFollowingUser", followDAO.currentUserFollowingUser(currentUser.getId(), user.getId()));
        return new ModelAndView("search-user", "searchedUserPosts", searchedUserPosts);
    }

    @PostMapping("/profile")
    public ModelAndView handleProfilePost(@ModelAttribute EditUserForm form, User updateUser, HttpSession session, BindingResult result) {
        try {

            userValidation.validate(form, result);
            if(result.hasErrors()) {
                return new ModelAndView("edit-form", "editUserErrors", result.getAllErrors());
            }

            String name = form.getName();
            String password = form.getPassword();
            String bio = form.getBio();
            String visibility = form.getVisibility();
            User currentUser = (User) session.getAttribute("currentUser");
            String fileName = currentUser.getUsername() + ".jpg";
            if(visibility.isBlank()) {
                visibility = VisibilityEnum.PUBLIC;
            }
            
            updateUser.setName(name);
            updateUser.setPassword(password);
            updateUser.setEmail(currentUser.getEmail());
            updateUser.setUsername(currentUser.getUsername());
            updateUser.setBio(bio);
            updateUser.setRole(currentUser.getRole());
            updateUser.setVisibility(visibility);
            updateUser.setId(currentUser.getId());
            updateUser.setProfilepicture(form.getProfilepicture());
            updateUser.setFilename(fileName);
            updateUser.setUserimagedata(form.getProfilepicture().getBytes());
            updateUser.setUserbase64string(Base64.getEncoder().encodeToString(form.getProfilepicture().getBytes()));

            userDAO.updateUser(updateUser, session, false);

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        
        return new ModelAndView("redirect:/profile");
    }
}
