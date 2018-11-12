package com.yafetsutanto.tempator;

public class User {

    private String name;
    private String email;
    private String password;
    private String telp;
    private String role;

    public User(String name, String email, String password, String telp, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.telp = telp;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
