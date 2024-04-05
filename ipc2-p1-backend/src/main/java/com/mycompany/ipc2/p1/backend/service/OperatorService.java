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
import com.mycompany.ipc2.p1.backend.model.Parameter;
import com.mycompany.ipc2.p1.backend.model.ProcessDetail;
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

}
