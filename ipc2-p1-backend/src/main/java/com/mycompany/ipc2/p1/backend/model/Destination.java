/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class Destination {
    private int id;
    private double destinationFee;
    private String name;
    private String address;

    public Destination() {
    }

    public Destination(int id, double destinationFee, String name, String address) {
        this.id = id;
        this.destinationFee = destinationFee;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDestinationFee() {
        return destinationFee;
    }

    public void setDestinationFee(double destinationFee) {
        this.destinationFee = destinationFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Destination{" + "id=" + id + ", cuota_destino=" + destinationFee + ", nombre=" + name + ", direccion=" + address + '}';
    }
    
}
