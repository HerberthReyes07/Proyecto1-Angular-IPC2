/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class EarningsReport {
    
    private double costs;
    private double revenue;
    private int routeId;

    public EarningsReport() {
    }

    public EarningsReport(double costs, double revenue, int routeId) {
        this.costs = costs;
        this.revenue = revenue;
        this.routeId = routeId;
    }

    public double getCosts() {
        return costs;
    }

    public void setCosts(double costs) {
        this.costs = costs;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return "EarningsReport{" + "costs=" + costs + ", revenue=" + revenue + ", routeId=" + routeId + '}';
    }
    
}
