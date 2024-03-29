/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Customer;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.model.Destination;
import com.mycompany.ipc2.p1.backend.model.PackageStatus;
import com.mycompany.ipc2.p1.backend.model.Parameter;
import com.mycompany.ipc2.p1.backend.model.Route;
import com.mycompany.ipc2.p1.backend.service.ReceptionistService;
import com.mycompany.ipc2.p1.backend.utils.GsonUtils;
import com.mycompany.ipc2.p1.backend.utils.PackageMobilization;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
@WebServlet("/recep/*")
public class ReceptionistController extends HttpServlet {

    private final GsonUtils<Destination> gsonDestination;
    private final GsonUtils<Package> gsonPackage;
    private final GsonUtils<List<Package>> gsonPackageS;
    private final GsonUtils<Customer> gsonCustomer;
    private final GsonUtils<Parameter> gsonParameter;
    private final ReceptionistService receptionistService;
    private final PackageMobilization packageMobilization;

    public ReceptionistController() {
        gsonDestination = new GsonUtils<>();
        gsonPackage = new GsonUtils<>();
        gsonPackageS = new GsonUtils<>();
        gsonCustomer = new GsonUtils<>();
        gsonParameter = new GsonUtils<>();
        receptionistService = new ReceptionistService();
        packageMobilization = new PackageMobilization();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO GET /RECEP");
        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String action = splits[1];
        System.out.println("ACCION: " + action);

        if (action.equals("readAllDestinations")) {
            //List<Destination> destinations = receptionistService.readAllDestinations();
            //List<Route> routes = receptionistService.getAllRoutes();
            List<Destination> validDestinations = receptionistService.getValidDestinations();

            response.setStatus(HttpServletResponse.SC_OK);
            gsonDestination.sendAsJson(response, validDestinations);
        } else if (action.equals("getCustomerByNit")) {

            String customerNit;
            try {
                customerNit = splits[2];
                Integer.parseInt(customerNit);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Customer customerFromDB = receptionistService.getCustomerByNit(customerNit);
            if (customerFromDB == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                gsonCustomer.sendAsJson(response, customerFromDB);
            }
        } else if (action.equals("getParameters")) {
            Parameter parameterFromDB = receptionistService.getParameters();
            response.setStatus(HttpServletResponse.SC_OK);
            gsonParameter.sendAsJson(response, parameterFromDB);
        } else if (action.equals("getInvoiceNo")) {
            int invoice = receptionistService.getLastInvoiceNo() + 1;
            response.setStatus(HttpServletResponse.SC_OK);
            gsonPackage.sendAsJson(response, new Package(invoice));
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO POST /RECEP");

        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");
        String action = splits[1];
        System.out.println("ACCION: " + action);

        if (action.equals("createPackage")) {
            
            Package packageFromJson;
            try {
                packageFromJson = gsonPackage.readFromJson(request, Package.class);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            System.out.println("Paquete enviado: " + packageFromJson.toString());

            List<Route> mobilizationRoutes = packageMobilization.getRoutesByDestinationId(packageFromJson.getDestinationId());
            System.out.println("MR: " + mobilizationRoutes.toString());//Ruta 1-2

            ControlPoint controlPoint = packageMobilization.getFirstControlPoint(mobilizationRoutes);

            if (controlPoint == null) {
                System.out.println("EN BODEGA");
                packageFromJson.setStatus(PackageStatus.EN_BODEGA);
                receptionistService.createPackage(packageFromJson);
            } else {

                System.out.println("CP: " + controlPoint.toString());
                packageFromJson.setStatus(PackageStatus.EN_PUNTO_CONTROL);
                receptionistService.createPackage(packageFromJson);

                int originalQueueCapacity = controlPoint.getQueueCapacity();
                controlPoint.setQueueCapacity(originalQueueCapacity - 1);
                receptionistService.updateControlPoint(controlPoint);

                receptionistService.createPackageControlPoint(receptionistService.getPackageCreatedId(), controlPoint.getId());
            }
            response.setStatus(HttpServletResponse.SC_OK);
            gsonPackage.sendAsJson(response, packageFromJson);

        } else if (action.equals("createCustomer")) {

            Customer customerFromJson = gsonCustomer.readFromJson(request, Customer.class);
            System.out.println("Cliente enviado: " + customerFromJson.toString());

            receptionistService.createCustomer(customerFromJson);
            gsonCustomer.sendAsJson(response, customerFromJson);
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

}
