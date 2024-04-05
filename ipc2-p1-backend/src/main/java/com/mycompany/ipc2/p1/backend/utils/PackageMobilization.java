/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.utils;

import com.mycompany.ipc2.p1.backend.data.ControlPointDB;
import com.mycompany.ipc2.p1.backend.data.RouteDB;
import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import com.mycompany.ipc2.p1.backend.model.Route;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
public class PackageMobilization {

    private final RouteDB routeDB;
    private final ControlPointDB controlPointDB;

    public PackageMobilization() {
        this.routeDB = new RouteDB();
        this.controlPointDB = new ControlPointDB();
    }

    public List<Route> getRoutesByDestinationId(int destinationId) {
        return routeDB.getRoutesByDestinationId(destinationId);
    }

    public ControlPoint getFirstControlPoint(List<Route> routes) {

        List<ControlPoint> firstControlPoints = controlPointDB.getFirstControlPoints();//Punto de control id: 1-3
        System.out.println("2-FCP : " + firstControlPoints.toString());
        List<ControlPoint> firstValidControlPoints = new ArrayList<>();

        for (int i = 0; i < routes.size(); i++) {//Ruta id: 1-2
            for (int j = 0; j < firstControlPoints.size(); j++) {//Punto de control id: 1-3
                if (routes.get(i).getId() == firstControlPoints.get(j).getRouteId()) {
                    if (firstControlPoints.get(j).getQueueCapacity() > 0) {
                        firstValidControlPoints.add(firstControlPoints.get(j));//Punto de control id: 1
                    }
                    break;
                }
            }
        }

        if (firstValidControlPoints.isEmpty()) {
            return null;
        }

        Collections.sort(firstValidControlPoints, new Comparator<ControlPoint>() {
            @Override
            public int compare(ControlPoint cp1, ControlPoint cp2) {
                return Integer.compare(cp2.getQueueCapacity(), cp1.getQueueCapacity());
            }
        });

        System.out.println("3-FVCP: " + firstValidControlPoints.toString()); // ordenado

        List<ControlPoint> queueCapacityRepeated = new ArrayList<>();
        ControlPoint auxControlPoint = firstValidControlPoints.get(0);
        queueCapacityRepeated.add(auxControlPoint);

        for (int i = 1; i < firstValidControlPoints.size(); i++) {
            if (firstValidControlPoints.get(i).getQueueCapacity() < auxControlPoint.getQueueCapacity()) {
                break;
            } else if (firstValidControlPoints.get(i).getQueueCapacity() == auxControlPoint.getQueueCapacity()) {
                queueCapacityRepeated.add(firstValidControlPoints.get(i));
                System.out.println("PCR: " + firstValidControlPoints.get(i).toString());
            }
        }

        ControlPoint controlPoint = auxControlPoint;
        System.out.println("4-QCR: " + queueCapacityRepeated.toString());

        if (queueCapacityRepeated.size() > 1) {
            int totalQueueCapacity = controlPointDB.getTotalQueueCapacityByRouteId(controlPoint.getRouteId());
            for (int i = 1; i < queueCapacityRepeated.size(); i++) {
                int aux = controlPointDB.getTotalQueueCapacityByRouteId(queueCapacityRepeated.get(i).getRouteId());
                System.out.println("5-TQC = " + totalQueueCapacity);
                System.out.println("5-aux = " + aux);
                if (aux > totalQueueCapacity) {
                    totalQueueCapacity = aux;
                    controlPoint = queueCapacityRepeated.get(i);
                }
            }
        }

        return controlPoint;
    }

}
