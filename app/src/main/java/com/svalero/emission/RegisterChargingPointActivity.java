package com.svalero.emission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.ChargingPoint;

public class RegisterChargingPointActivity extends AppCompatActivity {

    private MapView chargingPointMap;
    private Point point;
    private PointAnnotationManager pointAnnotationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_charging_point);

        chargingPointMap = findViewById(R.id.chargingPointMap);

        GesturesPlugin gesturesPlugin = GesturesUtils.getGestures(chargingPointMap);
        gesturesPlugin.addOnMapClickListener(point -> {
            removeAllMarkers();
            this.point = point;
            addMarker(point);
            return true;
        });

        initializePointManager();
    }

    public void addChargingPoint(View view) {
        EditText etCode = findViewById(R.id.et_registerChargingCode);
        EditText etAdress = findViewById(R.id.et_registerChargingAdress);
        EditText etPower = findViewById(R.id.et_registerChargingPower);
        CheckBox cbFree = findViewById(R.id.cb_registerChargingFree);

        String code = etCode.getText().toString();
        String adress = etAdress.getText().toString();
        float power = Float.parseFloat(etPower.getText().toString());
        boolean free = cbFree.isChecked();

        if (point == null) {
            Snackbar.make(etCode, R.string.choose_point_map, BaseTransientBottomBar.LENGTH_LONG).show();
            return;
        }

        ChargingPoint chargingPoint = new ChargingPoint(code, adress, power, false, free, point.latitude(), point.longitude());

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "charging_points")
                .allowMainThreadQueries().build();
        db.chargingPointDao().insert(chargingPoint);

        Snackbar.make(etCode, R.string.add_charging_points, BaseTransientBottomBar.LENGTH_LONG).show();
        etCode.setText("");
        etAdress.setText("");
        etPower.setText("");
        cbFree.setChecked(false);
    }

    private void initializePointManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(chargingPointMap);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }

    private void addMarker(Point point) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.drawable.charging_point_marker));
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    private void removeAllMarkers() {
        pointAnnotationManager.deleteAll();
    }

    public void goBack(View view) {
        onBackPressed();
    }
}