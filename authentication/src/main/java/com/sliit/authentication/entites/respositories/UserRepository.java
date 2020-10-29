package com.sliit.authentication.entites.respositories;

import com.sliit.authentication.entites.User;

import java.util.List;

public interface UserRepository {

    boolean createUser(User user);

    boolean authenticateUser(User user);

    List<User> getAllUsers();
}
