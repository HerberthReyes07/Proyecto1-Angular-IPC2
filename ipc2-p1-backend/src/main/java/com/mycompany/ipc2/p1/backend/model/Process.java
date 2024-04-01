/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class Process {
    
    private boolean done;
    private int packageId;
    private int controlPointId;

    public Process() {
    }

    public Process(boolean done, int packageId, int controlPointId) {
        this.done = done;
        this.packageId = packageId;
        this.controlPointId = controlPointId;
    }

    public Process(int packageId, int controlPointId) {
        this.packageId = packageId;
        this.controlPointId = controlPointId;
    }
    
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getControlPointId() {
        return controlPointId;
    }

    public void setControlPointId(int controlPointId) {
        this.controlPointId = controlPointId;
    }
    
}
