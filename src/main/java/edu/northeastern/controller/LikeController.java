package edu.northeastern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.northeastern.dao.LikeDAO;
import edu.northeastern.pojo.Like;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LikeController {
    
    @Autowired
    LikeDAO likeDAO;

    @GetMapping("/like/{postid}/{userid}/{path}")
    public ModelAndView handleLike(@PathVariable String postid, @PathVariable String userid, @PathVariable String path, Like like) {
        if(userid.equals("GUESTUSER")) {
            return new ModelAndView("redirect:/" + path);
        }
        like.setPostid(Integer.parseInt(postid));
        like.setUserid(Integer.parseInt(userid));
        likeDAO.likePost(like);
        return new ModelAndView("redirect:/" + path);
    }
    
}
