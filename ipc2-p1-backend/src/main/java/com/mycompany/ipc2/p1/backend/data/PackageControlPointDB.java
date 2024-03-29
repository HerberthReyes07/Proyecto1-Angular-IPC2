/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author herberthreyes
 */
public class PackageControlPointDB {

    private Connection connection = ConnectionDB.getConnection();

    public PackageControlPointDB() {
    }

    public void create(int packageId, int controlPointId) {
        String query = "INSERT INTO paquete_punto_control (paquete_id, punto_control_id) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, packageId);
            preparedStatement.setInt(2, controlPointId);
            preparedStatement.executeUpdate();
            
            System.out.println("Paquete-PuntoControl creado");
        } catch (SQLException e) {
            System.out.println("Error al crear Paquete-PuntoControl: " + e);
        }
    }

}
