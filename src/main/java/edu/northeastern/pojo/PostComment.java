package edu.northeastern.pojo;

import org.springframework.stereotype.Component;

@Component
public class PostComment {
    private int id;
    private int postid;
    private int commentorid;
    private String username;
    private String comment;
    private String commentedAt;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPostid() {
        return postid;
    }
    public void setPostid(int postid) {
        this.postid = postid;
    }
    public int getCommentorid() {
        return commentorid;
    }
    public void setCommentorid(int commentorid) {
        this.commentorid = commentorid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getCommentedAt() {
        return commentedAt;
    }
    public void setCommentedAt(String commentedAt) {
        this.commentedAt = commentedAt;
    }
}
