/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.service;

import com.mycompany.ipc2.p1.backend.data.ControlPointDB;
import com.mycompany.ipc2.p1.backend.data.DestinationDB;
import com.mycompany.ipc2.p1.backend.data.PackageDB;
import com.mycompany.ipc2.p1.backend.data.ParameterDB;
import com.mycompany.ipc2.p1.backend.data.ProcessDB;
import com.mycompany.ipc2.p1.backend.data.ProcessDetailDB;
import com.mycompany.ipc2.p1.backend.data.RouteDB;
import com.mycompany.ipc2.p1.backend.data.UserDB;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Destination;
import com.mycompany.ipc2.p1.backend.model.EarningsReport;
import com.mycompany.ipc2.p1.backend.model.PackageStatus;
import com.mycompany.ipc2.p1.backend.model.Parameter;
import com.mycompany.ipc2.p1.backend.model.Route;
import com.mycompany.ipc2.p1.backend.model.Process;
import com.mycompany.ipc2.p1.backend.model.ProcessDetail;
import com.mycompany.ipc2.p1.backend.model.RoutesReport;
import com.mycompany.ipc2.p1.backend.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
public class AdministratorService {
    
    private final RouteDB routeDB;
    private final ProcessDB processDB;
    private final ControlPointDB controlPointDB;
    private final UserDB userDB;
    private final ParameterDB parameterDB;
    private final DestinationDB destinationDB;
    private final PackageDB packageDB;
    private final ProcessDetailDB processDetailDB;
    
    public AdministratorService() {
        this.routeDB = new RouteDB();
        this.processDB = new ProcessDB();
        this.controlPointDB = new ControlPointDB();
        this.userDB = new UserDB();
        this.parameterDB = new ParameterDB();
        this.destinationDB = new DestinationDB();
        this.packageDB = new PackageDB();
        this.processDetailDB = new ProcessDetailDB();
    }
    
    public void createRoute(Route route) {
        routeDB.create(route);
    }
    
    public void createControlPoint(ControlPoint controlPoint) {
        controlPointDB.create(controlPoint);
    }
    
    public void createUser(User user) {
        userDB.create(user);
    }
    
    public void createParameter(Parameter parameter) {
        parameterDB.create(parameter);
    }
    
    public void createDestination(Destination destination) {
        destinationDB.create(destination);
    }
    
    public void updateRoute(Route route) {
        routeDB.update(route);
    }
    
    public void updateControlPoint(ControlPoint controlPoint) {
        controlPointDB.update(controlPoint);
    }
    
    public void updateUser(User user) {
        userDB.update(user);
    }
    
    public void updateLocalOperationFee(double currentLocalOperationFee, double localOperationFeeToSet) {
        controlPointDB.updateLocalOperationFee(currentLocalOperationFee, localOperationFeeToSet);
    }
    
    public void updateDestination(Destination destination) {
        destinationDB.update(destination);
    }

    public List<Route> getAllRoutes() {
        return routeDB.getAllRoutes();
    }
    
    public Route getRouteById(int id) {
        return routeDB.getRouteById(id);
    }
    
    public List<Process> getUnprocessedPackages() {
        return processDB.getUnprocessedPackages();
    }
    
    public List<ControlPoint> getControlPointsByRouteId(int routeId) {
        return controlPointDB.getControlPointsByRouteId(routeId);
    }
    
    public int getOrderNoByRouteId(int routeId) {
        return controlPointDB.getOrderNoByRouteId(routeId);
    }
    
    public List<ControlPoint> getAllControlPoints() {
        return controlPointDB.getAllControlPoints();
    }
    
    public ControlPoint getControlPointById(int id) {
        return controlPointDB.getControlPointById(id);
    }
    
    public List<User> getAllUsers() {
        return userDB.getAllUsers();
    }
    
    public User getUserById(int id) {
        return userDB.getUserById(id);
    }
    
    public List<User> getUsersByCodeTypeUser(int codeTypeUser) {
        return userDB.getUsersByCodeTypeUser(codeTypeUser);
    }
    
    public List<User> getUsersByStatus(boolean active) {
        return userDB.getUsersByStatus(active);
    }
    
    public Parameter getCurrentParameter() {
        return parameterDB.getCurrentParameter();
    }
    
    public List<Parameter> getAllParameters() {
        return parameterDB.getAllParameters();
    }

    public boolean canProceed(int routeId) {
        List<Process> unprocessedPackages = getUnprocessedPackages();
        List<ControlPoint> controlPoints = getControlPointsByRouteId(routeId);
        boolean canProceed = true;
        
        for (int j = 0; j < unprocessedPackages.size(); j++) {
            for (int i = 0; i < controlPoints.size(); i++) {
                if (controlPoints.get(i).getId() == unprocessedPackages.get(j).getControlPointId()) {
                    canProceed = false;
                    break;
                }
            }
            if (!canProceed) {
                break;
            }
        }
        return canProceed;
    }
    
    public List<RoutesReport> getRoutesReport(){
        
        List<Package> packagesOnStandBy = packageDB.getAllPackagesByStatus(3);
        List<Package> packagesPickedUp = packageDB.getAllPackagesByStatus(4);
        
        List<Package> packagesOffRoute = new ArrayList<>();
        packagesOffRoute.addAll(packagesOnStandBy);
        packagesOffRoute.addAll(packagesPickedUp);

        List<Package> packagesOnRoute = packageDB.getAllPackagesByStatus(2);
        
        List<Process> processesOnRoute = new ArrayList<>();

        for (int i = 0; i < packagesOnRoute.size(); i++) {
            processesOnRoute.add(processDB.getProcessByPackageId(packagesOnRoute.get(i).getId(), false));
        }
        
        List<Process> processesOffRoute = new ArrayList<>();
        
        for (int i = 0; i < packagesOffRoute.size(); i++) {
            processesOffRoute.add(processDB.getProcessByPackageId(packagesOffRoute.get(i).getId(), true));
        }
        
        List<RoutesReport> routesReports = new ArrayList<>();
        
        List<Route> routes = getAllRoutes();
        
        for (int i = 0; i < routes.size(); i++) {
            
            int onRoute = 0;
            int offRoute = 0;
            
            for (int j = 0; j < processesOnRoute.size(); j++) {
                ControlPoint controlPoint = controlPointDB.getControlPointById(processesOnRoute.get(j).getControlPointId());
                if (controlPoint.getRouteId() == routes.get(i).getId()) {
                    onRoute++;
                }
            }
            for (int j = 0; j < processesOffRoute.size(); j++) {
                ControlPoint controlPoint = controlPointDB.getControlPointById(processesOffRoute.get(j).getControlPointId());
                if (controlPoint.getRouteId() == routes.get(i).getId()) {
                    offRoute++;
                }
            }

            routesReports.add(new RoutesReport(onRoute, offRoute, routes.get(i).getId()));
        }
        
        return routesReports;
    }
    
    public List<EarningsReport> getEarningsReport(String initialDate, String finalDate){
        
        List<Package> packages;
        List<ProcessDetail> processDetails;
        
        if (initialDate == null && finalDate == null) {
            packages = packageDB.getAllPackages();
            processDetails = processDetailDB.getAllProcessDetails();
        } else {
            packages = packageDB.getAllPackagesByDateRange(initialDate, finalDate);
            processDetails = processDetailDB.getAllProcessDetailsByDateRange(initialDate, finalDate);
        }
        
        List<Process> revenueProcesses = new ArrayList<>();
        
        for (int i = 0; i < packages.size(); i++) {
            //ver lo de EN_BODEGA
            if (packages.get(i).getStatus() == PackageStatus.EN_PUNTO_CONTROL) {
                revenueProcesses.add(processDB.getProcessByPackageId(packages.get(i).getId(), false));
            } else if (packages.get(i).getStatus() == PackageStatus.EN_ESPERA_RETIRO || packages.get(i).getStatus() == PackageStatus.RETIRADO) {
                revenueProcesses.add(processDB.getProcessByPackageId(packages.get(i).getId(), true));
            }
        }
        
        List<Process> costsProcesses = new ArrayList<>();
        
        for (int i = 0; i < processDetails.size(); i++) {
            costsProcesses.add(processDB.getProcessById(processDetails.get(i).getProcessId()));
        }
        

        List<Route> routes = getAllRoutes();
        List<EarningsReport> earningsReports = new ArrayList<>();
        
        for (int i = 0; i < routes.size(); i++) {
            
            double revenue = 0;
            double costs = 0;
            
            for (int j = 0; j < revenueProcesses.size(); j++) {
                ControlPoint controlPoint = controlPointDB.getControlPointById(revenueProcesses.get(j).getControlPointId());
                if (controlPoint.getRouteId() == routes.get(i).getId()) {
                    revenue += packageDB.getPackageById(revenueProcesses.get(j).getPackageId()).getShippingCost();
                }
            }
            
            for (int j = 0; j < costsProcesses.size(); j++) {
                ControlPoint controlPoint = controlPointDB.getControlPointById(costsProcesses.get(j).getControlPointId());
                if (controlPoint.getRouteId() == routes.get(i).getId()) {
                    costs += processDetailDB.getCostByProcessId(costsProcesses.get(j).getId());
                }                
            }
            
            earningsReports.add(new EarningsReport(costs, revenue, routes.get(i).getId()));
        }
        
        return earningsReports;
    }
    
}
