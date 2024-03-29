/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import java.sql.Connection;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.utils.GeneralUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author herberthreyes
 */
public class PackageDB {

    private Connection connection = ConnectionDB.getConnection();
    private GeneralUtils gu = new GeneralUtils();

    public PackageDB() {
    }

    public void create(Package packageSent) {
        String query = "INSERT INTO paquete (peso, costo_envio, estado, no_factura, cliente_id, destino_id) VALUES (?, ?, ?, ?, ?, ?);";
        //Package packageS = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, packageSent.getWeight());
            preparedStatement.setDouble(2, packageSent.getShippingCost());
            preparedStatement.setInt(3, gu.getStatusPackageCode(packageSent.getStatus()));
            preparedStatement.setInt(4, packageSent.getInvoiceNo());
            preparedStatement.setInt(5, packageSent.getCustomerId());
            preparedStatement.setInt(6, packageSent.getDestinationId());
            preparedStatement.executeUpdate();
            
            System.out.println("Paquete creado");
            //return packageSent;
        } catch (SQLException e) {
            System.out.println("Error al crear paquete: " + e);
        }
        //return packageSent;
    }

    public int getPackageCreatedId(){
        String query = "SELECT id FROM paquete ORDER BY id DESC LIMIT 1;";
        int id = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getPackageCreatedId': " + e);
        }
        return id;
    }
    
    public int getLastInvoiceNo() {
        String query = "SELECT no_factura FROM paquete ORDER BY no_factura DESC LIMIT 1;";
        int lastInvoiceNo = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lastInvoiceNo = resultSet.getInt("no_factura");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getLastInvoiceNo': " + e);
        }
        return lastInvoiceNo;
    }

}
