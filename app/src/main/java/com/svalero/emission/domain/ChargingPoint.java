package com.svalero.emission.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;

@Entity
public class ChargingPoint {

    @PrimaryKey
    @NonNull
    private String code;
    @ColumnInfo
    private String adress;
    @ColumnInfo
    private float power;
    @ColumnInfo
    private boolean isOccupied;
    @ColumnInfo
    private boolean isFree;
    @ColumnInfo
    private double latitude;
    @ColumnInfo
    private double longitude;

    public ChargingPoint() {}

    public ChargingPoint(@NonNull String code, String adress, float power, boolean isOccupied, boolean isFree) {
        this.code = code;
        this.adress = adress;
        this.power = power;
        this.isOccupied = isOccupied;
        this.isFree = isFree;
    }

    public ChargingPoint(@NonNull String code, String adress, float power, boolean isOccupied, boolean isFree, double latitude, double longitude) {
        this.code = code;
        this.adress = adress;
        this.power = power;
        this.isOccupied = isOccupied;
        this.isFree = isFree;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
