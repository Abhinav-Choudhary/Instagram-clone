package edu.northeastern.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.northeastern.dao.PostDAO;
import edu.northeastern.model.CreateForm;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;
import jakarta.servlet.http.HttpSession;


@Controller
public class CreateController {

    @Autowired
    PostDAO postDAO;

    @Autowired
    private ResourceLoader resourceLoader;
    
    @GetMapping("/create")
    public String handleCreate() {
        return "create-form";
    }

    @PostMapping("/create")
    public ModelAndView handleCreatePost(@ModelAttribute CreateForm form, Post newPost, HttpSession session, Errors errors) {

        try {
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mma");
            String formattedDate = currentDate.format(formatter);
            User currentUser = (User) session.getAttribute("currentUser");

            newPost.setDescription(form.getDescription());
            newPost.setLocation(form.getLocation());
            newPost.setCreatedAt(formattedDate);
            newPost.setUserid(currentUser.getId());
            newPost.setPostimage(form.getPostimage());
            postDAO.savePost(newPost);

            String fileName = currentUser.getUsername() + newPost.getId() + ".jpg";
            Resource resource = resourceLoader.getResource("/src/main/resources/static/posts/" + fileName);
            String postImageLocation = resource.getFile().getAbsolutePath();
            if(postImageLocation.contains("\\src\\main\\webapp")) {
                postImageLocation = postImageLocation.replace("\\src\\main\\webapp", "");
            }
            File photo = new File(postImageLocation);
            newPost.getPostimage().transferTo(photo);

            session.setAttribute("newPost", newPost);
            return new ModelAndView("create-success", "postImageName", fileName);
        } catch(IOException e) {
            Logger.getLogger(CreateController.class.getName()).log(Level.SEVERE, null, e);
        }
        return new ModelAndView("create-error");
    }
    
    
}
