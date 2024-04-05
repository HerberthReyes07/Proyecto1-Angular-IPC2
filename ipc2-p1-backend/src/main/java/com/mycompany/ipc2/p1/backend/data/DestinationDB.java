/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.Destination;
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
public class DestinationDB {

    private Connection connection = ConnectionDB.getConnection();

    public DestinationDB() {
    }
    
    public void create(Destination destination) {
        String query = "INSERT INTO destino (cuota_destino, nombre, direccion) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, destination.getDestinationFee());
            preparedStatement.setString(2, destination.getName());
            preparedStatement.setString(3, destination.getAddress());
            preparedStatement.executeUpdate();

            System.out.println("Destino creado");
        } catch (SQLException e) {
            System.out.println("Error al crear Destino: " + e);
        }
    }
    
    public void update(Destination destination) {
        String query = "UPDATE destino SET cuota_destino = ?, nombre = ?, direccion = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, destination.getDestinationFee());
            preparedStatement.setString(2, destination.getName());
            preparedStatement.setString(3, destination.getAddress());
            preparedStatement.setInt(4, destination.getId());
            preparedStatement.executeUpdate();

            System.out.println("Destino actualizado");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Destino: " + e);
        }
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

    public Destination getDestinationById(int id) {
        String query = "SELECT * FROM destino WHERE id = ?";
        Destination destination = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double destinationFee = resultSet.getDouble("cuota_destino");
                    String name = resultSet.getString("nombre");
                    String address = resultSet.getString("direccion");

                    destination = new Destination(id, destinationFee, name, address);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getDestinationById': " + e);
        }
        return destination;
    }

}
