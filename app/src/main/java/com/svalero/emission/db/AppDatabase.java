package com.svalero.emission.db;

import  androidx.room.Database;
import androidx.room.RoomDatabase;
import com.svalero.emission.domain.ChargingPoint;
import com.svalero.emission.domain.User;
import com.svalero.emission.domain.Vehicle;

@Database(entities = {ChargingPoint.class, User.class, Vehicle.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ChargingPointDao chargingPointDao();

    public abstract UserDao userDao();

    public abstract VehicleDao vehicleDao();

}
