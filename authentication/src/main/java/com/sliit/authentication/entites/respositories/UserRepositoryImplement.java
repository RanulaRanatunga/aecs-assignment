package com.sliit.authentication.entites.respositories;

import com.sliit.authentication.data.DBManager;
import com.sliit.authentication.entites.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepositoryImplement implements UserRepository {

    DBManager dbManager;

    private String tblName = "User_Details";
    String partitionKey = "UserId";

    @Override
    public boolean createUser(User user) {
        try {
            dbManager.persistData(user, this.tblName, this.partitionKey, user.getUserId());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean authenticateUser(User user) {

        try {
            User userData = (User) dbManager.getDataItem(this.tblName, this.partitionKey, user.getUserId(), User.class);
            return userData.getPassword().equals(user.getPassword());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            List<Object> data = dbManager.getAll(this.tblName, User.class);
            return data.stream().filter(obj -> obj instanceof User).map(o -> (User) o).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
