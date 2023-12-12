package edu.northeastern.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.northeastern.pojo.Comment;
import edu.northeastern.pojo.PostComment;
import edu.northeastern.pojo.User;
import jakarta.persistence.NoResultException;

@Repository
public class CommentDAO {

    @Autowired
    UserDAO userDAO;

    public void addComment(Comment newComment) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(newComment);
        tx.commit();
    }

    public List<Comment> getCommentsFromPostId(int postid) {
        try {
            String hql = "FROM comment WHERE postid = :postid";
            Query<Comment> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("postid", postid);
            List<Comment> comments = query.list();
            return comments;
        } catch(NoResultException e) {
            return null;
        }
    }

    public List<Comment> getAllUserComments(User user) {
        try {
            String hql = "FROM comment WHERE commentorid = :commentorid";
            Query<Comment> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("commentorid", user.getId());
            List<Comment> comments = query.list();
            return comments;
        } catch(NoResultException e) {
            return null;
        }
    }

    public List<PostComment> getFormattedPostComments(int postid) {
        List<User> allUsers = userDAO.getAllUsers();
        List<Comment> comments = getCommentsFromPostId(postid);
        List<PostComment> postComments = new ArrayList<>();
        Map<Integer, String> userUsernameMapping = new HashMap<>();

        for(User user: allUsers) {
            userUsernameMapping.put(user.getId(), user.getUsername());
        }

        for(Comment comment: comments) {
            PostComment postComment = new PostComment();
            postComment.setPostid(postid);
            postComment.setId(comment.getId());
            postComment.setCommentorid(comment.getCommentorid());
            postComment.setComment(comment.getComment());
            postComment.setCommentedAt(comment.getCommentedAt());
            postComment.setUsername(userUsernameMapping.get(comment.getCommentorid()));

            postComments.add(postComment);
        }

        return postComments;
        
    }
}
