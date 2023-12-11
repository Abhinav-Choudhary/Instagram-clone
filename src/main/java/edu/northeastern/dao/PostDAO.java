package edu.northeastern.dao;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.northeastern.pojo.Follow;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;
import edu.northeastern.pojo.UserPost;
import jakarta.persistence.NoResultException;
// import jakarta.servlet.http.HttpSession;

@Repository
public class PostDAO {

    @Autowired
    UserDAO userDAO;
    
    public void savePost(Post newPost) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(newPost);
        tx.commit();
    }

    public void checkAndSetBase64String(Post post) {
        if(post.getPostbase64string() == null) {
            byte[] byteData = post.getPostimagedata();
            if(byteData != null) {
                post.setPostbase64string(Base64.getEncoder().encodeToString(post.getPostimagedata()));
            }
        }
    }

    public List<Post> getAllPosts() {
        try {
            String hql = "FROM post";
            Query<Post> query = DAO.getSessionFactory().openSession().createQuery(hql);
            List<Post> posts = query.getResultList();
            for(Post post: posts) {
                checkAndSetBase64String(post);
            }
            return posts;
        } catch(NoResultException e) {
            return null;
        }
        
    }

    public void updatePost(Post updatePost) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(updatePost);
        tx.commit();
    }

    public void deletePost(Post post) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.remove(post);
        tx.commit();
    }

    public List<Post> findByUserId(int userId) {
        try {
            String hql = "FROM post WHERE userid = :userid";
            Query<Post> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("userid", userId);
            List<Post> posts = query.getResultList();
            for(Post post: posts) {
                checkAndSetBase64String(post);
            }
            return posts;
        } catch(NoResultException e) {
            return null;
        }
    }

    public Post findByPostId(int postId) {
        try {
            String hql = "FROM post WHERE id = :postid";
            Query<Post> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameter("postid", postId);
            Post post = query.uniqueResult();
            checkAndSetBase64String(post);
            return post;
        } catch(NoResultException e) {
            return null;
        }
    }

    public List<UserPost> getAllUserPostMapping(List<User> listOfUsers) {
        List<Post> publicPosts = getPostsFromListUsers(listOfUsers);
        List<UserPost> userPosts = new ArrayList<>();
        Map<Integer, String> userUsernameMapping = new HashMap<>();

        for(User user: listOfUsers) {
            userUsernameMapping.put(user.getId(), user.getUsername());
        }

        for(Post post: publicPosts) {
            UserPost userPost = new UserPost();
            int postid = post.getId();
            int userid = post.getUserid();
            String userName = userUsernameMapping.get(userid);
            String fileName = userName + postid + ".jpg";

            userPost.setPostid(postid);
            userPost.setUserid(userid);
            userPost.setUsername(userName);
            userPost.setDescription(post.getDescription());
            userPost.setLocation(post.getLocation());
            userPost.setCreatedAt(post.getCreatedAt());
            userPost.setPostimagename(fileName);
            String postBase64String = post.getPostbase64string();
            if(postBase64String == null) {
                byte[] postByteData = post.getPostimagedata();
                if( postByteData != null) {
                    userPost.setBase64string(Base64.getEncoder().encodeToString(postByteData));
                }
            } else {
                userPost.setBase64string(postBase64String);
            }

            userPosts.add(userPost);
        }

        return userPosts;
    }

    public List<Post> getProfileUserPost(User currentUser) {
        List<User> currentUserList = new ArrayList<>();
        currentUserList.add(currentUser);
        List<Post> posts = getPostsFromListUsers(currentUserList);
        for(Post post: posts) {
            checkAndSetBase64String(post);
        }
        return posts;
    }

    public List<Post> getAllPublicPosts() {
        try {
            List<User> publicUsers = userDAO.getPublicUsers();
            List<Integer> publicUserIds = new ArrayList<>();
            for(User u: publicUsers) {
                publicUserIds.add(u.getId());
            }
            String hql = "FROM post WHERE userid IN (:userids)";
            Query<Post> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameterList("userids", publicUserIds);
            List<Post> posts = query.getResultList();
            for(Post post: posts) {
                checkAndSetBase64String(post);
            }
            return posts;
        } catch(NoResultException e) {
            return null;
        }
        
    }

    public List<Post> getPostsFromListUsers(List<User> Users) {
        try {
            List<Integer> UserIds = new ArrayList<>();
            for(User u: Users) {
                UserIds.add(u.getId());
            }
            String hql = "FROM post WHERE userid IN (:userids)";
            Query<Post> query = DAO.getSessionFactory().openSession().createQuery(hql);
            query.setParameterList("userids", UserIds);
            List<Post> posts = query.getResultList();
            for(Post post: posts) {
                checkAndSetBase64String(post);
            }
            return posts;
        } catch(NoResultException e) {
            return null;
        }
        
    }

    public List<UserPost> getFormattedPostFromFollowUsers(List<Follow> followUsers) {
        try {
            List<Integer> userIds = new ArrayList<>();
            for(Follow follow : followUsers) {
                userIds.add(follow.getFollowingid());
            }
            List<User> followingUsers = userDAO.getUsersFromListUserIds(userIds);
            return getAllUserPostMapping(followingUsers);
        } catch(NoResultException e) {
            return null;
        }
    }
}
