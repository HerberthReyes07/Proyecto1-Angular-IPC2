/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.service;

import com.mycompany.ipc2.p1.backend.data.ControlPointDB;
import com.mycompany.ipc2.p1.backend.data.PackageDB;
import com.mycompany.ipc2.p1.backend.data.ParameterDB;
import com.mycompany.ipc2.p1.backend.data.ProcessDB;
import com.mycompany.ipc2.p1.backend.data.ProcessDetailDB;
import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Process;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.model.PackageStatus;
import com.mycompany.ipc2.p1.backend.model.Parameter;
import com.mycompany.ipc2.p1.backend.model.ProcessDetail;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
public class OperatorService {

    private final ControlPointDB controlPointDB;
    private final ProcessDB processDB;
    private final PackageDB packageDB;
    private final ProcessDetailDB processDetailDB;
    private final ParameterDB parameterDB;

    public OperatorService() {
        this.controlPointDB = new ControlPointDB();
        this.processDB = new ProcessDB();
        this.packageDB = new PackageDB();
        this.processDetailDB = new ProcessDetailDB();
        this.parameterDB = new ParameterDB();
    }

    public List<ControlPoint> getControlPointsByOperatorId(int idOperator) {
        return controlPointDB.getControlPointsByOperatorId(idOperator);
    }

    public List<Process> getUnprocessedPackages() {
        return processDB.getUnprocessedPackages();
    }

    public List<Process> getUnprocessedPackagesByControlPointId(int controlPointId) {
        return processDB.getUnprocessedPackagesByControlPointId(controlPointId);
    }

    public Package getPackageById(int id) {
        return packageDB.getPackageById(id);
    }

    public Process getProcessByPackageId(int packageId) {
        return processDB.getProcessByPackageId(packageId);
    }

    public ControlPoint getControlPointById(int id) {
        return controlPointDB.getControlPointById(id);
    }

    public Package getPackageInWarehouseByDestinationId(int destinationId/*, int currentPackageId*/) {
        return packageDB.getPackageInWarehouseByDestinationId(destinationId/*, currentPackageId*/);
    }

    public ControlPoint getNextControlPointByRouteId(int nextOrderNo, int routeId) {
        return controlPointDB.getNextControlPointByRouteId(nextOrderNo, routeId);
    }

    public Parameter getCurrentParameter() {
        return parameterDB.getCurrentParameter();
    }

    public Parameter getParameterById(int id) {
        return parameterDB.getParameterById(id);
    }

    public void createProcessDetail(ProcessDetail processDetail) {
        processDetailDB.create(processDetail);
    }

    public void createProcess(Process process) {
        processDB.create(process);
    }

    public void updateProcess(Process process) {
        processDB.update(process);
    }

    public void updateControlPointQueueCapacity(ControlPoint controlPointToUpdate, boolean add) {
        int originalQueueCapacity = controlPointToUpdate.getQueueCapacity();
        if (add) {
            controlPointToUpdate.setQueueCapacity(originalQueueCapacity + 1);
            System.out.println("8-" + controlPointToUpdate);
        } else {
            controlPointToUpdate.setQueueCapacity(originalQueueCapacity - 1);
            System.out.println("12-" + controlPointToUpdate);
        }

        controlPointDB.update(controlPointToUpdate);
    }

    public void updatePackage(Package packageSent) {
        packageDB.update(packageSent);
    }

    //----------------------------------------------------//
    public List<Package> packagesToProcessByOperatorId(int operatorId) {

        List<ControlPoint> controlPoints = getControlPointsByOperatorId(operatorId);
        List<Process> unprocessedPackages = getUnprocessedPackages();

        if (controlPoints.isEmpty() || unprocessedPackages.isEmpty()) {
            return null;
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
            return null;
        }

        List<Package> packages = new ArrayList<>();

        for (int i = 0; i < validUnprocessedPackages.size(); i++) {
            packages.add(getPackageById(validUnprocessedPackages.get(i).getPackageId()));
        }

        return packages;
    }

    public List<Package> packagesToProcessByControlPointId(int controlPointId) {
        List<Process> unprocessedPackages = getUnprocessedPackagesByControlPointId(controlPointId);

        if (unprocessedPackages == null) {
            return null;
        }

        List<Package> packages = new ArrayList<>();

        for (int i = 0; i < unprocessedPackages.size(); i++) {
            packages.add(getPackageById(unprocessedPackages.get(i).getPackageId()));
        }

        return packages;
    }

    public ProcessDetail processPackage(int packageId, ProcessDetail processDetailFromJson) {

        Process processToDo = getProcessByPackageId(packageId);
        System.out.println("1-" + processToDo);

        if (processToDo == null) {
            //response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        ControlPoint currentControlPoint = getControlPointById(processToDo.getControlPointId());
        System.out.println("2-" + currentControlPoint);

        Parameter currentParameter = getCurrentParameter();
        System.out.println("2.5-" + currentParameter);

        if (currentControlPoint.getLocalOperationFee() == currentParameter.getGlobalOperationFee()) {
            Package currentPackage = getPackageById(processToDo.getPackageId());
            Parameter parameterToUse = getParameterById(currentPackage.getParameterId());
            processDetailFromJson.setCostProcess(parameterToUse.getGlobalOperationFee() * processDetailFromJson.getTime());
        } else {
            processDetailFromJson.setCostProcess(currentControlPoint.getLocalOperationFee() * processDetailFromJson.getTime());
        }

        processDetailFromJson.setProcessId(processToDo.getId());

        //processDetailFromJson.setCostProcess(currentControlPoint.getLocalOperationFee() * processDetailFromJson.getTime());
        System.out.println("3-" + processDetailFromJson);
        createProcessDetail(processDetailFromJson);

        processToDo.setDone(true);
        System.out.println("4-" + processToDo);
        updateProcess(processToDo);

        boolean packageInWarehouseAdded = false;

        if (currentControlPoint.getOrderNo() == 1) {
            Package currentPackage = getPackageById(processToDo.getPackageId());
            Package packageToAdd = getPackageInWarehouseByDestinationId(currentPackage.getDestinationId());
            System.out.println("5-" + packageToAdd);
            if (packageToAdd != null) {
                packageInWarehouseAdded = true;
                packageToAdd.setStatus(PackageStatus.EN_PUNTO_CONTROL);
                updatePackage(packageToAdd);
                createProcess(new Process(packageToAdd.getId(), processToDo.getControlPointId()));
                System.out.println("6-" + new Process(packageToAdd.getId(), processToDo.getControlPointId()));
            }
        }

        System.out.println("7-" + packageInWarehouseAdded);

        if (!packageInWarehouseAdded) {
            updateControlPointQueueCapacity(currentControlPoint, true);
        }

        ControlPoint nextControlPoint = getNextControlPointByRouteId(currentControlPoint.getOrderNo() + 1, currentControlPoint.getRouteId());
        System.out.println("9-" + nextControlPoint);

        if (nextControlPoint == null) {
            Package packageToUpdate = getPackageById(processToDo.getPackageId());
            packageToUpdate.setStatus(PackageStatus.EN_ESPERA_RETIRO);
            System.out.println("10-" + packageToUpdate);
            updatePackage(packageToUpdate);
        } else {
            System.out.println("11-" + new Process(processToDo.getPackageId(), nextControlPoint.getId()));
            createProcess(new Process(processToDo.getPackageId(), nextControlPoint.getId()));

            updateControlPointQueueCapacity(nextControlPoint, false);
        }

        return processDetailFromJson;
    }

}
