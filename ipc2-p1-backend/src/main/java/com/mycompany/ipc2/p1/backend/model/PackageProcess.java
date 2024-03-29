/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class PackageProcess {
    private int id;
    private int time;
    private double costProcess;
    private int controlPointId;

    public PackageProcess() {
    }

    public PackageProcess(int id, int time, double costProcess, int controlPointId) {
        this.id = id;
        this.time = time;
        this.costProcess = costProcess;
        this.controlPointId = controlPointId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getCostProcess() {
        return costProcess;
    }

    public void setCostProcess(double costProcess) {
        this.costProcess = costProcess;
    }

    public int getControlPointId() {
        return controlPointId;
    }

    public void setControlPointId(int controlPointId) {
        this.controlPointId = controlPointId;
    }

    @Override
    public String toString() {
        return "PackageProcess{" + "id=" + id + ", time=" + time + ", costProcess=" + costProcess + ", controlPointId=" + controlPointId + '}';
    }
    
}
