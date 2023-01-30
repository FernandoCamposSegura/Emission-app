package com.svalero.emission.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String username;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String password;
    @ColumnInfo
    private String adress;
    @ColumnInfo
    private String pathImg = "";

    public User() {}

    public User(String name, String username, String password, String adress, String pathImg) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.adress = adress;
    }

    public User(String name, @NonNull String username, String password, String adress) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.adress = adress;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }
}
