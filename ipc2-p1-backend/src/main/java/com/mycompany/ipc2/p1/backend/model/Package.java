/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class Package {
    private int id;
    private int customerId;
    private int destinationId;
    private double weight;
    private double shippingCost;
    private PackageStatus status;
    private int invoiceNo;

    public Package() {
    }

    public Package(int id, int customerId, int destinationId, double weight, double shippingCost, PackageStatus status, int invoiceNo) {
        this.id = id;
        this.customerId = customerId;
        this.destinationId = destinationId;
        this.weight = weight;
        this.shippingCost = shippingCost;
        this.status = status;
        this.invoiceNo = invoiceNo;
    }

    public Package(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @Override
    public String toString() {
        return "Package{" + "id=" + id + ", clientId=" + customerId + ", destinationId=" + destinationId + ", weight=" + weight + ", shippingCost=" + shippingCost + ", status=" + status + ", invoiceNo=" + invoiceNo + '}';
    }
    
}
