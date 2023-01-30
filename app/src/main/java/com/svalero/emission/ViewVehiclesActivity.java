package com.svalero.emission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.svalero.emission.adapter.VehicleAdapter;
import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class ViewVehiclesActivity extends AppCompatActivity {

    private List<Vehicle> vehicleList;
    private VehicleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicles);

        vehicleList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.vehicle_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new VehicleAdapter(this, vehicleList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "vehicles")
                .allowMainThreadQueries().build();
        vehicleList.clear();
        vehicleList.addAll(db.vehicleDao().getVehiclesByUserName(MainActivity.currentUser));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.register_charging_points) {
            Intent intent = new Intent(this, RegisterChargingPointActivity.class);
            startActivity(intent);
            return  true;
        } else if(item.getItemId() == R.id.view_profile) {
            Intent intent = new Intent(this, ViewProfileActivity.class);
            startActivity(intent);
            return  true;
        }
        else if(item.getItemId() == R.id.view_map) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            return  true;
        }
        return false;
    }
}