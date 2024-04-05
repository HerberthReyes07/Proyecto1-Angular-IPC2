/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.controller;

import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Process;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.model.PackageStatus;
import com.mycompany.ipc2.p1.backend.model.Parameter;
import com.mycompany.ipc2.p1.backend.model.ProcessDetail;
import com.mycompany.ipc2.p1.backend.service.OperatorService;
import com.mycompany.ipc2.p1.backend.utils.GsonUtils;
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
@WebServlet("/oper/*")
public class OperatorController extends HttpServlet {

    private final GsonUtils<ControlPoint> gsonControlPoint;
    private final GsonUtils<ProcessDetail> gsonProcessDetail;
    private final OperatorService operatorService;

    public OperatorController() {
        gsonControlPoint = new GsonUtils<>();
        gsonProcessDetail = new GsonUtils<>();
        operatorService = new OperatorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("----------");
        System.out.println("DO GET / OPER");
        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");
        System.out.println("----------");
        for (int i = 1; i < splits.length; i++) {
            System.out.println(splits[i]);
        }
        System.out.println("----------");

        if ((splits.length - 1) == 3) {
            if (pathInfo.equals("/process/packages/" + splits[3])) {

                String operatorId = splits[3];
                try {
                    Integer.parseInt(operatorId);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                List<ControlPoint> controlPoints = operatorService.getControlPointsByOperatorId(Integer.parseInt(operatorId));
                List<Process> unprocessedPackages = operatorService.getUnprocessedPackages();

                if (controlPoints.isEmpty() || unprocessedPackages.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                List<Process> validUnprocessedPackages = new ArrayList<>();

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
                gsonControlPoint.sendAsJson(response, packages);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DO POST / OPER");

        String pathInfo = request.getPathInfo();
        System.out.println("PATH INFO: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");
        System.out.println("----------");
        for (int i = 1; i < splits.length; i++) {
            System.out.println(splits[i]);
        }
        System.out.println("----------");

        if ((splits.length - 1) == 3) {
            if (pathInfo.equals("/process/package/" + splits[3])) {

                String packagerId = splits[3];
                ProcessDetail processDetailFromJson;
                try {
                    Integer.parseInt(packagerId);
                    processDetailFromJson = gsonProcessDetail.readFromJson(request, ProcessDetail.class);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                Process processToDo = operatorService.getProcessByPackageId(Integer.parseInt(packagerId));
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
                    Package packageToAdd = operatorService.getPackageInWarehouseByDestinationId(currentPackage.getDestinationId()/*, currentPackage.getId()*/);
                    System.out.println("5-" + packageToAdd);
                    if (packageToAdd != null) {
                        packageInWarehouseAdded = true;
                        packageToAdd.setStatus(PackageStatus.EN_PUNTO_CONTROL);
                        operatorService.updatePackage(packageToAdd);
                        operatorService.createProcess(new Process(packageToAdd.getId(), processToDo.getControlPointId()));
                        System.out.println("6-" + new Process(packageToAdd.getId(), processToDo.getControlPointId()));
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
                    System.out.println("11-" + new Process(processToDo.getPackageId(), nextControlPoint.getId()));
                    operatorService.createProcess(new Process(processToDo.getPackageId(), nextControlPoint.getId()));

                    operatorService.updateControlPointQueueCapacity(nextControlPoint, false);
                }
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
