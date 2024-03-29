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

    public Destination(int id, double cuota_destino, String nombre, String direccion) {
        this.id = id;
        this.destinationFee = cuota_destino;
        this.name = nombre;
        this.address = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCuota_destino() {
        return destinationFee;
    }

    public void setCuota_destino(double cuota_destino) {
        this.destinationFee = cuota_destino;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public String getDireccion() {
        return address;
    }

    public void setDireccion(String direccion) {
        this.address = direccion;
    }

    @Override
    public String toString() {
        return "Destination{" + "id=" + id + ", cuota_destino=" + destinationFee + ", nombre=" + name + ", direccion=" + address + '}';
    }
    
}
