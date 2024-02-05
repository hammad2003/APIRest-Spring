package com.example.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public List<User> readAllusers(){
        return userDAO.findAll();
    }

    public User getUserById(Integer id) {
        return userDAO.findById(id).orElse(null);
    }

    public User addUser(User user) {
        return userDAO.save(user);
    }

    public void deleteUserById(Integer id) {
        userDAO.deleteById(id);
    }

    public User updateUser(User user) {
        return userDAO.save(user);
    }


    public User modifyUser(User userPatched) {
        return userDAO.save(userPatched);
    }
}
