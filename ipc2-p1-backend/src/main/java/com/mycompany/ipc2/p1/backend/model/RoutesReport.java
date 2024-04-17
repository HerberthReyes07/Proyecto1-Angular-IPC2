/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class RoutesReport {
    
    private int packagesOnRoute;
    private int packagesOffRoute;
    private int routeId;

    public RoutesReport() {
    }

    public RoutesReport(int packagesOnRoute, int packagesOffRoute, int routeId) {
        this.packagesOnRoute = packagesOnRoute;
        this.packagesOffRoute = packagesOffRoute;
        this.routeId = routeId;
    }

    public int getPackagesOnRoute() {
        return packagesOnRoute;
    }

    public void setPackagesOnRoute(int packagesOnRoute) {
        this.packagesOnRoute = packagesOnRoute;
    }

    public int getPackagesOffRoute() {
        return packagesOffRoute;
    }

    public void setPackagesOffRoute(int packagesOffRoute) {
        this.packagesOffRoute = packagesOffRoute;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return "RoutesReport{" + "packagesOnRoute=" + packagesOnRoute + ", packagesOffRoute=" + packagesOffRoute + ", routeId=" + routeId + '}';
    }

}
