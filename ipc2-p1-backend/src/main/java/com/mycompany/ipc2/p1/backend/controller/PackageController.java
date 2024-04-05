/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.LocationReport;
import com.mycompany.ipc2.p1.backend.model.Package;
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
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            //return;
            /*List<Customer> customersFromDB = receptionistService.getAllCustomers();
            response.setStatus(HttpServletResponse.SC_OK);
            gsonCustomer.sendAsJson(response, customersFromDB);*/
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
                }
            } else if ((splits.length - 1) == 2) {
                if (pathInfo.equals("/on-standby/" + splits[2])) {
                    String filter = splits[2];
                    //List<Package> filterPackagesOnStandby = receptionistService.filterPackagesOnStandby(filter);
                    List<Package> filterPackagesOnStandby = receptionistService.filterPackagesByStatus(filter, 3);
                    response.setStatus(HttpServletResponse.SC_OK);
                    gsonPackage.sendAsJson(response, filterPackagesOnStandby);
                } else if (pathInfo.equals("/location/" + splits[2])) {

                    //PASARLO AL SERVICIO++
                    /*String filter = splits[2];
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
                    response.setStatus(HttpServletResponse.SC_OK);*/
                    List<LocationReport> locations = receptionistService.getLocationsOfPackagesByNitOrInvoiceNo(splits[2]);
                    gsonLocation.sendAsJson(response, locations);
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } else if ((splits.length - 1) == 3) {
                if (pathInfo.equals("/to-process/operator/" + splits[3])) {

                    //System.out.println("PASARLO AL SERVICIO");++
                    /*String operatorId = splits[3];
                    try {
                        Integer.parseInt(operatorId);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    List<ControlPoint> controlPoints = operatorService.getControlPointsByOperatorId(Integer.parseInt(operatorId));
                    List<com.mycompany.ipc2.p1.backend.model.Process> unprocessedPackages = operatorService.getUnprocessedPackages();

                    if (controlPoints.isEmpty() || unprocessedPackages.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    List<com.mycompany.ipc2.p1.backend.model.Process> validUnprocessedPackages = new ArrayList<>();

                    for (int i = 0; i < controlPoints.size(); i++) {
                        for (int j = 0; j < unprocessedPackages.size(); j++) {
                            if (controlPoints.get(i).getId() == unprocessedPackages.get(j).getControlPointId()) {
                                validUnprocessedPackages.add(unprocessedPackages.get(j));
                            }
                        }
                    }

                    if (validUnprocessedPackages.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    List<Package> packages = new ArrayList<>();

                    for (int i = 0; i < validUnprocessedPackages.size(); i++) {
                        packages.add(operatorService.getPackageById(validUnprocessedPackages.get(i).getPackageId()));
                    }

                    System.out.println(packages);

                    response.setStatus(HttpServletResponse.SC_OK);
                    //gsonControlPoint.sendAsJson(response, packages);
                    gsonPackage.sendAsJson(response, packages);*/
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

            //PASARLO AL SERVICIO++
            /*Package packageFromJson;
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
            gsonPackage.sendAsJson(response, packageFromJson);*/
            
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
            gsonPackage.sendAsJson(response, createdPackage);
            
            
        } else {
            String[] splits = pathInfo.split("/");
            if ((splits.length - 1) == 2) {
                if (pathInfo.equals("/process/" + splits[2])) {

                    //PASARLO AL SERVICIO
                    /*String packageId = splits[2];
                    ProcessDetail processDetailFromJson;
                    try {
                        Integer.parseInt(packageId);
                        processDetailFromJson = gsonProcessDetail.readFromJson(request, ProcessDetail.class);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    Process processToDo = operatorService.getProcessByPackageId(Integer.parseInt(packageId));
                    System.out.println("1-" + processToDo);

                    if (processToDo == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    ControlPoint currentControlPoint = operatorService.getControlPointById(processToDo.getControlPointId());
                    System.out.println("2-" + currentControlPoint);

                    Parameter currentParameter = operatorService.getCurrentParameter();
                    System.out.println("2.5-" + currentParameter);

                    if (currentControlPoint.getLocalOperationFee() == currentParameter.getGlobalOperationFee()) {
                        Package currentPackage = operatorService.getPackageById(processToDo.getPackageId());
                        Parameter parameterToUse = operatorService.getParameterById(currentPackage.getParameterId());
                        processDetailFromJson.setCostProcess(parameterToUse.getGlobalOperationFee() * processDetailFromJson.getTime());
                    } else {
                        processDetailFromJson.setCostProcess(currentControlPoint.getLocalOperationFee() * processDetailFromJson.getTime());
                    }

                    processDetailFromJson.setProcessId(processToDo.getId());

                    //processDetailFromJson.setCostProcess(currentControlPoint.getLocalOperationFee() * processDetailFromJson.getTime());
                    System.out.println("3-" + processDetailFromJson);
                    operatorService.createProcessDetail(processDetailFromJson);

                    processToDo.setDone(true);
                    System.out.println("4-" + processToDo);
                    operatorService.updateProcess(processToDo);

                    boolean packageInWarehouseAdded = false;

                    if (currentControlPoint.getOrderNo() == 1) {
                        Package currentPackage = operatorService.getPackageById(processToDo.getPackageId());
                        Package packageToAdd = operatorService.getPackageInWarehouseByDestinationId(currentPackage.getDestinationId());
                        System.out.println("5-" + packageToAdd);
                        if (packageToAdd != null) {
                            packageInWarehouseAdded = true;
                            packageToAdd.setStatus(PackageStatus.EN_PUNTO_CONTROL);
                            operatorService.updatePackage(packageToAdd);
                            operatorService.createProcess(new com.mycompany.ipc2.p1.backend.model.Process(packageToAdd.getId(), processToDo.getControlPointId()));
                            System.out.println("6-" + new com.mycompany.ipc2.p1.backend.model.Process(packageToAdd.getId(), processToDo.getControlPointId()));
                        }
                    }

                    System.out.println("7-" + packageInWarehouseAdded);

                    if (!packageInWarehouseAdded) {
                        operatorService.updateControlPointQueueCapacity(currentControlPoint, true);
                    }

                    ControlPoint nextControlPoint = operatorService.getNextControlPointByRouteId(currentControlPoint.getOrderNo() + 1, currentControlPoint.getRouteId());
                    System.out.println("9-" + nextControlPoint);

                    if (nextControlPoint == null) {
                        Package packageToUpdate = operatorService.getPackageById(processToDo.getPackageId());
                        packageToUpdate.setStatus(PackageStatus.EN_ESPERA_RETIRO);
                        System.out.println("10-" + packageToUpdate);
                        operatorService.updatePackage(packageToUpdate);
                    } else {
                        System.out.println("11-" + new com.mycompany.ipc2.p1.backend.model.Process(processToDo.getPackageId(), nextControlPoint.getId()));
                        operatorService.createProcess(new com.mycompany.ipc2.p1.backend.model.Process(processToDo.getPackageId(), nextControlPoint.getId()));

                        operatorService.updateControlPointQueueCapacity(nextControlPoint, false);
                    }
                    response.setStatus(HttpServletResponse.SC_OK);*/
                    
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
                Package packageFromJson;
                try {
                    Integer.parseInt(idPackage);
                    packageFromJson = gsonPackage.readFromJson(request, Package.class);
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
                /*packageFromDB.setStatus(PackageStatus.RETIRADO);*/
                receptionistService.updatePackage(packageFromJson);
                response.setStatus(HttpServletResponse.SC_OK);
                gsonPackage.sendAsJson(response, packageFromJson);
            }

        }
    }

}
