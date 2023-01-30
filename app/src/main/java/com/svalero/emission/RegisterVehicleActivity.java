package com.svalero.emission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.Vehicle;

public class RegisterVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);
    }

    public void registerVehicle(View view) {
        EditText et_licensePlate = findViewById(R.id.et_registerLicensePlateVehicle);
        EditText et_brand = findViewById(R.id.et_registerVehicleBrand);
        EditText et_model = findViewById(R.id.et_registerVehicleModel);
        EditText et_autonomy = findViewById(R.id.et_registerVehicleAutonomy);
        CheckBox cb_hybrid = findViewById(R.id.cb_registerVehicleHybrid);

        String licensePlate = et_licensePlate.getText().toString();
        String brand = et_brand.getText().toString();
        String model = et_model.getText().toString();
        String autonomy = et_autonomy.getText().toString();
        boolean hybrid = cb_hybrid.isChecked();

        Vehicle vehicle = new Vehicle(licensePlate, brand, model, Integer.parseInt(autonomy), hybrid, MainActivity.currentUser);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "vehicles")
                .allowMainThreadQueries().build();
        db.vehicleDao().insert(vehicle);

        Snackbar.make(et_licensePlate, R.string.add_vehicle, BaseTransientBottomBar.LENGTH_LONG).show();
        et_licensePlate.setText("");
        et_brand.setText("");
        et_model.setText("");
        et_autonomy.setText("");
        cb_hybrid.setChecked(false);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}