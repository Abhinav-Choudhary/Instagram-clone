package edu.northeastern.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import edu.northeastern.pojo.Follow;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;
import edu.northeastern.util.RoleEnum;
import edu.northeastern.util.VisibilityEnum;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpSession;

@Repository
public class UserDAO {

    public boolean authenticateUser(User user, HttpSession session) {
        String username = user.getUsername();
        String password = user.getPassword();
        User queryUser = findByUsernameOrEmail(username);

        if(queryUser != null) {
            if(queryUser.getPassword().equals(password)) {
                session.setAttribute("currentUser", queryUser);
                return true;
            }
        }

        return false;
    }

    public List<User> getPublicUsers() {
        String hql = "FROM user WHERE visibility = :visibility";
        Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
        query.setParameter("visibility", VisibilityEnum.PUBLIC);
        List<User> users = query.getResultList();
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

    public void updateUser(User updateUser, HttpSession httpSession) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(updateUser);
        tx.commit();
        User updatedUser = findById(updateUser.getId());
        httpSession.setAttribute("currentUser", updatedUser);
    }

    public void deleteUser(User user) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.remove(user);
        tx.commit();
    }

    public void makeUserAdmin(User user, HttpSession session) {
        user.setRole(RoleEnum.ADMIN);
        updateUser(user, session);
    }

    public void makeUserUser(User user, HttpSession session) {
        user.setRole(RoleEnum.USER);
        updateUser(user, session);
    }

    public List<User> getAllUsers() {
        String hql = "FROM user";
        Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
        List<User> users = query.getResultList();
        return users;
    }

    public User findById(int id) {
        try {
            String hql = "FROM user WHERE id = :id";
            Query<User> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("id", id);
            User user = (User) query.getSingleResult();
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
            return query.getResultList();
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
}
