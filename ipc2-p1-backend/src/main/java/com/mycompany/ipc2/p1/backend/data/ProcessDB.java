/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.Process;
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
public class ProcessDB {

    private Connection connection = ConnectionDB.getConnection();

    public ProcessDB() {
    }

    public void create(Process process) {
        String query = "INSERT INTO proceso (paquete_id, punto_control_id, realizado) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, process.getPackageId());
            preparedStatement.setInt(2, process.getControlPointId());
            preparedStatement.setBoolean(3, false);
            preparedStatement.executeUpdate();

            System.out.println("Proceso creado");
        } catch (SQLException e) {
            System.out.println("Error al crear Proceso: " + e);
        }
    }

    public void update(Process process) {
        String query = "UPDATE proceso SET paquete_id = ?, punto_control_id = ?, realizado = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, process.getPackageId());
            preparedStatement.setInt(2, process.getControlPointId());
            preparedStatement.setBoolean(3, process.isDone());
            preparedStatement.setInt(4, process.getId());
            preparedStatement.executeUpdate();

            System.out.println("Proceso actualizado");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Proceso: " + e);
        }
    }

    public List<Process> getUnprocessedPackages() {
        String query = "SELECT * FROM proceso WHERE realizado = false";
        List<Process> unprocessedPackages = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int packageId = resultSet.getInt("paquete_id");
                int controlPointId = resultSet.getInt("punto_control_id");

                Process processToAdd = new Process(id, false, packageId, controlPointId);
                unprocessedPackages.add(processToAdd);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getUnprocessedPackages': " + e);
        }
        return unprocessedPackages;
    }

    public List<Process> getUnprocessedPackagesByControlPointId(int controlPointId) {
        String query = "SELECT * FROM proceso WHERE realizado = false AND punto_control_id = ?";
        List<Process> unprocessedPackages = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, controlPointId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int packageId = resultSet.getInt("paquete_id");

                    Process processToAdd = new Process(id, false, packageId, controlPointId);
                    unprocessedPackages.add(processToAdd);
                }

            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getUnprocessedPackagesByControlPointId': " + e);
        }
        return unprocessedPackages;
    }

    public Process getProcessByPackageId(int packageId, boolean done) {
        String query = "SELECT * FROM proceso WHERE paquete_id = ? AND realizado = ?";
        
        if (done) {
            query += " LIMIT 1";
        }
        
        Process processToSend = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, packageId);
            preparedStatement.setBoolean(2, done);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int controlPointId = resultSet.getInt("punto_control_id");

                    processToSend = new Process(id, done, packageId, controlPointId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getProcessByPackageId': " + e);
        }
        return processToSend;
    }

}
