package edu.northeastern.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.PostDAO;
import edu.northeastern.dao.UserDAO;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class AdminController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @GetMapping("/admin")
    public ModelAndView handleAdmin(HttpServletRequest request) {
        List<User> allUsers = userDAO.getAllUsers();
        List<Post> allPosts = postDAO.getAllPosts();
        request.setAttribute("adminAllUsers", allUsers);
        request.setAttribute("postDeleteRedirectPath", "admin");
        return new ModelAndView("admin", "adminAllPosts", allPosts);
    }

    @GetMapping("/makeadmin/{userid}")
    public ModelAndView handleMakeAdmin(@PathVariable String userid, HttpSession session) {
        User user = userDAO.findById(Integer.parseInt(userid));
        userDAO.makeUserAdmin(user, session);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/makeuser/{userid}")
    public ModelAndView handleMakeUser(@PathVariable String userid, HttpSession session) {
        User user = userDAO.findById(Integer.parseInt(userid));
        userDAO.makeUserUser(user, session);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/deleteuser/{userid}")
    public ModelAndView handleDeleteUser(@PathVariable String userid, HttpSession session) {
        User user = userDAO.findById(Integer.parseInt(userid));
        userDAO.deleteUser(user);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/confirmdeleteuser/{userid}")
    public ModelAndView handleConfirmDeleteUser(@PathVariable String userid, HttpSession session) {
        return new ModelAndView("delete-user-form", "deleteUserId", userid);
    }

    @GetMapping("/deletepost/{postid}/{path}")
    public ModelAndView handleDeletePost(@PathVariable String postid, @PathVariable String path, HttpSession session) {
        Post post = postDAO.findByPostId(Integer.parseInt(postid));
        postDAO.deletePost(post);
        return new ModelAndView("redirect:/" + path);
    }
    
    @GetMapping("/confirmdeletepost/{postid}/{path}")
    public ModelAndView handleConfirmDeletePost(@PathVariable String postid, @PathVariable String path, HttpServletRequest request) {
        request.setAttribute("postDeleteRedirectPath", path);
        return new ModelAndView("delete-post-form", "deletePostId", postid);
    }
}
