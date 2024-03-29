/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.Destination;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herberthreyes
 */
public class DestinationDB {

    private Connection connection = ConnectionDB.getConnection();

    public DestinationDB() {
    }

    public List<Destination> getAllDestinations() {
        String query = "SELECT * FROM destino;";
        List<Destination> destinations = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double destinationFee = resultSet.getDouble("cuota_destino");
                String name = resultSet.getString("nombre");
                String address = resultSet.getString("direccion");

                Destination destination = new Destination(id, destinationFee, name, address);
                destinations.add(destination);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'readAllDestinations': " + e);
        }
        return destinations;
    }

}
