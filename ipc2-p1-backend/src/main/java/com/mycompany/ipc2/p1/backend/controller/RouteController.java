/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.Route;
import com.mycompany.ipc2.p1.backend.model.RoutesReport;
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
@WebServlet("/routes/*")
public class RouteController extends HttpServlet {

    private final GsonUtils<Route> gsonRoute;
    private final GsonUtils<RoutesReport> gsonRoutesReport;
    private final AdministratorService administratorService;

    public RouteController() {
        gsonRoute = new GsonUtils<>();
        gsonRoutesReport = new GsonUtils<>();
        administratorService = new AdministratorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO GET / ROUTE");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {

            List<Route> routes = administratorService.getAllRoutes();
            gsonRoute.sendAsJson(response, routes);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String[] splits = pathInfo.split("/");

            if ((splits.length - 1) == 1) {
                if (pathInfo.equals("/report")) {
                    List<RoutesReport> routesReports = administratorService.getRoutesReport();
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonRoutesReport.sendAsJson(response, routesReports);
                } else if (pathInfo.equals("/" + splits[1])) {
                    String idRoute = splits[1];
                    try {
                        Integer.parseInt(idRoute);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    Route route = administratorService.getRouteById(Integer.parseInt(idRoute));
                    if (route == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonRoute.sendAsJson(response, route);
                }
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO POST / ROUTE");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        Route routeFromJson;

        try {
            routeFromJson = gsonRoute.readFromJson(request, Route.class);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        System.out.println(routeFromJson);
        administratorService.createRoute(routeFromJson);
        gsonRoute.sendAsJson(response, routeFromJson);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO PUT / ROUTE");
        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if ((splits.length - 1) == 1) {
            if (pathInfo.equals("/" + splits[1])) {

                String idRoute = splits[1];
                try {
                    Integer.parseInt(idRoute);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Route routeToUpdate = administratorService.getRouteById(Integer.parseInt(idRoute));
                System.out.println(routeToUpdate);

                if (routeToUpdate == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                //Route routeToUpdate = gsonRoute.readFromJson(request, Route.class);

                boolean canDeactivate = administratorService.canProceed(routeToUpdate.getId());

                if (canDeactivate) {
                    System.out.println("SE PUEDE DESACTIVAR");
                    routeToUpdate.setActive(false);
                    administratorService.updateRoute(routeToUpdate);
                    //response.setStatus(HttpServletResponse.SC_OK);
                    gsonRoute.sendAsJson(response, routeToUpdate);
                } else {
                    System.out.println("NO SE PUEDE DESACTIVAR");
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    return;
                }
                response.setStatus(HttpServletResponse.SC_OK);

            }
        }

    }
}
