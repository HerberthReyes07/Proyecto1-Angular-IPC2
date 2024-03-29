/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author herberthreyes
 */
public class CustomerDB {

    private Connection connection = ConnectionDB.getConnection();

    public CustomerDB() {
    }

    public Optional<Customer> getCustomerByNit(String nit) {
        String query = "SELECT * FROM cliente WHERE nit = ?";
        Customer customer = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nit);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("nombre");
                    String sex = resultSet.getString("sexo");
                    customer = new Customer(id, name, nit, sex);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getCustomerByNit': " + e);
        }
        return Optional.ofNullable(customer);
    }
    
    public void create(Customer customer) {
        String query = "INSERT INTO cliente (nombre, nit, sexo) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getNit());
            preparedStatement.setString(3, customer.getSex());
            preparedStatement.executeUpdate();
            
            System.out.println("cliente creado");
        } catch (SQLException e) {
            System.out.println("Error al crear cliente: " + e);
        }
    }
}
