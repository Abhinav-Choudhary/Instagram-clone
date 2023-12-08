package edu.northeastern.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "comment")
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    @Column(name = "id")
    @Basic
    private int id;

    private String comment;
    private int commentorid;
    private int postid;
    private String commentedAt;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getCommentorid() {
        return commentorid;
    }
    public void setCommentorid(int commentorid) {
        this.commentorid = commentorid;
    }
    public int getPostid() {
        return postid;
    }
    public void setPostid(int postid) {
        this.postid = postid;
    }
    public String getCommentedAt() {
        return commentedAt;
    }
    public void setCommentedAt(String commentedAt) {
        this.commentedAt = commentedAt;
    }
    public Comment() {
    }
}
