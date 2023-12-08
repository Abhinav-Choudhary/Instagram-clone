package edu.northeastern.test;

import edu.northeastern.dao.UserDAO;
import edu.northeastern.pojo.User;
import edu.northeastern.util.RoleEnum;

public class TestUser {
    public static void main(String[] args) {
        User newUser = new User();
        UserDAO userDAO = new UserDAO();
        newUser.setName("Abhinav");
        newUser.setUsername("AbhinavChoudhary");
        newUser.setEmail("Choudhary.ab@northeastern.edu");
        newUser.setBio("Bio");
        newUser.setPassword("123456");
        newUser.setRole(RoleEnum.USER);
        User savedUser = userDAO.registerUser(newUser);
        System.out.println(savedUser.getName());
    }
}
