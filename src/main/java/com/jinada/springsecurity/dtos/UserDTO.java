package com.jinada.springsecurity.dtos;

public class UserDTO {
    private int id;
    private String username;

    public UserDTO() {
    }

    public UserDTO(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
