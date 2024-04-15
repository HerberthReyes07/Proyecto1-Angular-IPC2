/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.data;

import com.mycompany.ipc2.p1.backend.model.TypeUser;
import com.mycompany.ipc2.p1.backend.model.User;
import com.mycompany.ipc2.p1.backend.utils.GeneralUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author herberthreyes
 */
public class UserDB {

    private Connection connection = ConnectionDB.getConnection();
    private GeneralUtils gu = new GeneralUtils();

    public UserDB() {
    }

    public void create(User user) {
        String query = "INSERT INTO usuario (nombre, username, password, dpi, sexo, tipo_usuario, activo) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getDpi());
            preparedStatement.setString(5, user.getSex());
            preparedStatement.setInt(6, gu.getCodeTypeUser(user.getTypeUser()));
            preparedStatement.setBoolean(7, true);
            preparedStatement.executeUpdate();

            System.out.println("Usuario creado");
        } catch (SQLException e) {
            System.out.println("Error al crear Usuario: " + e);
        }
    }

    public void update(User user) {
        String query = "UPDATE usuario SET nombre = ?, username = ?, password = ?, dpi = ?, sexo = ?, tipo_usuario = ?, activo = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getDpi());
            preparedStatement.setString(5, user.getSex());
            preparedStatement.setInt(6, gu.getCodeTypeUser(user.getTypeUser()));
            preparedStatement.setBoolean(7, user.isActive());
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();
            System.out.println("Usuario actualizado");
        } catch (SQLException e) {
            System.out.println("Error al actualizar Usuario: " + e);
        }
    }

    public Optional<User> getUserByLoginCredentials(String username, String password) {
        String query = "SELECT * FROM usuario WHERE username = ? AND password = ? AND activo = true;";
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
                    TypeUser typeUser = gu.getTypeUser(resultSet.getInt("tipo_usuario"));
                    boolean active = resultSet.getBoolean("activo");

                    user = new User(id, name, username, password, dpi, sex, typeUser, active);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'obtenerUsuario': " + e);
        }
        return Optional.ofNullable(user);
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM usuario;";
        List<User> users = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nombre");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String dpi = resultSet.getString("dpi");
                String sex = resultSet.getString("sexo");
                TypeUser typeUser = gu.getTypeUser(resultSet.getInt("tipo_usuario"));
                boolean active = resultSet.getBoolean("activo");

                User user = new User(id, name, username, password, dpi, sex, typeUser, active);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getAllUsers': " + e);
        }
        return users;
    }

    public List<User> getUsersByCodeTypeUser(int codeTypeUser) {
        String query = "SELECT * FROM usuario WHERE tipo_usuario = ?;";
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, codeTypeUser);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("nombre");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String dpi = resultSet.getString("dpi");
                    String sex = resultSet.getString("sexo");
                    TypeUser typeUser = gu.getTypeUser(codeTypeUser);
                    boolean active = resultSet.getBoolean("activo");

                    User user = new User(id, name, username, password, dpi, sex, typeUser, active);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getUsersByCodeTypeUser': " + e);
        }
        return users;
    }
    
    public List<User> getUsersByStatus(boolean active) {
        String query = "SELECT * FROM usuario WHERE activo = ?;";
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, active);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("nombre");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String dpi = resultSet.getString("dpi");
                    String sex = resultSet.getString("sexo");
                    TypeUser typeUser = gu.getTypeUser(resultSet.getInt("tipo_usuario"));

                    User user = new User(id, name, username, password, dpi, sex, typeUser, active);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getUsersByStatus': " + e);
        }
        return users;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM usuario WHERE id = ?";
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("nombre");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String dpi = resultSet.getString("dpi");
                    String sex = resultSet.getString("sexo");
                    TypeUser typeUser = gu.getTypeUser(resultSet.getInt("tipo_usuario"));
                    boolean active = resultSet.getBoolean("activo");

                    user = new User(id, name, username, password, dpi, sex, typeUser, active);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar 'getUserById': " + e);
        }
        return user;
    }
}
