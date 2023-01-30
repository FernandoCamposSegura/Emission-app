package com.svalero.emission.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;

@Entity
public class Vehicle {

    @PrimaryKey
    @NonNull
    private String licensePlate;
    @ColumnInfo
    private String brand;
    @ColumnInfo
    private String model;
    @ColumnInfo
    private int autonomy;
    @ColumnInfo
    private boolean isHybrid;
    @ColumnInfo
    private String userName;

    public Vehicle() {
    }

    public Vehicle(@NonNull String licensePlate, String brand, String model, int autonomy, boolean isHybrid, String userName) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.autonomy = autonomy;
        this.isHybrid = isHybrid;
        this.userName = userName;
    }

    public Vehicle(String brand, String model, int autonomy, boolean isHybrid, String userName) {
        this.brand = brand;
        this.model = model;
        this.autonomy = autonomy;
        this.isHybrid = isHybrid;
        this.userName = userName;
    }

    @NonNull
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(@NonNull String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(int autonomy) {
        this.autonomy = autonomy;
    }

    public boolean isHybrid() {
        return isHybrid;
    }

    public void setHybrid(boolean hybrid) {
        isHybrid = hybrid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
