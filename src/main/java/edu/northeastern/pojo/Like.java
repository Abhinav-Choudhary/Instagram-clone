package edu.northeastern.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "postlikes")
@Table(name = "postlikes")
public class Like {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    @Column(name = "id")
    @Basic
    private int id;

    private int postid;
    private int userid;
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
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public Like() {
    }
}
