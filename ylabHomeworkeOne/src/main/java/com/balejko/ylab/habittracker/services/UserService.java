package com.balejko.ylab.habittracker.services;

import com.balejko.ylab.habittracker.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> users=new HashMap<>();

    public boolean registerUser(String email,String password,String name){
        if(users.containsKey(email)){
            return false;
        }
        users.put(email,new User(name,email,password));
        return true;
    }

    public boolean userExists(String email) {
        return users.containsKey(email);
    }

    public User login(String email,String password){
        User user=users.get(email);
        if(user!=null&&user.checkPassword(password)){
            return  user;
        }
        return null;
    }

    public void editProfile(User user, String newName, String newPassword, String newEmail){
        users.remove(user.getEmail());
        user.setEmail(newEmail);
        user.setName(newName);
        user.setPassword(newPassword);
        users.put(newEmail, user);
        System.out.println("Данные успешно обновлены");
    }

    public void deleteUser(User user){
        users.remove(user.getEmail());
        System.out.println("Профиль пользователья успешно удален");
    }




}

