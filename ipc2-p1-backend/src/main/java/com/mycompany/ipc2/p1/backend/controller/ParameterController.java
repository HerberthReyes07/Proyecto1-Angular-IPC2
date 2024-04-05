/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.Parameter;
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
@WebServlet("/parameters/*")
public class ParameterController extends HttpServlet {

    private final GsonUtils<Parameter> gsonParameter;
    private final AdministratorService administratorService;

    public ParameterController() {
        gsonParameter = new GsonUtils<>();
        administratorService = new AdministratorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO GET / USER");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {

            List<Parameter> parameters = administratorService.getAllParameters();

            if (parameters.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            gsonParameter.sendAsJson(response, parameters);
            
        } else {
            String[] splits = pathInfo.split("/");

            if ((splits.length - 1) == 1) {
                if (pathInfo.equals("/current")) {
                    Parameter currentParameter = administratorService.getCurrentParameter();
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonParameter.sendAsJson(response, currentParameter);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO POST / PARAMETER");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        Parameter parameterFromJson;
        try {
            parameterFromJson = gsonParameter.readFromJson(request, Parameter.class);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        System.out.println(parameterFromJson);

        Parameter currentParameter = administratorService.getCurrentParameter();

        administratorService.updateLocalOperationFee(currentParameter.getGlobalOperationFee(), parameterFromJson.getGlobalOperationFee());

        administratorService.createParameter(parameterFromJson);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
