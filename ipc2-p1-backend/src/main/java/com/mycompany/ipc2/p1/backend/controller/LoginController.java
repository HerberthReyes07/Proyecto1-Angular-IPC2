/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.User;
import com.mycompany.ipc2.p1.backend.service.UserService;
import com.mycompany.ipc2.p1.backend.utils.GsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author herberthreyes
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final UserService userService;
    private final GsonUtils<User> gsonUser;

    public LoginController() {
        userService = new UserService();
        gsonUser = new GsonUtils<>();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        User user = gsonUser.readFromJson(req, User.class);
        
        System.out.println("Usuario enviado: " + user.toString());
        
        if (user.getUsername().equals("") || user.getPassword().equals("")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User userFromDB = userService.read(user.getUsername(), user.getPassword());

        if(userFromDB == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            gsonUser.sendAsJson(resp, userFromDB);
        }
    }
}
