/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.ControlPoint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
public class ControlPointDB {

    private Connection connection = ConnectionDB.getConnection();

    public ControlPointDB() {
    }

    public List<ControlPoint> getFirstControlPoints() {
        String query = "SELECT * FROM punto_control WHERE no_orden = 1 ORDER BY capacidad_cola DESC;";
        List<ControlPoint> controlPoints = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int orderNo = resultSet.getInt("no_orden");
                String name = resultSet.getString("nombre");
                int queueCapacity = resultSet.getInt("capacidad_cola");
                double localOperationFee = resultSet.getDouble("tarifa_operacion_local");
                int routeId = resultSet.getInt("ruta_id");
                int operatorId = resultSet.getInt("usuario_operador_id");

                ControlPoint controlPoint = new ControlPoint(id, orderNo, name, queueCapacity, localOperationFee, routeId, operatorId);
                controlPoints.add(controlPoint);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getFirstControlPoints': " + e);
        }
        return controlPoints;
    }

    public int getTotalQueueCapacityByRouteId(int routeId) {
        String query = "SELECT SUM(capacidad_cola) as Suma FROM punto_control WHERE ruta_id = ?";
        int totalQueueCapacity = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, routeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalQueueCapacity = resultSet.getInt("Suma");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getTotalQueueCapacityByRouteId': " + e);
        }

        return totalQueueCapacity;
    }

    public void update(ControlPoint controlPoint) {
        String query = "UPDATE punto_control SET no_orden = ?, nombre = ?, capacidad_cola = ?, tarifa_operacion_local = ?, ruta_id = ?, usuario_operador_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, controlPoint.getOrderNo());
            preparedStatement.setString(2, controlPoint.getName());
            preparedStatement.setInt(3, controlPoint.getQueueCapacity());
            preparedStatement.setDouble(4, controlPoint.getLocalOperationFee());
            preparedStatement.setInt(5, controlPoint.getRouteId());
            preparedStatement.setInt(6, controlPoint.getOperatorId());
            preparedStatement.setInt(7, controlPoint.getId());
            preparedStatement.executeUpdate();
            System.out.println("Punto de control actualizado");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Punto de control: " + e);
        }
    }

}
