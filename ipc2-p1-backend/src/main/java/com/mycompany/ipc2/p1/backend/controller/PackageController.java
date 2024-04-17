/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.LocationReport;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.model.PackageStatus;
import com.mycompany.ipc2.p1.backend.model.Process;
import com.mycompany.ipc2.p1.backend.model.ProcessDetail;
import com.mycompany.ipc2.p1.backend.service.OperatorService;
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
@WebServlet("/packages/*")
public class PackageController extends HttpServlet {

    private final GsonUtils<Package> gsonPackage;
    private final GsonUtils<LocationReport> gsonLocation;
    private final GsonUtils<ProcessDetail> gsonProcessDetail;
    private final ReceptionistService receptionistService;
    private final OperatorService operatorService;

    public PackageController() {
        gsonPackage = new GsonUtils<>();
        gsonLocation = new GsonUtils<>();
        gsonProcessDetail = new GsonUtils<>();
        receptionistService = new ReceptionistService();
        operatorService = new OperatorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO GET / PACKAGE");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Package> packages = receptionistService.getAllPackages();
            response.setStatus(HttpServletResponse.SC_OK);
            gsonPackage.sendAsJson(response, packages);
        } else {
            String[] splits = pathInfo.split("/");

            if ((splits.length - 1) == 1) {
                if (pathInfo.equals("/on-standby")) {
                    List<Package> packagesOnStandby = receptionistService.getAllPackagesOnStandby();
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonPackage.sendAsJson(response, packagesOnStandby);
                } else if (pathInfo.equals("/invoiceNo")) {
                    int invoice = receptionistService.getLastInvoiceNo() + 1;
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonPackage.sendAsJson(response, new Package(invoice));
                } else if (pathInfo.equals("/on-route")) {
                    List<Package> packagesOnRoute = receptionistService.getAllPackagesOnRoute();
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonPackage.sendAsJson(response, packagesOnRoute);
                } else if (pathInfo.equals("/locations")) {
                    List<LocationReport> locations = receptionistService.getLocationsOfPackagesByNitOrInvoiceNo(null);
                    gsonLocation.sendAsJson(response, locations);
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } else if ((splits.length - 1) == 2) {
                if (pathInfo.equals("/on-standby/" + splits[2])) {
                    String filter = splits[2];
                    List<Package> filterPackagesOnStandby = receptionistService.filterPackagesByStatus(filter, 3);
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonPackage.sendAsJson(response, filterPackagesOnStandby);
                } else if (pathInfo.equals("/location/" + splits[2])) {
                    List<LocationReport> locations = receptionistService.getLocationsOfPackagesByNitOrInvoiceNo(splits[2]);
                    gsonLocation.sendAsJson(response, locations);
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } else if ((splits.length - 1) == 3) {
                if (pathInfo.equals("/to-process/operator/" + splits[3])) {

                    String operatorId = splits[3];
                    try {
                        Integer.parseInt(operatorId);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    List<Package> packages = operatorService.packagesToProcessByOperatorId(Integer.parseInt(operatorId));

                    if (packages == null) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    System.out.println(packages);

                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonPackage.sendAsJson(response, packages);
                } else if (pathInfo.equals("/to-process/control-point/" + splits[3])) {

                    String controlPointId = splits[3];
                    try {
                        Integer.parseInt(controlPointId);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    List<Package> packages = operatorService.packagesToProcessByControlPointId(Integer.parseInt(controlPointId));

                    if (packages == null) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    System.out.println(packages);

                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonPackage.sendAsJson(response, packages);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DO POST / PACKAGE");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {

            Package packageFromJson;
            try {
                packageFromJson = gsonPackage.readFromJson(request, Package.class);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            System.out.println("Paquete enviado: " + packageFromJson.toString());

            Package createdPackage = receptionistService.registerPackage(packageFromJson);
            response.setStatus(HttpServletResponse.SC_OK);
            gsonPackage.sendAsJson(response,packageFromJson);

        } else {
            String[] splits = pathInfo.split("/");
            if ((splits.length - 1) == 2) {
                if (pathInfo.equals("/process/" + splits[2])) {

                    String packageId = splits[2];
                    ProcessDetail processDetailFromJson;
                    try {
                        Integer.parseInt(packageId);
                        processDetailFromJson = gsonProcessDetail.readFromJson(request, ProcessDetail.class);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    ProcessDetail processDetail = operatorService.processPackage(Integer.parseInt(packageId), processDetailFromJson);

                    if (processDetail == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonProcessDetail.sendAsJson(response, processDetail);
                }
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO PUT / PACKAGE");
        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if ((splits.length - 1) == 1) {
            if (pathInfo.equals("/" + splits[1])) {
                String idPackage = splits[1];
                //Package packageFromJson;
                try {
                    Integer.parseInt(idPackage);
                    //packageFromJson = gsonPackage.readFromJson(request, Package.class);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                //VER BIEN ESTO
                Package packageFromDB = receptionistService.getPackageById(Integer.parseInt(idPackage));
                if (packageFromDB == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                packageFromDB.setStatus(PackageStatus.RETIRADO);
                receptionistService.updatePackage(packageFromDB);
                response.setStatus(HttpServletResponse.SC_OK);
                gsonPackage.sendAsJson(response, packageFromDB);
            }

        }
    }

}
