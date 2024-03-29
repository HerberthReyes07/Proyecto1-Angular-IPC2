/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.service;

import com.mycompany.ipc2.p1.backend.data.UserDB;
import com.mycompany.ipc2.p1.backend.model.User;

/**
 *
 * @author herberthreyes
 */
public class UserService {

    private final UserDB userDB;

    public UserService() {
        this.userDB = new UserDB();
    }
    
    public User getUserByLoginCredentials(String username, String password) {
        return userDB.getUserByLoginCredentials(username, password).orElse(null);
    }

}
