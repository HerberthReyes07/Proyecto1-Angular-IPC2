/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.ProcessDetail;
import java.sql.Connection;
import java.sql.Date;
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
public class ProcessDetailDB {

    private Connection connection = ConnectionDB.getConnection();

    public ProcessDetailDB() {
    }

    public void create(ProcessDetail processDetail) {
        String query = "INSERT INTO detalle_proceso (tiempo, costo_proceso, fecha_proceso, proceso_id) VALUES (?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, processDetail.getTime());
            preparedStatement.setDouble(2, processDetail.getCostProcess());
            preparedStatement.setDate(3, Date.valueOf(processDetail.getProcessDate()));
            preparedStatement.setInt(4, processDetail.getProcessId());
            preparedStatement.executeUpdate();

            System.out.println("Detalle Proceso creado");
        } catch (SQLException e) {
            System.out.println("Error al crear Detalle Proceso: " + e);
        }
    }

    public List<ProcessDetail> getAllProcessDetails() {
        String query = "SELECT * FROM detalle_proceso;";
        List<ProcessDetail> processDetails = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int tiempo = resultSet.getInt("tiempo");
                double costProcess = resultSet.getDouble("costo_proceso");
                String processDate = resultSet.getDate("fecha_proceso").toString();
                int processId = resultSet.getInt("proceso_id");

                ProcessDetail processDetail = new ProcessDetail(id, tiempo, costProcess, processDate, processId);
                processDetails.add(processDetail);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getAllProcessDetails': " + e);
        }
        return processDetails;
    }

    public List<ProcessDetail> getAllProcessDetailsByDateRange(String initialDate, String finalDate) {
        String query = "SELECT * FROM detalle_proceso WHERE fecha_proceso BETWEEN ? AND ?;";
        List<ProcessDetail> processDetails = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, initialDate);
            preparedStatement.setString(2, finalDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int tiempo = resultSet.getInt("tiempo");
                    double costProcess = resultSet.getDouble("costo_proceso");
                    String processDate = resultSet.getDate("fecha_proceso").toString();
                    int processId = resultSet.getInt("proceso_id");

                    ProcessDetail processDetail = new ProcessDetail(id, tiempo, costProcess, processDate, processId);
                    processDetails.add(processDetail);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar 'getAllProcessDetails': " + e);
        }
        return processDetails;
    }

    public double getCostByProcessId(int processId) {
        String query = "SELECT costo_proceso FROM detalle_proceso WHERE proceso_id = ?;";
        double cost = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, processId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    cost = resultSet.getDouble("costo_proceso");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getCostByProcessId': " + e);
        }

        return cost;
    }

    public int getTotalTimeByPackageId(int packageId) {
        String query = "SELECT SUM(dp.tiempo) AS tiempo_total FROM detalle_proceso dp JOIN proceso p ON dp.proceso_id = p.id WHERE p.paquete_id = ?;";
        int totalTime = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, packageId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalTime = resultSet.getInt("tiempo_total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getTotalTimeByPackageId': " + e);
        }

        return totalTime;

    }

}
