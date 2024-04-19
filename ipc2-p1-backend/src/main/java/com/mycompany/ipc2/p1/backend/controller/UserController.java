/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.User;
import com.mycompany.ipc2.p1.backend.service.AdministratorService;
import com.mycompany.ipc2.p1.backend.utils.GsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
@WebServlet("/users/*")
public class UserController extends HttpServlet {

    private final GsonUtils<User> gsonUser;
    private final AdministratorService administratorService;

    public UserController() {
        gsonUser = new GsonUtils<>();
        administratorService = new AdministratorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO GET / USER");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {

            List<User> users = administratorService.getAllUsers();

            if (users.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            gsonUser.sendAsJson(response, users);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String[] splits = pathInfo.split("/");

            if ((splits.length - 1) == 1) {
                if (pathInfo.equals("/administrators") || pathInfo.equals("/operators") || pathInfo.equals("/receptionists")) {

                    int codeTypeUser = 0;
                    if (pathInfo.equals("/administrators")) {
                        codeTypeUser = 1;
                    } else if (pathInfo.equals("/operators")) {
                        codeTypeUser = 2;
                    } else if (pathInfo.equals("/receptionists")) {
                        codeTypeUser = 3;
                    }

                    List<User> users = administratorService.getUsersByCodeTypeUser(codeTypeUser);

                    if (users.isEmpty()) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonUser.sendAsJson(response, users);

                } else if (pathInfo.equals("/active") || pathInfo.equals("/deactive")) {

                    boolean active = true;

                    if (pathInfo.equals("/deactive")) {
                        active = false;
                    }

                    List<User> users = administratorService.getUsersByStatus(active);
                    if (users.isEmpty()) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonUser.sendAsJson(response, users);
                } else if (pathInfo.equals("/" + splits[1])) {
                    String idUser = splits[1];
                    try {
                        Integer.parseInt(idUser);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    User user = administratorService.getUserById(Integer.parseInt(idUser));
                    if (user == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;

                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonUser.sendAsJson(response, user);

                }
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO POST / USER");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        User userFromJson;
        try {
            userFromJson = gsonUser.readFromJson(request, User.class);
            if (userFromJson.getDpi().length() != 13) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        System.out.println(userFromJson);

        administratorService.createUser(userFromJson);
        gsonUser.sendAsJson(response, userFromJson);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO PUT / USER");
        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if ((splits.length - 1) == 1) {
            if (pathInfo.equals("/" + splits[1])) {
                User userToUpdate;

                try {
                    userToUpdate = gsonUser.readFromJson(request, User.class);
                } catch (Exception e) {
                    System.out.println(e);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                System.out.println(userToUpdate);

                administratorService.updateUser(userToUpdate);
                gsonUser.sendAsJson(response, userToUpdate);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }

    }
}
