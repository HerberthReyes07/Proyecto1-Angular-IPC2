/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.service;

import com.mycompany.ipc2.p1.backend.data.ControlPointDB;
import com.mycompany.ipc2.p1.backend.data.ParameterDB;
import com.mycompany.ipc2.p1.backend.data.ProcessDB;
import com.mycompany.ipc2.p1.backend.data.RouteDB;
import com.mycompany.ipc2.p1.backend.data.UserDB;
import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Parameter;
import com.mycompany.ipc2.p1.backend.model.Route;
import com.mycompany.ipc2.p1.backend.model.Process;
import com.mycompany.ipc2.p1.backend.model.User;
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

    public AdministratorService() {
        this.routeDB = new RouteDB();
        this.processDB = new ProcessDB();
        this.controlPointDB = new ControlPointDB();
        this.userDB = new UserDB();
        this.parameterDB = new ParameterDB();
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

    /*public int getParameterCreatedId() {
        return parameterDB.getParameterCreatedId();
    }*/
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
        return  canProceed;
    }
    
    
}
