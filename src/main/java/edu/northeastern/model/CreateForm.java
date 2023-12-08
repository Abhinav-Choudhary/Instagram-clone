package edu.northeastern.model;

import org.springframework.web.multipart.MultipartFile;

public class CreateForm {
    private String description;
    private String location;
    private MultipartFile postimage;
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public MultipartFile getPostimage() {
        return postimage;
    }
    public void setPostimage(MultipartFile postimage) {
        this.postimage = postimage;
    }
}
