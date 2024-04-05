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

    public void create(ControlPoint controlPoint) {
        String query = "INSERT INTO punto_control (no_orden, nombre, capacidad_cola, tarifa_operacion_local, ruta_id, usuario_operador_id) VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, controlPoint.getOrderNo());
            preparedStatement.setString(2, controlPoint.getName());
            preparedStatement.setInt(3, controlPoint.getQueueCapacity());
            preparedStatement.setDouble(4, controlPoint.getLocalOperationFee());
            preparedStatement.setInt(5, controlPoint.getRouteId());
            preparedStatement.setInt(6, controlPoint.getOperatorId());
            preparedStatement.executeUpdate();

            System.out.println("Punto de Control creado");
        } catch (SQLException e) {
            System.out.println("Error al crear Punto de Control: " + e);
        }
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

    public void updateLocalOperationFee(double currentLocalOperationFee, double localOperationFeeToSet) {
        String query = "UPDATE punto_control SET tarifa_operacion_local = ? WHERE tarifa_operacion_local = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, localOperationFeeToSet);
            preparedStatement.setDouble(2, currentLocalOperationFee);
            preparedStatement.executeUpdate();
            System.out.println("tarifa_operacion_local actualizado");
        } catch (SQLException e) {
            System.out.println("Error al actualizar tarifa_operacion_local: " + e);
        }
    }

    public List<ControlPoint> getAllControlPoints() {
        String query = "SELECT * FROM punto_control;";
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
            System.out.println("Error al consultar 'getAllRoutes': " + e);
        }
        return controlPoints;
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

    public List<ControlPoint> getControlPointsByOperatorId(int operatorId) {
        String query = "SELECT * FROM punto_control WHERE usuario_operador_id = ?";
        List<ControlPoint> controlPoints = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, operatorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int orderNo = resultSet.getInt("no_orden");
                    String name = resultSet.getString("nombre");
                    int queueCapacity = resultSet.getInt("capacidad_cola");
                    double localOperationFee = resultSet.getDouble("tarifa_operacion_local");
                    int routeId = resultSet.getInt("ruta_id");

                    ControlPoint controlPoint = new ControlPoint(id, orderNo, name, queueCapacity, localOperationFee, routeId, operatorId);
                    controlPoints.add(controlPoint);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getControlPointsByOperatorId': " + e);
        }
        return controlPoints;
    }

    public ControlPoint getControlPointById(int id) {
        String query = "SELECT * FROM punto_control WHERE id = ?";
        ControlPoint controlPoint = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int orderNo = resultSet.getInt("no_orden");
                    String name = resultSet.getString("nombre");
                    int queueCapacity = resultSet.getInt("capacidad_cola");
                    double localOperationFee = resultSet.getDouble("tarifa_operacion_local");
                    int routeId = resultSet.getInt("ruta_id");
                    int operatorId = resultSet.getInt("usuario_operador_id");

                    controlPoint = new ControlPoint(id, orderNo, name, queueCapacity, localOperationFee, routeId, operatorId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getControlPointById': " + e);
        }
        return controlPoint;
    }

    public ControlPoint getNextControlPointByRouteId(int nextOrderNo, int routeId) {
        String query = "SELECT * FROM punto_control WHERE no_orden = ? AND ruta_id = ?";
        ControlPoint controlPoint = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, nextOrderNo);
            preparedStatement.setInt(2, routeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("nombre");
                    int queueCapacity = resultSet.getInt("capacidad_cola");
                    double localOperationFee = resultSet.getDouble("tarifa_operacion_local");
                    int operatorId = resultSet.getInt("usuario_operador_id");

                    controlPoint = new ControlPoint(id, nextOrderNo, name, queueCapacity, localOperationFee, routeId, operatorId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getNextControlPointByRouteId': " + e);
        }
        return controlPoint;
    }

    public List<ControlPoint> getControlPointsByRouteId(int routeId) {
        String query = "SELECT * FROM punto_control WHERE ruta_id = ?";
        List<ControlPoint> controlPoints = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, routeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int orderNo = resultSet.getInt("no_orden");
                    String name = resultSet.getString("nombre");
                    int queueCapacity = resultSet.getInt("capacidad_cola");
                    double localOperationFee = resultSet.getDouble("tarifa_operacion_local");
                    int operatorId = resultSet.getInt("usuario_operador_id");

                    ControlPoint controlPoint = new ControlPoint(id, orderNo, name, queueCapacity, localOperationFee, routeId, operatorId);
                    controlPoints.add(controlPoint);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getControlPointsByOperatorId': " + e);
        }
        return controlPoints;
    }

    public int getOrderNoByRouteId(int routeId) {
        String query = "SELECT no_orden FROM punto_control WHERE ruta_id = ? ORDER BY no_orden DESC LIMIT 1;";
        int orderNo = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, routeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderNo = resultSet.getInt("no_orden");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getOrderNoByRouteId': " + e);
        }

        return orderNo;
    }
}
