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
import com.svalero.emission.domain.ChargingPoint;

public class ModifyChargingPointActivity extends AppCompatActivity {

    private ChargingPoint chargingPoint;
    EditText etCode;
    EditText etAdress;
    EditText etPower;
    CheckBox cbFree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_charging_point);

        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        if (code == null)
            return;

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "charging_points")
                .allowMainThreadQueries().build();
        chargingPoint = db.chargingPointDao().getByCode(code);

        etCode = findViewById(R.id.et_modifyChargingCode);
        etAdress = findViewById(R.id.et_modifyChargingAdress);
        etPower = findViewById(R.id.et_modifyChargingPower);
        cbFree = findViewById(R.id.cb_modifyChargingFree);

        etCode.setText(chargingPoint.getCode());
        etAdress.setText(chargingPoint.getAdress());
        etPower.setText(String.valueOf(chargingPoint.getPower()));
        cbFree.setChecked(chargingPoint.isFree());
    }

    public void modifyChargingPoint(View view) {

        String code = etCode.getText().toString();
        String adress = etAdress.getText().toString();
        float power = Float.parseFloat(etPower.getText().toString());
        boolean free = cbFree.isChecked();


        ChargingPoint newChargingPoint = new ChargingPoint(code, adress, power, false, free);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "charging_points")
                .allowMainThreadQueries().build();
        db.chargingPointDao().update(newChargingPoint);

        Snackbar.make(etCode, R.string.charging_point_update, BaseTransientBottomBar.LENGTH_LONG).show();
        etCode.setText("");
        etAdress.setText("");
        etPower.setText("");
        cbFree.setChecked(false);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}