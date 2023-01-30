package com.svalero.emission.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.emission.domain.ChargingPoint;

import java.util.List;

@Dao
public interface ChargingPointDao {

    @Query("SELECT * FROM chargingpoint")
    List<ChargingPoint> getAll();

    @Query("SELECT * FROM chargingpoint WHERE code = :code")
    ChargingPoint getByCode(String code);

    @Insert
    void insert(ChargingPoint chargingPoint);

    @Delete
    void delete(ChargingPoint chargingPoint);

    @Update
    void update(ChargingPoint chargingPoint);

}
