package edu.northeastern.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            String formattedDate = currentDate.format(formatter);
            User currentUser = (User) session.getAttribute("currentUser");

            newPost.setDescription(form.getDescription());
            newPost.setLocation(form.getLocation());
            newPost.setCreatedAt(formattedDate);
            newPost.setUserid(currentUser.getId());
            newPost.setPostimage(form.getPostimage());
            postDAO.savePost(newPost);

            String fileName = currentUser.getUsername() + newPost.getId() + ".jpg";
            Resource resource = resourceLoader.getResource("classpath:/static/posts/");
            File staticFolder = resource.getFile();
            String absolutePath = staticFolder.getAbsolutePath();
            String postImageLocation = absolutePath + "\\" + fileName;
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
