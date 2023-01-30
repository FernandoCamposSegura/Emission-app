package com.svalero.emission;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.ChargingPoint;
import com.svalero.emission.domain.Vehicle;

public class VehicleDetailsActivity extends AppCompatActivity {

    private String licensePlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        Intent intent = getIntent();
        licensePlate = intent.getStringExtra("licensePlate");
        if (licensePlate == null)
            return;

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "vehicles")
                .allowMainThreadQueries().build();
        Vehicle vehicle = db.vehicleDao().getVehicleByLicensePlate(licensePlate);

        fillData(vehicle);
    }

    private void fillData(Vehicle vehicle) {
        TextView tv_vehicleDetailLicensePlate = findViewById(R.id.tv_vehicleDetailLicensePlate);
        TextView tv_vehicleDetailBrand = findViewById(R.id.tv_vehicleDetailBrand);
        TextView tv_vehicleDetailModel = findViewById(R.id.tv_vehicleDetailModel);
        TextView tv_vehicleDetailAutonomy = findViewById(R.id.tv_vehicleDetailAutonomy);
        CheckBox cb_vehicleDetailHybrid = findViewById(R.id.cb_vehicleDetailHybrid);

        tv_vehicleDetailLicensePlate.setText(vehicle.getLicensePlate());
        tv_vehicleDetailBrand.setText(vehicle.getBrand());
        tv_vehicleDetailModel.setText(vehicle.getModel());
        tv_vehicleDetailAutonomy.setText(String.valueOf(vehicle.getAutonomy()));
        cb_vehicleDetailHybrid.setChecked(vehicle.isHybrid());

    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void updateVehicle(View view) {
        Intent intent = getIntent();
        String licensePlate = intent.getStringExtra("licensePlate");
        if (licensePlate == null)
            return;

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "vehicles")
                .allowMainThreadQueries().build();
        Vehicle vehicle = db.vehicleDao().getVehicleByLicensePlate(licensePlate);

        Intent newIntent = new Intent(this, ModifyVehicleActivity.class);
        newIntent.putExtra("licensePlate", vehicle.getLicensePlate());
        startActivity(newIntent);
    }

    public void deleteVehicle(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_message)
                .setTitle(R.string.remove_vehicle_title)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "vehicles")
                            .allowMainThreadQueries().build();

                    Vehicle vehicle = db.vehicleDao().getVehicleByLicensePlate(licensePlate);
                    db.vehicleDao().delete(vehicle);
                    onBackPressed();
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}