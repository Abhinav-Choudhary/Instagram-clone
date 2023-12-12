package edu.northeastern.dao;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.northeastern.pojo.Comment;
import edu.northeastern.pojo.Follow;
import edu.northeastern.pojo.Like;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;
import edu.northeastern.util.RoleEnum;
import edu.northeastern.util.VisibilityEnum;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpSession;

@Repository
public class UserDAO {

    @Autowired
    LikeDAO likeDAO;

    @Autowired
    FollowDAO followDAO;

    public boolean authenticateUser(User user, HttpSession session, StandardPBEStringEncryptor standardPBEStringEncryptor) {
        String username = user.getUsername();
        String password = user.getPassword();
        User queryUser = findByUsernameOrEmail(username);

        if(queryUser != null) {
            String decryptedPasswordQueryUser = standardPBEStringEncryptor.decrypt(queryUser.getPassword());
            if(decryptedPasswordQueryUser.equals(password)) {
                checkAndSetBase64String(queryUser);
                session.setAttribute("currentUser", queryUser);
                return true;
            }
        }

        return false;
    }

    public void checkAndSetBase64String(User user) {
        if(user.getUserbase64string() == null) {
            byte[] byteData = user.getUserimagedata();
            if(byteData != null) {
                user.setUserbase64string(Base64.getEncoder().encodeToString(byteData));
            }
        }
    }

    public List<User> getPublicUsers() {
        String hql = "FROM user WHERE visibility = :visibility";
        Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
        query.setParameter("visibility", VisibilityEnum.PUBLIC);
        List<User> users = query.getResultList();
        for(User user: users) {
            checkAndSetBase64String(user);
        }
        return users;
    }

    public String authenticateAndRegister(User newUser, HttpSession session) {
        User emailUser = findByEmail(newUser.getEmail());
        if(emailUser != null) {
            return "Email is not unique. Please check your inputs.";
        }
        User usernameUser = findByUsername(newUser.getUsername());
        if(usernameUser != null) {
            return "Username is not unique. Please check your inputs";
        }
        User savedUser = registerUser(newUser);
        session.setAttribute("savedUser", savedUser);
        return null;
    }

    public User registerUser(User newUser) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(newUser);
        tx.commit();
        return newUser;
    }

    public void updateUser(User updateUser, HttpSession httpSession, boolean adminRequest) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(updateUser);
        tx.commit();
        User updatedUser = findById(updateUser.getId());
        checkAndSetBase64String(updateUser);
        if(!adminRequest)   httpSession.setAttribute("currentUser", updatedUser);
    }

    public void deleteUser(User user, List<Post> posts, List<Comment> comments) {
        List<Like> likes = likeDAO.getAllUserLikes(user);
        List<Follow> follows = followDAO.getAllUserFollowerFollowing(user);

        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        for(Post post: posts) session.remove(post);
        for(Comment comment: comments) session.remove(comment);
        for(Like like: likes) session.remove(like);
        for(Follow follow: follows) session.remove(follow);
        session.remove(user);

        tx.commit();
    }

    public void makeUserAdmin(User user, HttpSession session) {
        user.setRole(RoleEnum.ADMIN);
        updateUser(user, session, true);
    }

    public void makeUserUser(User user, HttpSession session) {
        user.setRole(RoleEnum.USER);
        updateUser(user, session, true);
    }

    public List<User> getAllUsers() {
        String hql = "FROM user";
        Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
        List<User> users = query.getResultList();
        for(User user: users) {
            checkAndSetBase64String(user);
        }
        return users;
    }

    public User findById(int id) {
        try {
            String hql = "FROM user WHERE id = :id";
            Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("id", id);
            User user = (User) query.getSingleResult();
            checkAndSetBase64String(user);
            return user;
        } catch(NoResultException e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            String hql = "FROM user WHERE email = :email";
            Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("email", email);
            User user = (User) query.getSingleResult();
            checkAndSetBase64String(user);
            return user;
        } catch(NoResultException e) {
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            String hql = "FROM user WHERE username = :username";
            Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("username", username);
            User user = (User) query.getSingleResult();
            checkAndSetBase64String(user);
            return user;
        } catch(NoResultException e) {
            return null;
        }
    }

    public User findByUsernameOrEmail(String username) {
        try {
            String hql = "FROM user WHERE username = :username OR email = :email";
            Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("username", username).setParameter("email", username);
            User user = (User) query.getSingleResult();
            checkAndSetBase64String(user);
            return user;
        } catch(NoResultException e) {
            return null;
        }
    }

    public List<User> getUsersFromListUserIds(List<Integer> userIds) {
        try {
            String hql = "FROM user WHERE id IN (:userids)";
            Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameterList("userids", userIds);
            List<User> users = query.getResultList();
            for(User user: users) {
                checkAndSetBase64String(user);
            }
            return users;
        } catch(NoResultException e) {
            return null;
        }
    }

    public List<User> removeUsersWhichUserIsFollowing(List<User> users, List<Follow> following) {
        Map<Integer, User> userMap = new HashMap<>();
        for(User user: users) {
            userMap.put(user.getId(), user);
        }
        for(Follow follow: following) {
            userMap.remove(follow.getFollowingid());
        }
        users.clear();
        users.addAll(userMap.values());
        return users;
    }

    public List<User> removeCurrentUserFromAllUsers(User currentUser, List<User> allUsers) {
        List<User> newUsers = new ArrayList<>();
        for(User user: allUsers) {
            if(user.getId() != currentUser.getId()) {
                newUsers.add(user);
            }
        }

        return newUsers;
    }
}
