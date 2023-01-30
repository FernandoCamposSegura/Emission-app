package com.svalero.emission.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.emission.domain.ChargingPoint;
import com.svalero.emission.domain.User;
import com.svalero.emission.domain.Vehicle;

import java.util.List;

@Dao
public interface VehicleDao {

    @Query("SELECT * FROM vehicle")
    List<Vehicle> getAll();

    @Query("SELECT * FROM vehicle WHERE licensePlate = :licensePlate")
    Vehicle getVehicleByLicensePlate(String licensePlate);

    @Query("SELECT * FROM vehicle WHERE userName = :username")
    List<Vehicle> getVehiclesByUserName(String username);

    @Insert
    void insert(Vehicle vehicle);

    @Delete
    void delete(Vehicle vehicle);

    @Update
    void update(Vehicle vehicle);
}
