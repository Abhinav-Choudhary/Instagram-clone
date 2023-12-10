package edu.northeastern.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import edu.northeastern.pojo.Follow;
import jakarta.persistence.NoResultException;
@Repository
public class FollowDAO {
    
    public void followUser(Follow follow) {
        Follow alreadyFollow = getFollowDataFromFollowerFollowing(follow.getFollowerid(), follow.getFollowingid());
        if(alreadyFollow == null) {
            Session session = DAO.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.persist(follow);
            tx.commit();
        }
    }

    public List<Follow> getFollowers(int followerid) {
        try {
            String hql = "FROM follow WHERE followerid = :followerid";
            Query<Follow> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("followerid", followerid);
            List<Follow> followers = query.getResultList();
            return followers;
        } catch(NoResultException e) {
            return null;
        }
    }

    public List<Follow> getFollowing(int followingid) {
        try {
            String hql = "FROM follow WHERE followingid = :followingid";
            Query<Follow> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("followingid", followingid);
            List<Follow> followers = query.getResultList();
            return followers;
        } catch(NoResultException e) {
            return null;
        }
        
    }

    public Follow getFollowDataFromFollowerFollowing(int followerid, int followingid) {
        try {
            String hql = "FROM follow WHERE followerid = :followerid AND followingid = :followingid";
            Query<Follow> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("followerid", followerid);
            query.setParameter("followingid", followingid);
            Follow follow = query.getSingleResult();
            return follow;
        } catch(NoResultException e) {
            return null;
        }
    }

    public boolean currentUserFollowingUser(int followerid, int followingid) {
        Follow checkFollow = getFollowDataFromFollowerFollowing(followerid, followingid);
        return  checkFollow != null;
    }

    public void unfollowUser(Follow follow) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.remove(follow);
        tx.commit();
    }

    public int getFollowersCount(int followerid) {
        return getFollowers(followerid).size();
    }

    public int getFollowingCount(int followingid) {
        return getFollowing(followingid).size();
    }
}
