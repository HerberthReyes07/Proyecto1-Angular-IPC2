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

/**
 *
 * @author herberthreyes
 */
public class ParameterDB {

    private Connection connection = ConnectionDB.getConnection();

    public ParameterDB() {
    }

    public Parameter getParameters() {
        String query = "SELECT * FROM parametro WHERE id = 1;";
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
            System.out.println("Error al consultar 'getParameter': " + e);
        }
        return parameter;
    }

}
