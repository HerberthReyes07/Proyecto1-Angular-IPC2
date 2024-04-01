/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;


import com.mycompany.ipc2.p1.backend.model.Process;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
