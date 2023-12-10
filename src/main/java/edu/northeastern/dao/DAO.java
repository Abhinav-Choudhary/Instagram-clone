package edu.northeastern.dao;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import edu.northeastern.pojo.Comment;
import edu.northeastern.pojo.Follow;
import edu.northeastern.pojo.Like;
import edu.northeastern.pojo.Post;
import edu.northeastern.pojo.User;

public class DAO {
    private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/instagramdb");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "admin");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addPackage("edu.northeastern.pojo");
                configuration.addAnnotatedClass(User.class).addAnnotatedClass(Post.class)
                .addAnnotatedClass(Comment.class).addAnnotatedClass(Like.class).addAnnotatedClass(Follow.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
