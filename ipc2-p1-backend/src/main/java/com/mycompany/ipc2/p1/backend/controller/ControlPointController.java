/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Process;
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
@WebServlet("/control-points/*")
public class ControlPointController extends HttpServlet {

    private final GsonUtils<ControlPoint> gsonControlPoint;
    private final AdministratorService administratorService;

    public ControlPointController() {
        gsonControlPoint = new GsonUtils<>();
        administratorService = new AdministratorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO GET / CONTROL-POINT");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {

            List<ControlPoint> controlPoints = administratorService.getAllControlPoints();
            gsonControlPoint.sendAsJson(response, controlPoints);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String[] splits = pathInfo.split("/");

            if ((splits.length - 1) == 1) {
                if (pathInfo.equals("/" + splits[1])) {
                    String idControlPoint = splits[1];
                    try {
                        Integer.parseInt(idControlPoint);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    ControlPoint controlPoint = administratorService.getControlPointById(Integer.parseInt(idControlPoint));
                    if (controlPoint == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonControlPoint.sendAsJson(response, controlPoint);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO POST / CONTROL-POINT");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        ControlPoint controlPointFromJson;
        try {
            controlPointFromJson = gsonControlPoint.readFromJson(request, ControlPoint.class);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        System.out.println(controlPointFromJson);

        boolean canAdd = administratorService.canProceed(controlPointFromJson.getRouteId());

        if (canAdd) {
            int orderNo = administratorService.getOrderNoByRouteId(controlPointFromJson.getRouteId());
            controlPointFromJson.setOrderNo(orderNo + 1);
            administratorService.createControlPoint(controlPointFromJson);
        } else {
            System.out.println("NO SE PUEDE AGREGAR");
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO PUT / CONTROL-POINT");
        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if ((splits.length - 1) == 1) {
            if (pathInfo.equals("/" + splits[1])) {

                ControlPoint controlPointToUpdate;

                try {
                    controlPointToUpdate = gsonControlPoint.readFromJson(request, ControlPoint.class);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                System.out.println(controlPointToUpdate);
                List<Process> unprocessedPackages = administratorService.getUnprocessedPackages();

                boolean canModify = true;
                for (int i = 0; i < unprocessedPackages.size(); i++) {
                    if (unprocessedPackages.get(i).getControlPointId() == controlPointToUpdate.getId()) {
                        canModify = false;
                        break;
                    }
                }

                if (canModify) {
                    System.out.println("SE PUEDE MODIFICAR");
                    administratorService.updateControlPoint(controlPointToUpdate);
                    gsonControlPoint.sendAsJson(response, controlPointToUpdate);
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    System.out.println("NO SE PUEDE MODIFICAR");
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            }
        }

    }
}
