/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.service;

import com.mycompany.ipc2.p1.backend.data.UserDB;
import com.mycompany.ipc2.p1.backend.model.TypeUser;
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
    
    public User read(String username, String password) {
        return userDB.read(username, password, this).orElse(null);
    }
    
    public TypeUser searchTypeUser(int type) {
        switch (type) {
            case 1:
                return TypeUser.ADMINISTRATOR;
            case 2:
                return TypeUser.OPERATOR;
            case 3:
                return TypeUser.RECEPTIONIST;
            default:
                System.out.println("TIPO NO ENCONTRADO");
        }
        return null;
    }
}
