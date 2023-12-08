package edu.northeastern.pojo;

import org.springframework.stereotype.Component;

@Component
public class UserPost {
    private int postid;
    private int userid;
    private String description;
    private String location;
    private String createdAt;
    private String username;
    private String postimagename;

    public int getPostid() {
        return postid;
    }
    public void setPostid(int postid) {
        this.postid = postid;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
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
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPostimagename() {
        return postimagename;
    }
    public void setPostimagename(String postimagename) {
        this.postimagename = postimagename;
    }
}
