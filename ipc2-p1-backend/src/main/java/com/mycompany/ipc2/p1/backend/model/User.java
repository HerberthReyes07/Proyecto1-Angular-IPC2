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

    public User(int id, String name, String username, String password, String dpi, String sex, TypeUser typeUser, boolean active) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.dpi = dpi;
        this.sex = sex;
        this.typeUser = typeUser;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", dpi=" + dpi + ", sex=" + sex + ", typeUser=" + typeUser + ", active=" + active + '}';
    }

}
