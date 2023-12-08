package edu.northeastern.pojo;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "post")
@Table(name = "post")
@Component
public class Post {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    @Column(name = "id")
    @Basic
    private int id;

    private String description;
    private String location;
    private String createdAt;
    private int userid;

    @Transient
    private MultipartFile postimage;
    
    public MultipartFile getPostimage() {
        return postimage;
    }
    public void setPostimage(MultipartFile postimage) {
        this.postimage = postimage;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    //add profile image
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public Post() {
    }
}
