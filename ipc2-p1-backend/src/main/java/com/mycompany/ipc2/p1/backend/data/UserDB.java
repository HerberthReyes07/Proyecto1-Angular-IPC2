/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.TypeUser;
import com.mycompany.ipc2.p1.backend.model.User;
import com.mycompany.ipc2.p1.backend.service.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author herberthreyes
 */
public class UserDB {

    private Connection connection = ConnectionDB.getConnection();

    public UserDB() {
    }

    public Optional<User> read(String username, String password, UserService us) {
        String query = "SELECT * FROM usuario WHERE username = ? AND password = ?";
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);//primer ?
            preparedStatement.setString(2, password);//segundo ?

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("nombre");
                    String dpi = resultSet.getString("dpi");
                    String sex = resultSet.getString("sexo");
                    TypeUser typeUser = us.searchTypeUser(resultSet.getInt("tipo_usuario"));
                    boolean active = resultSet.getBoolean("activo");

                    user = new User(id, name, username, password, dpi, sex, typeUser, active);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'obtenerUsuario': " + e);
        }
        return Optional.ofNullable(user);
    }

    /*private TypeUser searchTypeUser(int type) {
        switch (type) {
            case 1:
                return TypeUser.ADMINISTRATOR;
            case 2:
                return TypeUser.OPERATOR;
            case 3:
                return TypeUser.RECEPTIONIST;
            default:
                System.out.println("TIPO NO ENCONTRADO");
        }
        return null;
    }*/

}
