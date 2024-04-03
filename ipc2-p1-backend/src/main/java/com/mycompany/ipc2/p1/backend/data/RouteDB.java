/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.Route;
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
public class RouteDB {

    private Connection connection = ConnectionDB.getConnection();

    public RouteDB() {
    }

    public void create(Route route) {
        String query = "INSERT INTO ruta (nombre, activa, destino_id) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, route.getName());
            preparedStatement.setBoolean(2, true);
            preparedStatement.setInt(3, route.getDestinationId());
            preparedStatement.executeUpdate();

            System.out.println("Ruta creada");
        } catch (SQLException e) {
            System.out.println("Error al crear ruta: " + e);
        }
    }

    public void update(Route route) {
        String query = "UPDATE ruta SET nombre = ?, activa = ?, destino_id = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, route.getName());
            preparedStatement.setBoolean(2, route.isActive());
            preparedStatement.setInt(3, route.getDestinationId());
            preparedStatement.setInt(4, route.getId());
            preparedStatement.executeUpdate();

            System.out.println("Ruta actualizado");
        } catch (SQLException e) {
            System.out.println("Error al actualizar ruta: " + e);
        }
    }

    public Route getRouteById(int id) {
        String query = "SELECT * FROM ruta WHERE id = ?";
        Route route = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("nombre");
                    boolean active = resultSet.getBoolean("activa");
                    int destinationId = resultSet.getInt("destino_id");

                    route = new Route(id, name, active, destinationId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getRouteById': " + e);
        }
        return route;
    }

    public List<Route> getAllRoutes() {
        String query = "SELECT * FROM ruta;";
        List<Route> routes = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nombre");
                boolean active = resultSet.getBoolean("activa");
                int destinationId = resultSet.getInt("destino_id");

                Route route = new Route(id, name, active, destinationId);
                routes.add(route);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getAllRoutes': " + e);
        }
        return routes;
    }

    public List<Route> getRoutesByDestinationId(int destinationId) {
        String query = "SELECT * FROM ruta WHERE destino_id = ?";
        List<Route> routes = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, destinationId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("nombre");
                    boolean active = resultSet.getBoolean("activa");

                    Route route = new Route(id, name, active, destinationId);
                    routes.add(route);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getRoutesByDestinationId': " + e);
        }
        return routes;
    }

}
