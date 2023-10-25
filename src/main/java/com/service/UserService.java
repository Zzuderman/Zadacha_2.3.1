package com.service;

import com.model.User;

import java.util.List;


public interface UserService {
    public List<User> getAllUsers();
    public void saveUser(User user);
    public User getUserId(int id);
    public void deleteUser(int id);
}
