package edu.northeastern.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import edu.northeastern.pojo.Like;
import edu.northeastern.pojo.User;
import jakarta.persistence.NoResultException;

@Repository
public class LikeDAO {
    
    public void likePost(Like like) {
        Like currentUserLiked = findExistingLike(like.getUserid(), like.getPostid());
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        if(currentUserLiked != null) {
            session.remove(currentUserLiked);
        } else {
            session.persist(like);
        }
        tx.commit();
    }

    public List<Like> getPostLikes(int postid) {
        try {
            String hql = "FROM postlikes WHERE postid = :postid";
            Query<Like> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("postid", postid);
            List<Like> likes = query.list();
            return likes;
        } catch(NoResultException e) {
            return null;
        }
    }

    public boolean hasCurrentUserLiked(int userid, List<Like> likes) {
        for(Like like: likes) {
            if(like.getUserid() == userid) return true;
        }
        return false;
    }

    public Like findExistingLike(int userid, int postid) {
        try {
            String hql = "FROM postlikes WHERE userid = :userid AND postid = :postid";
            Query<Like> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("postid", postid);
            query.setParameter("userid", userid);
            Like like = query.uniqueResult();
            return like;
        } catch(NoResultException e) {
            return null;
        }
    }

    public List<Like> getAllUserLikes(User user) {
        try {
            String hql = "FROM postlikes WHERE userid = :userid";
            Query<Like> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("userid", user.getId());
            List<Like> likes = query.getResultList();
            return likes;
        } catch(NoResultException e) {
            return null;
        }
    }
}
