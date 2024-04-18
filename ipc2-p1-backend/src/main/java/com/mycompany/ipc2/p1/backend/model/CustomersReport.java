/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class CustomersReport {

    private int customerId;
    private int packageId;
    private double revenue;
    private double costs;

    public CustomersReport() {
    }

    public CustomersReport(int customerId, int packageId, double revenue, double costs) {
        this.customerId = customerId;
        this.packageId = packageId;
        this.revenue = revenue;
        this.costs = costs;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getCosts() {
        return costs;
    }

    public void setCosts(double costs) {
        this.costs = costs;
    }

    @Override
    public String toString() {
        return "CustomerReport{" + "customerId=" + customerId + ", revenue=" + revenue + ", costs=" + costs + '}';
    }

}
