package edu.northeastern.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
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
import edu.northeastern.util.RoleEnum;
import edu.northeastern.util.VisibilityEnum;
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
    private ResourceLoader resourceLoader;
    
    @GetMapping("/profile")
    public ModelAndView handleProfile(HttpSession session, HttpServletRequest request) {
        User currentUser = (User) session.getAttribute("currentUser");
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
    public ModelAndView handleShowUser(@PathVariable String userid, HttpServletRequest request) {
        User user = userDAO.findById(Integer.parseInt(userid));
        List<Post> searchedUserPosts = postDAO.findByUserId(user.getId());
        request.setAttribute("searchedUserPosts", searchedUserPosts);
        return new ModelAndView("search-user", "searchedUser", user);
    }

    @PostMapping("/profile")
    public ModelAndView handleProfilePost(@ModelAttribute EditUserForm form, User updateUser, HttpSession session, Errors errors) {
       //add validations
        try {
            String name = form.getName();
            String password = form.getPassword();
            String bio = form.getBio();
            String visibility = form.getVisibility();
            User currentUser = (User) session.getAttribute("currentUser");
            if(bio.length() > 255) {
                bio = bio.substring(0, 255);
            }
            if(visibility.isBlank()) {
                visibility = VisibilityEnum.PUBLIC;
            }

            if(password.isBlank()) {
                errors.rejectValue("password", "Password cannot be empty. Please check your inputs.");
                return new ModelAndView("register-form", "editUserErrors", errors.getFieldErrors());
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

            userDAO.updateUser(updateUser, session);

            String fileName = currentUser.getUsername() + ".jpg";
            // Resource resource = resourceLoader.getResource("classpath:/static/users/");
            Resource resource = resourceLoader.getResource("/resources/static/users/");
            File staticFolder = resource.getFile();
            String absolutePath = staticFolder.getAbsolutePath();
            String postImageLocation = absolutePath + "\\" + fileName;
            File photo = new File(postImageLocation);
            updateUser.getProfilepicture().transferTo(photo);

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        
        return new ModelAndView("redirect:/profile");
    }   
}
