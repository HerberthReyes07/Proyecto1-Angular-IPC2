/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class LocationReport {
    
    private int packageId;
    private int controlPointId;
    private int totalTime;

    public LocationReport() {
    }

    public LocationReport(int packageId, int controlPointId, int totalTime) {
        this.packageId = packageId;
        this.controlPointId = controlPointId;
        this.totalTime = totalTime;
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

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "LocationReport{" + "packageId=" + packageId + ", controlPointId=" + controlPointId + ", totalTime=" + totalTime + '}';
    }
    
}
