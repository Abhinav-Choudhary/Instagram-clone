package edu.northeastern.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.CommentDAO;
import edu.northeastern.dao.LikeDAO;
import edu.northeastern.dao.PostDAO;
import edu.northeastern.dao.UserDAO;
import edu.northeastern.model.PostForm;
import edu.northeastern.pojo.Comment;
import edu.northeastern.pojo.Like;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.PostComment;
import edu.northeastern.pojo.User;
import edu.northeastern.pojo.UserPost;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    CommentDAO commentDAO;

    @Autowired
    LikeDAO likeDAO;
    
    @GetMapping("/post_{postid}")
    public ModelAndView handlePost(@PathVariable String postid, Post post, User user, UserPost detailedUserPost, HttpServletRequest request, HttpSession session) {
        post = postDAO.findByPostId(Integer.parseInt(postid));
        user = userDAO.findById(post.getUserid());

        detailedUserPost.setPostid(post.getId());
        detailedUserPost.setUserid(post.getUserid());
        detailedUserPost.setDescription(post.getDescription());
        detailedUserPost.setLocation(post.getLocation());
        detailedUserPost.setCreatedAt(post.getCreatedAt());
        detailedUserPost.setUsername(user.getUsername());
        detailedUserPost.setPostimagename(user.getUsername() + post.getId() + ".jpg");

        List<PostComment> comments = commentDAO.getFormattedPostComments(Integer.parseInt(postid));
        request.setAttribute("postComments", comments);

        List<Like> likes = likeDAO.getPostLikes(Integer.parseInt(postid));
        int likeCount = likes.size();
        User currentUser = (User)session.getAttribute("currentUser");
        boolean currentUserLiked = false;
        if(currentUser != null) {
            currentUserLiked = likeDAO.hasCurrentUserLiked(currentUser.getId(), likes);
        }

        request.setAttribute("likeCount", likeCount);
        request.setAttribute("currentUserLiked", currentUserLiked);
        request.setAttribute("postDeleteRedirectPath", "home");

        return new ModelAndView("post", "detailedUserPost", detailedUserPost);
    }

    @PostMapping("/post")
    public ModelAndView handlePostPost(@ModelAttribute PostForm form, Comment comment, HttpSession session) {
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mma");
        String formattedDate = currentDate.format(formatter);

        User currentUser = (User) session.getAttribute("currentUser");

        comment.setComment(form.getComment());
        comment.setPostid(Integer.parseInt(form.getPostid()));
        comment.setCommentorid(currentUser.getId());
        comment.setCommentedAt(formattedDate);

        commentDAO.addComment(comment);

        return new ModelAndView("redirect:/post_" + form.getPostid());
    }
    
    
}
