/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author herberthreyes
 */
public class ConnectionDB {

    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/bd_paqueteria";
    private static final String USER = "herberth";
    private static final String PASSWORD = "hjrpDB07";
    private static Connection connection = null;

    private ConnectionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
            //System.out.println("Conexión exitosa");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al registrar el driver de MySQL: " + e);
        }
    }

    public static Connection getConnection() {
        if(connection == null) {
            new ConnectionDB();
            System.out.println("Conexión exitosa");
        }
        return connection;
    }

    /*public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.out.println("No se pudo cerrar la conexión");
                e.printStackTrace();
            }
        }
    }*/
}
