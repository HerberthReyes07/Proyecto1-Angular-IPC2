/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.ProcessDetail;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author herberthreyes
 */
public class ProcessDetailDB {

    private Connection connection = ConnectionDB.getConnection();

    public ProcessDetailDB() {
    }

    public void create(ProcessDetail processDetail) {
        String query = "INSERT INTO detalle_proceso (tiempo, costo_proceso, fecha_proceso, proceso_id) VALUES (?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, processDetail.getTime());
            preparedStatement.setDouble(2, processDetail.getCostProcess());
            preparedStatement.setDate(3, Date.valueOf(processDetail.getProcessDate()));
            preparedStatement.setInt(4, processDetail.getProcessId());
            preparedStatement.executeUpdate();

            System.out.println("Detalle Proceso creado");
        } catch (SQLException e) {
            System.out.println("Error al crear Detalle Proceso: " + e);
        }
    }

    public int getTotalTimeByPackageId(int packageId) {
        String query = "SELECT SUM(dp.tiempo) AS tiempo_total FROM detalle_proceso dp JOIN proceso p ON dp.proceso_id = p.id WHERE p.paquete_id = ?;";
        int totalTime = 0;
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, packageId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalTime = resultSet.getInt("tiempo_total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getTotalTimeByPackageId': " + e);
        }
        
        return totalTime;
        
    }

}
