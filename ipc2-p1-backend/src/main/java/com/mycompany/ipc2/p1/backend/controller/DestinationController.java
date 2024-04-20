/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.Destination;
import com.mycompany.ipc2.p1.backend.service.AdministratorService;
import com.mycompany.ipc2.p1.backend.service.ReceptionistService;
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
@WebServlet("/destinations/*")
public class DestinationController extends HttpServlet {

    private final GsonUtils<Destination> gsonDestination;
    private final ReceptionistService receptionistService;
    private final AdministratorService administratorService;

    public DestinationController() {
        gsonDestination = new GsonUtils<>();
        receptionistService = new ReceptionistService();
        administratorService = new AdministratorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO GET / DESTINATION");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            //List<Destination> validDestinations = receptionistService.getValidDestinations();
            List<Destination> destinations = receptionistService.getAllDestinations();
            response.setStatus(HttpServletResponse.SC_OK);
            gsonDestination.sendAsJson(response, destinations);
        } else {
            String[] splits = pathInfo.split("/");

            if ((splits.length - 1) == 1) {
                if (pathInfo.equals("/valid")) {
                    List<Destination> validDestinations = receptionistService.getValidDestinations();
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonDestination.sendAsJson(response, validDestinations);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO POST / DESTINATION");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        Destination destinationFromJson;

        try {
            destinationFromJson = gsonDestination.readFromJson(request, Destination.class);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        System.out.println(destinationFromJson);
        administratorService.createDestination(destinationFromJson);
        response.setStatus(HttpServletResponse.SC_OK);
        gsonDestination.sendAsJson(response, destinationFromJson);

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO PUT / DESTINATION");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if ((splits.length - 1) == 1) {
            if (pathInfo.equals("/" + splits[1])) {

                Destination destinationToUpdate;

                try {
                    destinationToUpdate = gsonDestination.readFromJson(request, Destination.class);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                administratorService.updateDestination(destinationToUpdate);
                gsonDestination.sendAsJson(response, destinationToUpdate);
                response.setStatus(HttpServletResponse.SC_OK);

            }
        }
    }

}
