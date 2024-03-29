/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class Parameter {
    
    private int id;
    private double globalOperationFee;
    private double pricePerPound;

    public Parameter() {
    }

    public Parameter(int id, double globalOperationFee, double pricePerPound) {
        this.id = id;
        this.globalOperationFee = globalOperationFee;
        this.pricePerPound = pricePerPound;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGlobalOperationFee() {
        return globalOperationFee;
    }

    public void setGlobalOperationFee(double globalOperationFee) {
        this.globalOperationFee = globalOperationFee;
    }

    public double getPricePerPound() {
        return pricePerPound;
    }

    public void setPricePerPound(double pricePerPound) {
        this.pricePerPound = pricePerPound;
    }

    @Override
    public String toString() {
        return "Parameter{" + "id=" + id + ", globalOperationFee=" + globalOperationFee + ", pricePerPound=" + pricePerPound + '}';
    }

}
