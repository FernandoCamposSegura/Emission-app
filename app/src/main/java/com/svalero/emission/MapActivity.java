package com.svalero.emission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

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

import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        initializePointManager();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "charging_points")
                .allowMainThreadQueries().build();
        List<ChargingPoint> chargingPointList = db.chargingPointDao().getAll();
        addTasksToMap(chargingPointList);
    }

    private void addTasksToMap(List<ChargingPoint> chargingPointList) {
        for (ChargingPoint chargingPoint : chargingPointList) {
            Point point = Point.fromLngLat(chargingPoint.getLongitude(), chargingPoint.getLatitude());
            addMarker(point, chargingPoint.getCode());
        }

        ChargingPoint lastChargingPoint = chargingPointList.get(chargingPointList.size() - 1);
        setCameraPosition(Point.fromLngLat(lastChargingPoint.getLongitude(), lastChargingPoint.getLatitude()));
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
}