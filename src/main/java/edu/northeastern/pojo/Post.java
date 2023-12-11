package edu.northeastern.pojo;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    private String filename;

    @Lob
    @Column(name="postimagedata", columnDefinition = "LONGBLOB")
    private byte[] postimagedata;

    @Transient
    private MultipartFile postimage;

    @Transient
    private String postbase64string;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getPostimagedata() {
        return postimagedata;
    }

    public void setPostimagedata(byte[] postimagedata) {
        this.postimagedata = postimagedata;
    }

    public String getPostbase64string() {
        return postbase64string;
    }

    public void setPostbase64string(String postbase64string) {
        this.postbase64string = postbase64string;
    }
    
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
