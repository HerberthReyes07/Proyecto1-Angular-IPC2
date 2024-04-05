/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Customer;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.model.Destination;
import com.mycompany.ipc2.p1.backend.model.LocationReport;
import com.mycompany.ipc2.p1.backend.model.Process;
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
    private final GsonUtils<Customer> gsonCustomer;
    private final GsonUtils<Parameter> gsonParameter;
    private final GsonUtils<LocationReport> gsonLocation;
    private final ReceptionistService receptionistService;
    private final PackageMobilization packageMobilization;

    public ReceptionistController() {
        gsonDestination = new GsonUtils<>();
        gsonPackage = new GsonUtils<>();
        gsonCustomer = new GsonUtils<>();
        gsonParameter = new GsonUtils<>();
        gsonLocation = new GsonUtils<>();
        receptionistService = new ReceptionistService();
        packageMobilization = new PackageMobilization();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("----------");
        System.out.println("DO GET / RECEP");
        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        //response.setStatus(HttpServletResponse.SC_OK);//

        String[] splits = pathInfo.split("/");
        System.out.println("----------");
        for (int i = 1; i < splits.length; i++) {
            System.out.println(splits[i]);
        }
        System.out.println("----------");

        if ((splits.length - 1) == 1) {
            /*if (pathInfo.equals("/destinations")) {
                List<Destination> validDestinations = receptionistService.getValidDestinations();
                response.setStatus(HttpServletResponse.SC_OK);
                gsonDestination.sendAsJson(response, validDestinations);
            }*/ /*else if (pathInfo.equals("/parameters")) {
                //Parameter parameterFromDB = receptionistService.getParameters();
                Parameter parameterFromDB = receptionistService.getCurrentParameter();
                response.setStatus(HttpServletResponse.SC_OK);
                gsonParameter.sendAsJson(response, parameterFromDB);
            } *//*else if (pathInfo.equals("/customers")) {
                List<Customer> customersFromDB = receptionistService.getAllCustomers();
                response.setStatus(HttpServletResponse.SC_OK);
                gsonCustomer.sendAsJson(response, customersFromDB);
            }*/
        } else if ((splits.length - 1) == 2) {
            /*if (pathInfo.equals("/customers/" + splits[2])) {
                String customerNit = splits[2];
                try {
                    Integer.parseInt(customerNit);
                } catch (NumberFormatException e) {
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
            } else if (pathInfo.equals("/customers/InvoiceNo")) {
                int invoice = receptionistService.getLastInvoiceNo() + 1;
                response.setStatus(HttpServletResponse.SC_OK);
                gsonPackage.sendAsJson(response, new Package(invoice));
            } else*/ /*if (pathInfo.equals("/packages/on-standby")) {
                List<Package> packagesOnStandby = receptionistService.getAllPackagesOnStandby();
                response.setStatus(HttpServletResponse.SC_OK);
                gsonPackage.sendAsJson(response, packagesOnStandby);
            }*/
        } /*else if ((splits.length - 1) == 3) {
            if (pathInfo.equals("/packages/on-standby/" + splits[3])) {
                String filter = splits[3];
                //List<Package> filterPackagesOnStandby = receptionistService.filterPackagesOnStandby(filter);
                List<Package> filterPackagesOnStandby = receptionistService.filterPackagesByStatus(filter, 3);
                response.setStatus(HttpServletResponse.SC_OK);
                gsonPackage.sendAsJson(response, filterPackagesOnStandby);
            } else if (pathInfo.equals("/packages/location/" + splits[3])) {

                String filter = splits[3];
                List<Package> filterPackagesOnRoute = receptionistService.filterPackagesByStatus(filter, 2);
                
                List<Process> processes = new ArrayList<>();
                
                for (int i = 0; i < filterPackagesOnRoute.size(); i++) {
                    processes.add(receptionistService.getProcessByPackageId(filterPackagesOnRoute.get(i).getId()));
                }
                
                List<LocationReport> locations = new ArrayList<>();
                
                for (int i = 0; i < processes.size(); i++) {
                    int totalTime = receptionistService.getTotalTimeByPackageId(processes.get(i).getPackageId());
                    locations.add(new LocationReport(processes.get(i).getPackageId(), processes.get(i).getControlPointId(), totalTime));
                }
                
                
                gsonLocation.sendAsJson(response, locations);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }*/ else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String resource = splits[1];
        System.out.println("RESOURCE: " + resource);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO POST / RECEP");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);
        //response.setStatus(HttpServletResponse.SC_OK);//

        String[] splits = pathInfo.split("/");
        String action = splits[1];
        System.out.println("ACCION: " + action);

        /*if (action.equals("packages")) {//createPackage

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

                receptionistService.createProcess(new Process(receptionistService.getPackageCreatedId(), controlPoint.getId()));
            }
            response.setStatus(HttpServletResponse.SC_OK);
            gsonPackage.sendAsJson(response, packageFromJson);

        }*/ /*else if (action.equals("customers")) {//createCustomer

            Customer customerFromJson = gsonCustomer.readFromJson(request, Customer.class);
            System.out.println("Cliente enviado: " + customerFromJson.toString());

            receptionistService.createCustomer(customerFromJson);
            gsonCustomer.sendAsJson(response, customerFromJson);
            response.setStatus(HttpServletResponse.SC_OK);
        }*/
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO PUT / RECEP");
        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //response.setStatus(HttpServletResponse.SC_OK);//
        String[] splits = pathInfo.split("/");

        /*if ((splits.length - 1) == 2) {
            if (pathInfo.equals("/packages/" + splits[2])) {
                String idPackage = splits[2];
                try {
                    Integer.parseInt(idPackage);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Package packageFromDB = receptionistService.getPackageById(Integer.parseInt(idPackage));
                packageFromDB.setStatus(PackageStatus.RETIRADO);
                receptionistService.updatePackage(packageFromDB);
                response.setStatus(HttpServletResponse.SC_OK);
                gsonPackage.sendAsJson(response, packageFromDB);
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }*/
        String action = splits[1];
        System.out.println("ACCION: " + action);
    }

}
