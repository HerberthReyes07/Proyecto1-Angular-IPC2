/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class ProcessDetail {

    private int id;
    private int time;
    private double costProcess;
    private String processDate;
    private int processId;

    public ProcessDetail() {
    }

    public ProcessDetail(int id, int time, double costProcess, String processDate, int processId) {
        this.id = id;
        this.time = time;
        this.costProcess = costProcess;
        this.processDate = processDate;
        this.processId = processId;
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

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    @Override
    public String toString() {
        return "ProcessDetail{" + "id=" + id + ", time=" + time + ", costProcess=" + costProcess + ", processDate=" + processDate + ", processId=" + processId + '}';
    }

}
