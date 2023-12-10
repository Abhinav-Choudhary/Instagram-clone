package edu.northeastern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.northeastern.dao.FollowDAO;
import edu.northeastern.dao.UserDAO;
import edu.northeastern.pojo.Follow;
import edu.northeastern.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class FollowController {
    
    @Autowired
    FollowDAO followDAO;

    @Autowired
    UserDAO userDAO;

    @GetMapping("/follow/{followingid}")
    public ModelAndView handleFollow(@PathVariable String followingid, Follow follow, HttpSession session, HttpServletRequest request) {
        User followingUser = userDAO.findById(Integer.parseInt(followingid));
        User followerUser = (User)session.getAttribute("currentUser");
        follow.setFollowerid(followerUser.getId());
        follow.setFollowingid(followingUser.getId());
        followDAO.followUser(follow);
        session.setAttribute("currentUserFollowingUser", true);
        session.setAttribute("UserAlreadyFound", "TRUE");
        return new ModelAndView("redirect:/search", "searchedUser", followingUser);
    }

    @GetMapping("/unfollow/{followingid}")
    public ModelAndView handleUnfollow(@PathVariable String followingid, HttpSession session, HttpServletRequest request) {
        User followingUser = userDAO.findById(Integer.parseInt(followingid));
        User followerUser = (User)session.getAttribute("currentUser");
        Follow follow = followDAO.getFollowDataFromFollowerFollowing(followerUser.getId(), followingUser.getId());
        followDAO.unfollowUser(follow);
        session.setAttribute("currentUserFollowingUser", false);
        session.setAttribute("UserAlreadyFound", "TRUE");
        session.setAttribute("searchedUser", followingUser);
        return new ModelAndView("redirect:/search");
    }
    
    
}
