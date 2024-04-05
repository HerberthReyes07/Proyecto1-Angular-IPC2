/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.service;

import com.mycompany.ipc2.p1.backend.data.ControlPointDB;
import com.mycompany.ipc2.p1.backend.data.CustomerDB;
import com.mycompany.ipc2.p1.backend.data.DestinationDB;
import com.mycompany.ipc2.p1.backend.data.ProcessDB;
import com.mycompany.ipc2.p1.backend.data.PackageDB;
import com.mycompany.ipc2.p1.backend.data.ParameterDB;
import com.mycompany.ipc2.p1.backend.data.ProcessDetailDB;
import com.mycompany.ipc2.p1.backend.data.RouteDB;
import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.model.Customer;
import com.mycompany.ipc2.p1.backend.model.Destination;
import com.mycompany.ipc2.p1.backend.model.LocationReport;
import com.mycompany.ipc2.p1.backend.model.PackageStatus;
import com.mycompany.ipc2.p1.backend.model.Process;
import com.mycompany.ipc2.p1.backend.model.Parameter;
import com.mycompany.ipc2.p1.backend.model.Route;
import com.mycompany.ipc2.p1.backend.utils.PackageMobilization;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
public class ReceptionistService {

    private final DestinationDB destinationDB;
    private final RouteDB routeDB;
    private final CustomerDB customerDB;
    private final PackageDB packageDB;
    private final ControlPointDB controlPointDB;
    private final ProcessDB processDB;
    private final ParameterDB parameterDB;
    private final ProcessDetailDB processDetailDB;
    private final OperatorService operatorService;

    public ReceptionistService() {
        this.destinationDB = new DestinationDB();
        this.routeDB = new RouteDB();
        this.customerDB = new CustomerDB();
        this.packageDB = new PackageDB();
        this.controlPointDB = new ControlPointDB();
        this.processDB = new ProcessDB();
        this.parameterDB = new ParameterDB();
        this.processDetailDB = new ProcessDetailDB();
        this.operatorService = new OperatorService();
    }

    public List<Destination> getAllDestinations() {
        return destinationDB.getAllDestinations();
    }

    public List<Route> getAllRoutes() {
        return routeDB.getAllRoutes();
    }

    public List<Destination> getValidDestinations() {
        List<Destination> destinations = getAllDestinations();
        List<Route> routes = getAllRoutes();

        List<Destination> validDestinations = new ArrayList<>();

        for (int i = 0; i < destinations.size(); i++) {
            for (int j = 0; j < routes.size(); j++) {
                if (destinations.get(i).getId() == routes.get(j).getDestinationId()) {
                    validDestinations.add(destinations.get(i));
                    break;
                }
            }
        }

        return validDestinations;
    }

    public Customer getCustomerByNit(String nit) {
        return customerDB.getCustomerByNit(nit).orElse(null);
    }

    /*public Parameter getParameters() {
        return parameterDB.getParameters();
    }*/
    public Parameter getCurrentParameter() {
        return parameterDB.getCurrentParameter();
    }

    public int getLastInvoiceNo() {
        return packageDB.getLastInvoiceNo();
    }

    public int getPackageCreatedId() {
        return packageDB.getPackageCreatedId();
    }

    public List<Customer> getAllCustomers() {
        return customerDB.getAllCustomers();
    }

    public Package getPackageById(int id) {
        return packageDB.getPackageById(id);
    }

    public List<Package> getAllPackagesOnStandby() {
        return packageDB.getAllPackagesOnStandby();
    }

    /*public List<Package> filterPackagesOnStandby(String filter) {
        //return packageDB.filterPackagesOnStandby(filter);
        return  packageDB.filterPackagesByStatus(filter, 3);
    }*/
    public List<Package> filterPackagesByStatus(String filter, int status) {
        return packageDB.filterPackagesByStatus(filter, status);
    }

    public Process getProcessByPackageId(int packageId) {
        return processDB.getProcessByPackageId(packageId);
    }

    public int getTotalTimeByPackageId(int packageId) {
        return processDetailDB.getTotalTimeByPackageId(packageId);
    }

    public void createCustomer(Customer customer) {
        customerDB.create(customer);
    }

    public void createPackage(Package packageSent) {
        packageDB.create(packageSent);
    }

    public void createProcess(Process process) {
        processDB.create(process);
    }

    public void updateControlPoint(ControlPoint controlPoint) {
        controlPointDB.update(controlPoint);
    }

    public void updatePackage(Package packageSent) {
        packageDB.update(packageSent);
    }

    //----------------------------------------------------------------------------//
    public List<LocationReport> getLocationsOfPackagesByNitOrInvoiceNo(String filter) {
        List<Package> filterPackagesOnRoute = filterPackagesByStatus(filter, 2);

        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < filterPackagesOnRoute.size(); i++) {
            processes.add(getProcessByPackageId(filterPackagesOnRoute.get(i).getId()));
        }

        List<LocationReport> locations = new ArrayList<>();

        for (int i = 0; i < processes.size(); i++) {
            int totalTime = getTotalTimeByPackageId(processes.get(i).getPackageId());
            locations.add(new LocationReport(processes.get(i).getPackageId(), processes.get(i).getControlPointId(), totalTime));
        }
        return locations;
    }

    public Package registerPackage(Package packageFromJson) {

        PackageMobilization packageMobilization = new PackageMobilization();

        List<Route> mobilizationRoutes = packageMobilization.getRoutesByDestinationId(packageFromJson.getDestinationId());
        System.out.println("1-MR: " + mobilizationRoutes.toString());//Ruta 1-2

        ControlPoint controlPoint = packageMobilization.getFirstControlPoint(mobilizationRoutes);

        packageFromJson.setParameterId(getCurrentParameter().getId());///
        
        

        if (controlPoint == null) {
            System.out.println("EN BODEGA");
            packageFromJson.setStatus(PackageStatus.EN_BODEGA);
            createPackage(packageFromJson);
        } else {

            System.out.println("CP: " + controlPoint.toString());
            packageFromJson.setStatus(PackageStatus.EN_PUNTO_CONTROL);
            createPackage(packageFromJson);

            int originalQueueCapacity = controlPoint.getQueueCapacity();
            controlPoint.setQueueCapacity(originalQueueCapacity - 1);
            updateControlPoint(controlPoint);

            createProcess(new Process(getPackageCreatedId(), controlPoint.getId()));
        }
        
        return packageFromJson;
    }
}
