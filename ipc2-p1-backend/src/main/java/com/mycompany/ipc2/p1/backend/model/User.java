/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2.p1.backend.model;

/**
 *
 * @author herberthreyes
 */
public class User {

    private int id;
    private String name;
    private String username;
    private String password;
    private String dpi;
    private String sex;
    private TypeUser typeUser;
    private boolean active;

    public User() {
    }

    public User(int id, String nombre, String username, String password, String dpi, String sexo, TypeUser tipoUsuario, boolean activo) {
        this.id = id;
        this.name = nombre;
        this.username = username;
        this.password = password;
        this.dpi = dpi;
        this.sex = sexo;
        this.typeUser = tipoUsuario;
        this.active = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getSexo() {
        return sex;
    }

    public void setSexo(String sexo) {
        this.sex = sexo;
    }

    public TypeUser getTipoUsuario() {
        return typeUser;
    }

    public void setTipoUsuario(TypeUser tipoUsuario) {
        this.typeUser = tipoUsuario;
    }

    public boolean isActivo() {
        return active;
    }

    public void setActivo(boolean activo) {
        this.active = activo;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", dpi=" + dpi + ", sex=" + sex + ", typeUser=" + typeUser + ", active=" + active + '}';
    }

}
