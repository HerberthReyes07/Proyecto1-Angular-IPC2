/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class ControlPoint {

    private int id;
    private int orderNo;
    private String name;
    private int queueCapacity;
    private double localOperationFee;
    private int routeId;
    private int operatorId;

    public ControlPoint() {
    }

    public ControlPoint(int id, int orderNo, String name, int queueCapacity, double localOperationFee, int routeId, int operatorId) {
        this.id = id;
        this.orderNo = orderNo;
        this.name = name;
        this.queueCapacity = queueCapacity;
        this.localOperationFee = localOperationFee;
        this.routeId = routeId;
        this.operatorId = operatorId;
    }

    public ControlPoint(int orderNo) {
        this.orderNo = orderNo;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public double getLocalOperationFee() {
        return localOperationFee;
    }

    public void setLocalOperationFee(double localOperationFee) {
        this.localOperationFee = localOperationFee;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "ControlPoint{" + "id=" + id + ", orderNo=" + orderNo + ", name=" + name + ", queueCapacity=" + queueCapacity + ", localOperationFee=" + localOperationFee + ", routeId=" + routeId + ", operatorId=" + operatorId + '}';
    }

}
