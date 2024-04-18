/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class PopularRoutesReport {

    private int routeId;
    private int packagesQuantity;

    public PopularRoutesReport() {
    }

    public PopularRoutesReport(int routeId, int packagesQuantity) {
        this.routeId = routeId;
        this.packagesQuantity = packagesQuantity;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getPackagesQuantity() {
        return packagesQuantity;
    }

    public void setPackagesQuantity(int packagesQuantity) {
        this.packagesQuantity = packagesQuantity;
    }

    @Override
    public String toString() {
        return "PopularRoutesReport{" + "routeId=" + routeId + ", packagesQuantity=" + packagesQuantity + '}';
    }

}
