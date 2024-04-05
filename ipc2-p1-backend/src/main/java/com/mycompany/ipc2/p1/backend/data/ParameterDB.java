/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.Parameter;
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
public class ParameterDB {

    private Connection connection = ConnectionDB.getConnection();

    public ParameterDB() {
    }

    public void create(Parameter parameter) {
        String query = "INSERT INTO parametro (tarifa_operacion_global, precio_libra) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, parameter.getGlobalOperationFee());
            preparedStatement.setDouble(2, parameter.getPricePerPound());
            preparedStatement.executeUpdate();

            System.out.println("Parametro creado");
        } catch (SQLException e) {
            System.out.println("Error al crear Parametro: " + e);
        }
    }

    public Parameter getCurrentParameter() {
        String query = "SELECT * FROM parametro ORDER BY id DESC LIMIT 1;";
        Parameter parameter = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double globalOperationFee = resultSet.getDouble("tarifa_operacion_global");
                    double pricePerPound = resultSet.getDouble("precio_libra");

                    parameter = new Parameter(id, globalOperationFee, pricePerPound);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getCurrentParameter': " + e);
        }
        return parameter;
    }

    public List<Parameter> getAllParameters() {
        String query = "SELECT * FROM parametro;";
        List<Parameter> parameters = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double globalOperationFee = resultSet.getDouble("tarifa_operacion_global");
                double pricePerPound = resultSet.getDouble("precio_libra");

                Parameter parameter = new Parameter(id, globalOperationFee, pricePerPound);

                parameters.add(parameter);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getAllParameters': " + e);
        }
        return parameters;
    }

    public Parameter getParameterById(int id) {
        String query = "SELECT * FROM parametro WHERE id = ?";
        Parameter parameter = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double globalOperationFee = resultSet.getDouble("tarifa_operacion_global");
                    double pricePerPound = resultSet.getDouble("precio_libra");

                    parameter = new Parameter(id, globalOperationFee, pricePerPound);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getParameterById': " + e);
        }
        return parameter;
    }

}
