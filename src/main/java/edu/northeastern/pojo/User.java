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

@Entity(name = "user")
@Table(name="user")
@Component
public class User {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    @Column(name="id")
    @Basic
    private int id;

    private String name;
    private String username;
    private String email;
    private String password;
    private String bio;
    private String role;
    private String visibility;

    private String filename;

    @Lob
    @Column(name="userimagedata", columnDefinition = "LONGBLOB")
    private byte[] userimagedata;
    
    @Transient
    private MultipartFile profilepicture;

    @Transient
    private String userbase64string;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getUserimagedata() {
        return userimagedata;
    }

    public void setUserimagedata(byte[] userimagedata) {
        this.userimagedata = userimagedata;
    }

    public String getUserbase64string() {
        return userbase64string;
    }

    public void setUserbase64string(String userbase64string) {
        this.userbase64string = userbase64string;
    }
    
    public MultipartFile getProfilepicture() {
        return profilepicture;
    }
    public void setProfilepicture(MultipartFile profilepicture) {
        this.profilepicture = profilepicture;
    }
    public String getVisibility() {
        return visibility;
    }
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public User() {
    }
}
