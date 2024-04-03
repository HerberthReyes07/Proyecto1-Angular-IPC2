/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.service;

import com.mycompany.ipc2.p1.backend.data.ControlPointDB;
import com.mycompany.ipc2.p1.backend.data.ProcessDB;
import com.mycompany.ipc2.p1.backend.data.RouteDB;
import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Route;
import com.mycompany.ipc2.p1.backend.model.Process;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
public class AdministratorService {
    
    private final RouteDB routeDB;
    private final ProcessDB processDB; 
    private final ControlPointDB controlPointDB;

    public AdministratorService() {
        this.routeDB = new RouteDB();
        this.processDB = new ProcessDB();
        this.controlPointDB = new ControlPointDB();
    }
    
    public void createRoute(Route route){
        routeDB.create(route);
    }
    
    public void updateRoute(Route route){
        routeDB.update(route);
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
    
}
