package com.svalero.emission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.Vehicle;

public class ModifyVehicleActivity extends AppCompatActivity {

    Vehicle vehicle;
    EditText et_modifyVehicleLicensePlate;
    EditText et_modifyVehicleBrand;
    EditText et_modifyVehicleModel;
    EditText et_modifyVehicleAutonomy;
    CheckBox cb_modifyHybrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_vehicle);

        Intent intent = getIntent();
        String licensePlate = intent.getStringExtra("licensePlate");
        if (licensePlate == null)
            return;

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "vehicles")
                .allowMainThreadQueries().build();
        vehicle = db.vehicleDao().getVehicleByLicensePlate(licensePlate);

        et_modifyVehicleLicensePlate = findViewById(R.id.et_modifyLicensePlate);
        et_modifyVehicleBrand = findViewById(R.id.et_modifyBrand);
        et_modifyVehicleModel = findViewById(R.id.et_modifyModel);
        et_modifyVehicleAutonomy = findViewById(R.id.et_modifyAutonomy);
        cb_modifyHybrid = findViewById(R.id.cb_modifyHybrid);

        et_modifyVehicleLicensePlate.setText(vehicle.getLicensePlate());
        et_modifyVehicleBrand.setText(vehicle.getBrand());
        et_modifyVehicleModel.setText(vehicle.getModel());
        et_modifyVehicleAutonomy.setText(String.valueOf(vehicle.getAutonomy()));
        cb_modifyHybrid.setChecked(vehicle.isHybrid());
    }

    public void modifyVehicle(View view) {

        String licensePlate = et_modifyVehicleLicensePlate.getText().toString();
        String brand = et_modifyVehicleBrand.getText().toString();
        String model = et_modifyVehicleModel.getText().toString();
        int autonomy = Integer.parseInt(et_modifyVehicleAutonomy.getText().toString());
        boolean isHybrid = cb_modifyHybrid.isChecked();


        Vehicle newVehicle = new Vehicle(licensePlate, brand, model, autonomy, isHybrid, MainActivity.currentUser);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "vehicles")
                .allowMainThreadQueries().build();
        db.vehicleDao().update(newVehicle);

        Snackbar.make(et_modifyVehicleLicensePlate, R.string.update_vehicle, BaseTransientBottomBar.LENGTH_LONG).show();
        et_modifyVehicleLicensePlate.setText("");
        et_modifyVehicleBrand.setText("");
        et_modifyVehicleModel.setText("");
        et_modifyVehicleAutonomy.setText("");
        cb_modifyHybrid.setChecked(false);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}