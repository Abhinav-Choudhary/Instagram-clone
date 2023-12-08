package edu.northeastern.model;

import org.springframework.web.multipart.MultipartFile;

public class EditUserForm {
    private String name;
    private String username;
    private String email;
    private String password;
    private String bio;
    private String visibility;
    private MultipartFile profilepicture;

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
    public String getVisibility() {
        return visibility;
    }
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    public MultipartFile getProfilepicture() {
        return profilepicture;
    }
    public void setProfilepicture(MultipartFile profilepicture) {
        this.profilepicture = profilepicture;
    }
}
