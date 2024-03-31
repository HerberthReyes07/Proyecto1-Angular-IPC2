/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.service;

import com.mycompany.ipc2.p1.backend.data.ControlPointDB;
import com.mycompany.ipc2.p1.backend.data.CustomerDB;
import com.mycompany.ipc2.p1.backend.data.DestinationDB;
import com.mycompany.ipc2.p1.backend.data.PackageControlPointDB;
import com.mycompany.ipc2.p1.backend.data.PackageDB;
import com.mycompany.ipc2.p1.backend.data.ParameterDB;
import com.mycompany.ipc2.p1.backend.data.RouteDB;
import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.model.Customer;
import com.mycompany.ipc2.p1.backend.model.Destination;
import com.mycompany.ipc2.p1.backend.model.Parameter;
import com.mycompany.ipc2.p1.backend.model.Route;
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
    private final PackageControlPointDB packageControlPointDB;
    private final ParameterDB parameterDB;

    public ReceptionistService() {
        this.destinationDB = new DestinationDB();
        this.routeDB = new RouteDB();
        this.customerDB = new CustomerDB();
        this.packageDB = new PackageDB();
        this.controlPointDB = new ControlPointDB();
        this.packageControlPointDB = new PackageControlPointDB();
        this.parameterDB = new ParameterDB();
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

    public Parameter getParameters() {
        return parameterDB.getParameters();
    }

    public int getLastInvoiceNo() {
        return packageDB.getLastInvoiceNo();
    }

    public int getPackageCreatedId() {
        return packageDB.getPackageCreatedId();
    }
    
    public List<Customer> getAllCustomers(){
        return customerDB.getAllCustomers();
    }
    
    public List<Package> getAllPackagesOnStandby() {
        return packageDB.getAllPackagesOnStandby();
    }
    
    public List<Package> filterPackagesOnStandby(String filter) {
        return packageDB.filterPackagesOnStandby(filter);
    }

    public void createCustomer(Customer customer) {
        customerDB.create(customer);
    }

    public void createPackage(Package packageSent) {
        packageDB.create(packageSent);
    }

    public void createPackageControlPoint(int packageId, int controlPointId) {
        packageControlPointDB.create(packageId, controlPointId);
    }

    public void updateControlPoint(ControlPoint controlPoint) {
        controlPointDB.update(controlPoint);
    }
    
    public void updatePackage(Package packageSent){
        packageDB.update(packageSent);
    }

}
