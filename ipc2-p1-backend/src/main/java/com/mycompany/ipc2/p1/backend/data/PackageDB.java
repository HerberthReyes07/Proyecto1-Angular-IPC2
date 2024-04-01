/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import java.sql.Connection;
import com.mycompany.ipc2.p1.backend.model.Package;
import com.mycompany.ipc2.p1.backend.model.PackageStatus;
import com.mycompany.ipc2.p1.backend.utils.GeneralUtils;
import java.sql.Date;
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
public class PackageDB {

    private Connection connection = ConnectionDB.getConnection();
    private GeneralUtils gu = new GeneralUtils();

    public PackageDB() {
    }

    public void create(Package packageSent) {
        String query = "INSERT INTO paquete (peso, costo_envio, estado, no_factura, fecha_ingreso, cliente_id, destino_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, packageSent.getWeight());
            preparedStatement.setDouble(2, packageSent.getShippingCost());
            preparedStatement.setInt(3, gu.getStatusPackageCode(packageSent.getStatus()));
            preparedStatement.setInt(4, packageSent.getInvoiceNo());
            preparedStatement.setDate(5, Date.valueOf(packageSent.getEntryDate()));
            preparedStatement.setInt(6, packageSent.getCustomerId());
            preparedStatement.setInt(7, packageSent.getDestinationId());
            preparedStatement.executeUpdate();

            System.out.println("Paquete creado");
        } catch (SQLException e) {
            System.out.println("Error al crear paquete: " + e);
        }
    }

    public void update(Package packageSent) {
        String query = "UPDATE paquete SET peso = ?, costo_envio = ?, estado = ?, no_factura = ?, fecha_ingreso = ?, cliente_id = ?, destino_id = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, packageSent.getWeight());
            preparedStatement.setDouble(2, packageSent.getShippingCost());
            preparedStatement.setInt(3, gu.getStatusPackageCode(packageSent.getStatus()));
            preparedStatement.setInt(4, packageSent.getInvoiceNo());
            preparedStatement.setDate(5, Date.valueOf(packageSent.getEntryDate()));
            preparedStatement.setInt(6, packageSent.getCustomerId());
            preparedStatement.setInt(7, packageSent.getDestinationId());
            preparedStatement.setInt(8, packageSent.getId());
            preparedStatement.executeUpdate();

            System.out.println("Paquete actualizado");
        } catch (SQLException e) {
            System.out.println("Error al actualizar paquete: " + e);
        }
    }

    public Package getPackageById(int id) {
        String query = "SELECT * FROM paquete WHERE id = ?";
        Package packageToSend = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double weight = resultSet.getDouble("peso");
                    double shippingCost = resultSet.getDouble("costo_envio");
                    int status = resultSet.getInt("estado");
                    int invoiceNo = resultSet.getInt("no_factura");
                    String entryDate = resultSet.getDate("fecha_entrada").toString();
                    int customerId = resultSet.getInt("cliente_id");
                    int destinationId = resultSet.getInt("destino_id");
                    packageToSend = new Package(id, customerId, destinationId, weight, shippingCost, gu.getPackageStatus(status), invoiceNo, entryDate);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getPackageById': " + e);
        }
        return packageToSend;
    }

    public int getPackageCreatedId() {
        String query = "SELECT id FROM paquete ORDER BY id DESC LIMIT 1;";
        int id = -1;
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(query)) {
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
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(query)) {
                if (resultSet.next()) {
                    lastInvoiceNo = resultSet.getInt("no_factura");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getLastInvoiceNo': " + e);
        }
        return lastInvoiceNo;
    }

    public List<Package> getAllPackagesOnStandby() {
        String query = "SELECT * FROM paquete WHERE estado = 3;";
        List<Package> packages = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double weight = resultSet.getDouble("peso");
                double shippingCost = resultSet.getDouble("costo_envio");
                int invoiceNo = resultSet.getInt("no_factura");
                String entryDate = resultSet.getDate("fecha_entrada").toString();
                int customerId = resultSet.getInt("cliente_id");
                int destinationId = resultSet.getInt("destino_id");

                Package packageToAdd = new Package(id, customerId, destinationId, weight, shippingCost, PackageStatus.EN_ESPERA_RETIRO, invoiceNo, entryDate);
                packages.add(packageToAdd);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getPackagesOnStandby': " + e);
        }
        return packages;
    }

    public List<Package> filterPackagesOnStandby(String filter) {/*'%?%'*/
        String query = "SELECT p.* FROM paquete p JOIN cliente c ON p.cliente_id = c.id WHERE (c.nit LIKE ? OR p.no_factura LIKE ?) AND estado = 3;";
        List<Package> packages = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + filter + "%");
            preparedStatement.setString(2, "%" + filter + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double weight = resultSet.getDouble("peso");
                    double shippingCost = resultSet.getDouble("costo_envio");
                    int invoiceNo = resultSet.getInt("no_factura");
                    String entryDate = resultSet.getDate("fecha_entrada").toString();
                    int customerId = resultSet.getInt("cliente_id");
                    int destinationId = resultSet.getInt("destino_id");

                    Package packageToAdd = new Package(id, customerId, destinationId, weight, shippingCost, PackageStatus.EN_ESPERA_RETIRO, invoiceNo, entryDate);
                    packages.add(packageToAdd);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'filterPackagesOnStandby': " + e);
        }
        return packages;
    }

}
