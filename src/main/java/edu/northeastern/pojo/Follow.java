package edu.northeastern.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity (name = "follow")
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name="id")
    @Basic
    private int id;

    private int followerid;
    private int followingid;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getFollowerid() {
        return followerid;
    }
    public void setFollowerid(int followerid) {
        this.followerid = followerid;
    }
    public int getFollowingid() {
        return followingid;
    }
    public void setFollowingid(int followingid) {
        this.followingid = followingid;
    }
}
