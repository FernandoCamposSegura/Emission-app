package com.svalero.emission;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.ChargingPoint;
import com.svalero.emission.domain.User;

import java.util.List;

public class ChargingPointDetailsActivity extends AppCompatActivity {

    String code;
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_point_details);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        if (code == null)
            return;

        mapView = findViewById(R.id.chargingPointMapDetail);
        initializePointManager();



        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "charging_points")
                .allowMainThreadQueries().build();
        ChargingPoint chargingPoint = db.chargingPointDao().getByCode(code);

        fillData(chargingPoint);
        addChargingPointToMap(chargingPoint);
    }

    private void fillData(ChargingPoint chargingPoint) {
        TextView tv_detailCodeChargingPoint = findViewById(R.id.tv_detailChargingCode);
        TextView tv_detailAdressChargingPoint = findViewById(R.id.tv_detailChargingAdress);
        TextView tv_detailPowerChargingPoint = findViewById(R.id.tv_detailChargingPower);
        CheckBox cb_detailFreeChargingPoint = findViewById(R.id.cb_detailChargingFree);

        tv_detailCodeChargingPoint.setText(chargingPoint.getCode());
        tv_detailAdressChargingPoint.setText(chargingPoint.getAdress());
        tv_detailPowerChargingPoint.setText(String.valueOf(chargingPoint.getPower()));
        cb_detailFreeChargingPoint.setChecked(chargingPoint.isFree());

    }

    private void addChargingPointToMap(ChargingPoint chargingPoint) {

        Point point = Point.fromLngLat(chargingPoint.getLongitude(), chargingPoint.getLatitude());
        addMarker(point, "");
        setCameraPosition(point);

    }

    private void initializePointManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }

    private void addMarker(Point point, String title) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withTextField(title)
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.drawable.charging_point_marker));
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    private void setCameraPosition(Point point) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(point)
                .pitch(0.0)
                .zoom(13.5)
                .bearing(-17.6)
                .build();
        mapView.getMapboxMap().setCamera(cameraPosition);
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void modifyChargingPoint(View view) {
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        if (code == null)
            return;

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "charging_points")
                .allowMainThreadQueries().build();
        ChargingPoint chargingPoint = db.chargingPointDao().getByCode(code);

        Intent newIntent = new Intent(this, ModifyChargingPointActivity.class);
        newIntent.putExtra("code", chargingPoint.getCode());
        startActivity(newIntent);
    }

    public void deleteChargingPoint(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_message)
                .setTitle(R.string.remove_charging_point_message)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    Intent intent = getIntent();
                    String code = intent.getStringExtra("code");
                    if (code == null)
                        return;

                    final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "charging_points")
                            .allowMainThreadQueries().build();

                    ChargingPoint chargingPoint = db.chargingPointDao().getByCode(code);
                    db.chargingPointDao().delete(chargingPoint);
                    onBackPressed();
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}